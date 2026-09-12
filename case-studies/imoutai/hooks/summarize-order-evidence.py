#!/usr/bin/env python3
"""Summarize the safe order-evidence events emitted by the read-only Frida hook.

The driver writes Python repr payloads, so parsing uses ast.literal_eval only.
This script never prints or writes request/header/response values.
"""

import ast
import json
import re
import sys
from pathlib import Path


AGENT_RE = re.compile(r"\[agent-send\]\s+(\{.*\})\s*$")


def parse_events(lines):
    events = []
    parse_errors = 0
    for line in lines:
        match = AGENT_RE.search(line)
        if not match:
            continue
        try:
            payload = ast.literal_eval(match.group(1))
        except (SyntaxError, ValueError):
            parse_errors += 1
            continue
        if not isinstance(payload, dict) or payload.get("type") != "order-evidence":
            continue
        data = payload.get("data")
        if isinstance(data, dict):
            events.append(data)
    return events, parse_errors


def summarize(events, parse_errors=0):
    phases = {
        "compose": {"requests": 0, "responses": 0, "responseCodes": [], "requestShapes": [], "responseShapes": []},
        "captcha-network": {"requests": 0, "responses": 0, "responseCodes": [], "requestShapes": [], "responseShapes": []},
        "submit": {"requests": 0, "responses": 0, "responseCodes": [], "requestShapes": [], "responseShapes": []},
    }
    request_sequence = []
    captcha_events = {"sdkInventory": 0, "webViewCalls": 0, "errors": 0}
    has_transaction_field = False
    has_order_field = False

    for event in events:
        kind = event.get("kind")
        if kind == "http-request":
            phase = event.get("phase")
            if phase in phases:
                phases[phase]["requests"] += 1
                request_sequence.append(phase)
                body = event.get("body")
                if isinstance(body, dict) and "jsonShape" in body:
                    phases[phase]["requestShapes"].append(body["jsonShape"])
        elif kind == "http-response":
            phase = event.get("phase")
            if phase in phases:
                phases[phase]["responses"] += 1
                response = event.get("response")
                if isinstance(response, dict):
                    if isinstance(response.get("code"), int):
                        phases[phase]["responseCodes"].append(response["code"])
                    if "jsonShape" in response:
                        phases[phase]["responseShapes"].append(response["jsonShape"])
                    has_transaction_field = has_transaction_field or response.get("hasTransactionId") is True
                    has_order_field = has_order_field or response.get("hasOrderId") is True
        elif kind == "captcha-sdk-inventory":
            captcha_events["sdkInventory"] += 1
        elif kind == "captcha-webview-call":
            captcha_events["webViewCalls"] += 1
        elif kind == "error":
            captcha_events["errors"] += 1

    compose_before_submit = (
        "compose" in request_sequence
        and "submit" in request_sequence
        and request_sequence.index("compose") < request_sequence.index("submit")
    )
    shape_ready = all(
        phases[p]["requests"] > 0 and phases[p]["responses"] > 0
        for p in ("compose", "submit")
    )
    return {
        "eventCount": len(events),
        "parseErrors": parse_errors,
        "requestSequence": request_sequence,
        "composeBeforeSubmit": compose_before_submit,
        "shapeReadyForReview": shape_ready and compose_before_submit,
        "hasTransactionFieldInResponse": has_transaction_field,
        "hasOrderFieldInResponse": has_order_field,
        "captchaSignals": captcha_events,
        "phases": phases,
        "structuralOnly": True,
        "orderCreated": False,
        "note": "字段形状摘要不等于真实订单成功；验证码通过和订单创建仍需人工/服务端证据。",
    }


def self_test():
    lines = [
        "[agent-send] {'type': 'order-evidence', 'data': {'kind': 'installed', 'readOnly': True}}",
        "[agent-send] {'type': 'order-evidence', 'data': {'kind': 'http-request', 'phase': 'compose', 'body': {'jsonShape': {'actParam': 'string'}}}}",
        "[agent-send] {'type': 'order-evidence', 'data': {'kind': 'http-response', 'phase': 'compose', 'response': {'code': 200, 'jsonShape': {'data': 'object'}, 'hasTransactionId': True}}}",
        "[agent-send] {'type': 'order-evidence', 'data': {'kind': 'captcha-webview-call', 'method': 'evaluateJavascript', 'argLengths': [42]}}",
        "[agent-send] {'type': 'order-evidence', 'data': {'kind': 'http-request', 'phase': 'submit', 'body': {'jsonShape': {'transactionId': 'string'}}}}",
        "[agent-send] {'type': 'order-evidence', 'data': {'kind': 'http-response', 'phase': 'submit', 'response': {'code': 200, 'jsonShape': {'data': 'object'}, 'hasOrderId': True}}}",
    ]
    report = summarize(*parse_events(lines))
    assert report["shapeReadyForReview"]
    assert report["composeBeforeSubmit"]
    assert report["hasTransactionFieldInResponse"]
    assert report["hasOrderFieldInResponse"]
    assert report["captchaSignals"]["webViewCalls"] == 1
    print(json.dumps({"selfTest": "passed", "structuralOnly": report["structuralOnly"]}, ensure_ascii=False))


def main():
    if len(sys.argv) == 2 and sys.argv[1] == "--self-test":
        self_test()
        return 0
    if len(sys.argv) != 2:
        print("用法：python summarize-order-evidence.py <events.log>", file=sys.stderr)
        return 2
    path = Path(sys.argv[1])
    if not path.is_file():
        print("events.log 不存在", file=sys.stderr)
        return 2
    events, parse_errors = parse_events(path.read_text(encoding="utf-8", errors="replace").splitlines())
    print(json.dumps(summarize(events, parse_errors), ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
