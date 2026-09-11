#!/system/bin/sh
# dump-mem2.sh — 快速无注入内存抓取 v2（不冻结，看门狗无感知）
# 与 v1 的区别: kill -STOP 会触发壳 fork 看门狗（检测父进程 state=T）SIGKILL 父进程，
# 故 v2 完全不碰进程状态；只读 /proc/PID/mem，仅抓匿名段（业务 dex 是匿名内存）。
# 用法: su -c 'sh /data/local/tmp/memdump2.sh <pid>'   （pid 缺省用自动识别）
OUT=/data/local/tmp/memdump2
BB=/data/adb/magisk/busybox
PKG=com.moutai.mall

if [ -n "$1" ]; then PID="$1"; else
    PID=""
    for p in $(pidof "$PKG"); do
        if grep -q "libDexHelper" /proc/$p/maps 2>/dev/null; then PID=$p; break; fi
    done
fi
[ -z "$PID" ] && { echo "ERR: no pid"; exit 1; }
echo "PID=$PID (no freeze, read-only)"

rm -rf "$OUT"; mkdir -p "$OUT"
cp /proc/$PID/maps "$OUT/maps.txt" 2>/dev/null

i=0
sed 's/ \+/ /g' /proc/$PID/maps > "$OUT/maps.norm.txt" 2>/dev/null
while read -r f1 f2 f3 f4 f5 f6 rest; do
    case "$f2" in r--p|rw-p) ;; *) continue ;; esac
    # 只要匿名/memfd 段（dex 解密产物在 dalvik 堆、memfd 映射或无名匿名内存）
    # 注意: 区域名可含空格（如 "[anon:dalvik-main space (region space)]"），read 分词后
    # f6 无闭括号，必须用前缀匹配 "\[*"，不能用 "\[*]" 或 "\[*\]*"（F16 教训）
    case "$f6" in
        ""|[aA]non*|\[*|/memfd:*) ;;
        *) continue ;;
    esac
    s=$((16#${f1%%-*})); e=$((16#${f1##*-})); size=$((e - s))
    [ "$size" -le 0 ] && continue
    [ "$size" -gt 1073741824 ] && continue
    [ "$size" -lt 65536 ] && continue
    n=$(printf "%03d" $i)
    $BB dd if=/proc/$PID/mem bs=1048576 iflag=skip_bytes,count_bytes skip=$s count=$size of="$OUT/$n.bin" 2>/dev/null
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
