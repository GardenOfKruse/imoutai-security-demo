#!/system/bin/sh
# dump-mem4.sh — clean-process, read-only coverage expansion
# Compared with dump-mem3: also captures readable app-owned file-backed mappings
# with r--s/rw-s/r-xp/rwxp permissions. No STOP/CONT and no old-output cleanup.
OUT_BASE=/data/local/tmp/memdump4
BB=/data/adb/magisk/busybox
PKG=com.moutai.mall

if [ -n "$1" ]; then PID="$1"; else
    PID=""
    for p in $(pidof "$PKG"); do
        if grep -q "libDexHelper" /proc/$p/maps 2>/dev/null; then PID=$p; break; fi
    done
fi
[ -z "$PID" ] && { echo "ERR: no pid"; exit 1; }

OUT="$OUT_BASE-$PID-$(date +%s)"
mkdir -p "$OUT"
echo "PID=$PID (read-only, no freeze, all readable target file mappings)"
cp /proc/$PID/maps "$OUT/maps.txt" 2>/dev/null
sed 's/ \+/ /g' /proc/$PID/maps > "$OUT/maps.norm.txt" 2>/dev/null

i=0
while read -r f1 f2 f3 f4 f5 f6 rest; do
    case "$f2" in
        r--p|rw-p|r--s|rw-s|r-xp|rwxp) ;;
        *) continue ;;
    esac
    # Keep anonymous/dalvik/memfd and every target-owned file-backed mapping.
    case "$f6" in
        ""|[aA]non*|\[*|/memfd:*|*com.moutai.mall*) ;;
        *) continue ;;
    esac
    s=$((16#${f1%%-*})); e=$((16#${f1##*-})); size=$((e - s))
    [ "$size" -le 0 ] && continue
    [ "$size" -gt 1073741824 ] && continue
    [ "$size" -lt 4096 ] && continue
    n=$(printf "%04d" $i)
    $BB dd if=/proc/$PID/mem bs=1048576 iflag=skip_bytes,count_bytes \
        skip=$s count=$size of="$OUT/$n.bin" 2>/dev/null
    if [ -s "$OUT/$n.bin" ]; then
        echo "$n $f1 $f2 $f6 size=$size" >> "$OUT/dump-list.txt"
        i=$((i+1))
    else
        rm -f "$OUT/$n.bin"
    fi
done < "$OUT/maps.norm.txt"

echo "captured_regions=$i"
if kill -0 $PID 2>/dev/null; then echo "process alive after dump"; else echo "WARN: process gone after dump"; fi
for f in "$OUT"/*.bin; do [ -f "$f" ] && gzip -1 -f "$f"; done
du -sh "$OUT"
echo "OUT=$OUT"
