#!/system/bin/sh
# dump-mem.sh — 无注入内存快照（root 读 /proc/PID/mem，不 ptrace attach，TracerPid 不变）
# 用法: su -c 'sh /data/local/tmp/memdump.sh <pkg>'
# 产出: /data/local/tmp/memdump/NNN.gz + maps.txt
PKG="$1"
OUT=/data/local/tmp/memdump
BB=/data/adb/magisk/busybox

# 主进程 = 加载了 libDexHelper 的那个（排掉壳的 IsoService 隔离进程）
PID=""
for p in $(pidof "$PKG"); do
    if grep -q "libDexHelper" /proc/$p/maps 2>/dev/null; then
        PID=$p
        break
    fi
done
if [ -z "$PID" ]; then
    PID=$(pidof -s "$PKG" | cut -d' ' -f1)
fi
if [ -z "$PID" ]; then
    echo "ERR: $PKG not running"
    exit 1
fi
echo "PID=$PID, freezing..."
kill -STOP "$PID"
sleep 1

rm -rf "$OUT"
mkdir -p "$OUT"
cp /proc/$PID/maps "$OUT/maps.txt"

i=0
total=0
# 规范化多空格后取: f1=range f2=perms f6=path(可为空)
sed 's/ \+/ /g' /proc/$PID/maps | while read -r f1 f2 f3 f4 f5 f6 rest; do
    case "$f2" in
        r--p|rw-p) ;;
        *) continue ;;
    esac
    case "$f6" in
        /system/*|/vendor/*|/apex/*|/product/*|/data/app/*|/data/dalvik-cache/*|/dev/*|/dev) continue ;;
        /data/user/0/*|/data/data/*|/memfd*|/memfd:*) ;;   # app 数据 + memfd 保留
        ""|[aA]non*|\[*\]*) ;;                              # 匿名段（含 [anon:dalvik-*] Java堆）
        *) continue ;;
    esac
    s=$((16#${f1%%-*}))
    e=$((16#${f1##*-}))
    size=$((e - s))
    if [ "$size" -le 0 ] || [ "$size" -gt 1073741824 ]; then continue; fi
    n=$(printf "%03d" $i)
    $BB dd if=/proc/$PID/mem bs=1048576 iflag=skip_bytes,count_bytes skip=$s count=$size of="$OUT/$n.bin" 2>/dev/null
    echo "$n $f1 $f2 $f6 size=$size" >> "$OUT/dump-list.txt"
    i=$((i+1))
done

echo "gzip..."
for f in "$OUT"/*.bin; do
    [ -f "$f" ] || continue
    gzip -1 -f "$f"
done
kill -CONT "$PID"
echo "done: $(ls "$OUT" | wc -l) files"
ls -la "$OUT" | head -5
du -sh "$OUT"
