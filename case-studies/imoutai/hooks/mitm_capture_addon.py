"""mitmdump addon: 抓取 i茅台 全部 /xhr/ 流量 → JSONL（脱敏后处理由离线脚本做）"""
import json
import time
from mitmproxy import http

OUT = r'E:\code\逆向\android\work\20260907-222238-i-app-frida-dump-dex-android-apk\evidence\mitm-flows-20260912.jsonl'

def response(flow: http.HTTPFlow) -> None:
    try:
        if 'moutai' not in flow.request.host:
            return
        entry = {
            'ts': time.strftime('%H:%M:%S'),
            'host': flow.request.host,
            'method': flow.request.method,
            'url': flow.request.url,
            'path': flow.request.path,
            'reqHeaders': dict(flow.request.headers),
            'reqBody': flow.request.get_text()[:3000],
            'status': flow.response.status_code if flow.response else 0,
            'respBody': (flow.response.get_text()[:1500] if flow.response else ''),
        }
        with open(OUT, 'a', encoding='utf-8') as f:
            f.write(json.dumps(entry, ensure_ascii=False) + '\n')
        print(f'[capture] {entry["method"]} {entry["path"][:70]} → {entry["status"]}', flush=True)
    except Exception as e:
        print(f'[addon-error] {e}', flush=True)
