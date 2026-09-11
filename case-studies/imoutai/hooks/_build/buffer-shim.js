// Minimal Node Buffer-compatible subset used by frida-java-bridge/lib/mkdex.js.
// The resulting bundle runs in Frida's JavaScript runtime, which has no Node require().
'use strict';

function utf8Bytes(s) {
    var out = [];
    for (var i = 0; i < s.length; i++) {
        var c = s.charCodeAt(i);
        if (c < 0x80) out.push(c);
        else if (c < 0x800) out.push(0xc0 | (c >> 6), 0x80 | (c & 0x3f));
        else if (c >= 0xd800 && c <= 0xdbff && i + 1 < s.length) {
            var d = s.charCodeAt(++i);
            var cp = 0x10000 + ((c - 0xd800) << 10) + (d - 0xdc00);
            out.push(0xf0 | (cp >> 18), 0x80 | ((cp >> 12) & 0x3f),
                0x80 | ((cp >> 6) & 0x3f), 0x80 | (cp & 0x3f));
        } else out.push(0xe0 | (c >> 12), 0x80 | ((c >> 6) & 0x3f), 0x80 | (c & 0x3f));
    }
    return out;
}

class BufferShim extends Uint8Array {
    static from(value, encoding) {
        if (typeof value === 'string') {
            if (encoding && encoding !== 'utf8') throw new Error('buffer-shim: only utf8 supported');
            return new BufferShim(utf8Bytes(value));
        }
        if (value instanceof ArrayBuffer) return new BufferShim(new Uint8Array(value));
        if (ArrayBuffer.isView(value)) return new BufferShim(value);
        return new BufferShim(value || []);
    }

    static alloc(size) { return new BufferShim(size); }

    static concat(list, totalLength) {
        if (totalLength === undefined) {
            totalLength = 0;
            for (var i = 0; i < list.length; i++) totalLength += list[i].length;
        }
        var out = new BufferShim(totalLength);
        var off = 0;
        for (var j = 0; j < list.length && off < totalLength; j++) {
            var n = Math.min(list[j].length, totalLength - off);
            out.set(list[j].subarray(0, n), off);
            off += n;
        }
        return out;
    }

    write(str, offset, length, encoding) {
        offset = offset || 0;
        var bytes = BufferShim.from(str, encoding);
        var n = Math.min(length === undefined ? bytes.length : length, bytes.length, this.length - offset);
        this.set(bytes.subarray(0, n), offset);
        return n;
    }

    writeUInt32LE(value, offset) {
        this[offset] = value & 0xff;
        this[offset + 1] = (value >>> 8) & 0xff;
        this[offset + 2] = (value >>> 16) & 0xff;
        this[offset + 3] = (value >>> 24) & 0xff;
        return offset + 4;
    }

    copy(target, targetStart, sourceStart, sourceEnd) {
        targetStart = targetStart || 0;
        sourceStart = sourceStart || 0;
        sourceEnd = sourceEnd === undefined ? this.length : sourceEnd;
        target.set(this.subarray(sourceStart, sourceEnd), targetStart);
        return sourceEnd - sourceStart;
    }
}

export { BufferShim as Buffer };
