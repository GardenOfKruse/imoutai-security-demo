#!/system/bin/sh
# dump-mem3.sh — 无注入、无冻结的扩展内存抓取
# v2 基础上额外抓取路径包含 com.moutai.mall 的 r--p/rw-p file-backed 区域，
# 以及所有前缀为 [anon:dalvik-classes.dex] 的区域；不调用 STOP/CONT。
OUT=/data/local/tmp/memdump3
BB=/data/adb/magisk/busybox
PKG=com.moutai.mall

if [ -n "$1" ]; then PID="$1"; else
    PID=""
    for p in $(pidof "$PKG"); do
        if grep -q "libDexHelper" /proc/$p/maps 2>/dev/null; then PID=$p; break; fi
    done
fi
[ -z "$PID" ] && { echo "ERR: no pid"; exit 1; }
echo "PID=$PID (no freeze, read-only, app file-backed included)"

rm -rf "$OUT"; mkdir -p "$OUT"
cp /proc/$PID/maps "$OUT/maps.txt" 2>/dev/null
sed 's/ \+/ /g' /proc/$PID/maps > "$OUT/maps.norm.txt" 2>/dev/null

i=0
while read -r f1 f2 f3 f4 f5 f6 rest; do
    case "$f2" in r--p|rw-p) ;; *) continue ;; esac
    # f6 是路径/匿名标签的首个 token；路径含空格时仍可用前缀/包名判断。
    case "$f6" in
        ""|[aA]non*|\[*|/memfd:*|*com.moutai.mall*) ;;
        *) continue ;;
    esac
    s=$((16#${f1%%-*})); e=$((16#${f1##*-})); size=$((e - s))
    [ "$size" -le 0 ] && continue
    [ "$size" -gt 1073741824 ] && continue
    [ "$size" -lt 4096 ] && continue
    n=$(printf "%03d" $i)
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
echo "gzip..."
for f in "$OUT"/*.bin; do [ -f "$f" ] && gzip -1 -f "$f"; done
du -sh "$OUT"
