#!/usr/bin/env python
"""
watch_capture.py — M1.6 HeaderMap 抓取守护（跟随 App 重启自动重挂 hook）
每 2 秒轮询主进程 PID：出现新 PID 即自动在全新目录起一个 obs-driver 会话（--wait 240）。
总守护窗口 5 分钟。用户从容操作（勾协议→手机号→获取验证码→可登录），无需赶时间。
"""
import subprocess
import sys
import time
from datetime import datetime

ADB = r'E:\Android\platform-tools\adb.exe'
SERIAL = '82e459fc0920'
PKG = 'com.moutai.mall'
JS = 'dump-dex-hook-annot4.js'
BASE = r'E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\extract'
WATCH_SECONDS = 300
SESSION_WAIT = 600


def pidof():
    out = subprocess.run([ADB, '-s', SERIAL, 'shell', 'pidof', PKG],
                         capture_output=True, text=True).stdout.split()
    return int(out[0]) if out else None


def main():
    start = time.time()
    last = None
    n = 0
    print(f'[watch] 守护启动，窗口 {WATCH_SECONDS}s；App 重启将自动重挂 hook', flush=True)
    while time.time() - start < WATCH_SECONDS:
        pid = pidof()
        if pid and pid != last:
            n += 1
            outdir = f'{BASE}\\obs-headermap8-{datetime.now().strftime("%H%M%S")}-{n}'
            subprocess.Popen(
                [sys.executable, '-u', 'obs-driver.py', '--js', JS,
                 '--out', outdir, '--wait', str(SESSION_WAIT), '--attach-running'],
                stdout=open(f'{outdir}.log', 'w', encoding='utf-8'),
                stderr=subprocess.STDOUT, cwd='.')
            stamp = datetime.now().strftime('%H:%M:%S')
            print(f'[{stamp}] 会话#{n} → PID {pid}（out: {outdir}）', flush=True)
            last = pid
        elif pid is None and last is not None:
            print(f'[{datetime.now().strftime("%H:%M:%S")}] App 进程消失（熔断），等待重启后自动重挂…', flush=True)
            last = None
        time.sleep(2)
    print(f'[watch] 守护结束，共 {n} 个会话', flush=True)


if __name__ == '__main__':
    main()
