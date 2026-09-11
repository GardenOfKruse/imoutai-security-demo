#!/usr/bin/env python
"""
gen_openapi.py — 从运行时取证数据生成 i茅台 App 后端接口 OpenAPI 3.0 文档
数据源: extract/obs-mp34-annot2-t6-20260911/reflection.jsonl（220 类方法/参数/字段注解 dump）
输出: demo-app/src/lib/openapi.json（内嵌 Swagger UI）+ demo-app/docs/imoutai-openapi.json（独立分发）
"""
import json
import re
from pathlib import Path

SRC = Path(r'E:/code/逆向/android/work/20260907-222238-i-app-frida-dump-dex-android-apk/extract/obs-mp34-annot2-t6-20260911/reflection.jsonl')
OUT_EMBED = Path(r'E:/code/逆向/android/case-studies/imoutai/demo-app/src/lib/openapi.json')
OUT_DIST = Path(r'E:/code/逆向/android/case-studies/imoutai/demo-app/docs/imoutai-openapi.json')

# 注解字母映射（Retrofit 混淆后单字母，映射关系经 annot2 dump 交叉验证）
HTTP_MAP = {'hj.o': 'post', 'hj.f': 'get'}
PARAM_MAP = {'@hj.a': 'body', '@hj.j': 'headerMap', '@hj.y': 'url', '@hj.w': 'streaming', '@hj.l': 'multipart'}

TYPE_MAP = {
    'class java.lang.String': ('string', None),
    'int': ('integer', 'int32'),
    'long': ('integer', 'int64'),
    'class java.lang.Long': ('integer', 'int64'),
    'class java.lang.Integer': ('integer', 'int32'),
    'boolean': ('boolean', None),
    'class java.lang.Boolean': ('boolean', None),
    'class java.util.Map': ('object', None),
    'class java.util.List': ('array', None),
    'float': ('number', 'float'),
    'double': ('number', 'double'),
    'class java.lang.Object': ('object', None),
}


# 语义注释：按路径精确匹配（已知名），未命中则按最后一段自动生成
PATH_DESC = {
    '/xhr/front/user/register/vcode': '发送短信验证码。md5 = MD5(deviceKey + mobile + timestamp)，客户端预计算（已离线逐字节验证 15/15）',
    '/xhr/front/user/register/login': '短信验证码登录（登录即隐式注册）。HeaderMap 携带签名头（MT-Token 等）',
    '/xhr/front/user/register/ctdid/login': '一键登录（中国移动认证）：bizSeq + certPwdData + idCardAuthData',
    '/xhr/front/user/register/ctdid/bindLogin': '一键登录绑定手机号',
    '/xhr/front/user/register/cancel': '账号注销',
    '/xhr/front/user/realNameAuth': '实名认证提交（姓名 + 身份证）',
    '/xhr/front/user/realPersonAuth': '人脸核身（V1）',
    '/xhr/front/user/realPersonAuthV2': '人脸核身（V2）',
    '/xhr/front/user/getRealNameAuthInfo': '查询已提交的实名认证信息',
    '/xhr/front/user/authConfig': '认证/登录配置拉取（登录页自动调用，风控信号观测点）',
    '/xhr/front/user/ctdid/didInfo': '设备数字身份（ctdid）信息查询',
    '/xhr/front/user/ctdid/sendUnbindCode': '发送设备解绑验证码',
    '/xhr/front/user/ctdid/unbind': '解绑设备数字身份',
    '/xhr/front/user/ctdid/userBind': '用户与设备数字身份绑定',
    '/xhr/front/user/ctdid/bindNewCtdid': '绑定新设备数字身份',
    '/xhr/front/user/ctdid/args': 'ctdid 参数下发',
    '/xhr/front/user/did/send/success': '设备身份绑定成功回执',
    '/xhr/front/user/did/user/center': '设备身份用户中心',
    '/xhr/front/mall/reservation/add': '提交申购预约（核心业务写接口，风控重点）',
    '/xhr/front/mall/reservation/list/days': '预约开放日历',
    '/xhr/front/mall/reservation/list/pageOne/queryV2': '预约列表第一页',
    '/xhr/front/mall/reservation/list/more/queryV2': '预约列表加载更多',
    '/xhr/front/mall/reservation/detail/query': '预约详情',
    '/xhr/front/mall/reservation/record/cache': '预约记录缓存',
    '/xhr/front/mall/item/shopList': '门店商品列表',
    '/xhr/front/mall/item/shopList/v2': '门店商品列表 V2',
    '/xhr/front/mall/item/popular': '热门商品推荐',
    '/xhr/front/mall/item/purchaseInfoV2': '★ 商品购买信息（本次测试目标接口，H5 域）',
    '/xhr/front/mall/item/nfcTrace/getRandomCode': 'NFC 防伪追溯随机码',
    '/xhr/front/mall/item/nfcTrace/getInfo': 'NFC 防伪追溯信息',
    '/xhr/front/mall/item/codeTrace/getIndex': '码上追溯索引',
    '/xhr/front/trade/order/standard/compose': '订单组合（结算页数据生成）',
    '/xhr/front/trade/order/standard/compose/v2': '订单组合 V2',
    '/xhr/front/trade/order/standard/submit': '订单提交（核心写接口，止步支付）',
    '/xhr/front/trade/order/standard/submit/v2': '订单提交 V2（核心写接口，止步支付）',
    '/xhr/front/trade/order/place/get': '下单前置数据获取',
    '/xhr/front/trade/order/place/create': '创建订单',
    '/xhr/front/trade/order/place/batch/get': '批量下单前置数据',
    '/xhr/front/trade/order/place/batch/create': '批量创建订单',
    '/xhr/front/trade/order/pay': '🚫 发起支付（仅文档化——测试红线：绝不真实调用，保护线上账务）',
    '/xhr/front/trade/order/payResult': '🚫 支付结果查询（同上，仅文档化）',
    '/xhr/front/trade/order/ops/cancel': '取消订单',
    '/xhr/front/trade/order/detail/get': '订单详情',
    '/xhr/front/trade/order/list/get': '订单列表',
    '/xhr/front/trade/cart/getCarts': '获取购物车',
    '/xhr/front/trade/cart/getMiniCartNum': '迷你购物车数量角标',
    '/xhr/front/trade/cart/updateCount': '更新购物车数量',
    '/xhr/front/trade/cart/updateCheck': '购物车勾选状态',
    '/xhr/front/trade/cart/precheck': '购物车结算前置校验',
    '/xhr/front/trade/cart/delete': '删除购物车条目',
    '/xhr/front/trade/invoice/supply': '发票信息提供',
    '/xhr/front/trade/invoice/saveTempInvoice': '暂存发票信息',
    '/xhr/front/trade/invoice/fuzzySearch': '发票抬头模糊搜索',
    '/xhr/front/trade/pickupCode/get': '自提码获取',
    '/xhr/front/trade/priority/static/resource': '静态资源优先级配置',
    '/xhr/front/user/address/ship/query': '收货地址列表',
    '/xhr/front/user/address/ship/upsert': '新建/更新收货地址',
    '/xhr/front/user/address/ship/delete': '删除收货地址',
    '/xhr/front/user/birthday/set': '设置生日',
    '/xhr/front/user/avatar/update': '头像更新',
    '/xhr/front/user/avatar/query': '头像查询',
    '/xhr/front/user/gray/grayInfo': '灰度配置查询',
    '/xhr/front/user/center/service': '用户中心服务入口',
    '/xhr/front/user/center/order': '用户中心订单入口',
    '/xhr/front/user/info': '用户信息',
    '/xhr/front/user/privacy/query': '隐私协议查询',
    '/xhr/front/user/shop/attention/list': '关注的门店列表',
    '/xhr/front/user/shop/attention/subscribe': '订阅/关注门店',
    '/xhr/front/user/shop/default/subscribe': '设置默认门店',
    '/xhr/front/user/shop/default/upsert': '更新默认自提门店',
    '/xhr/front/mall/shop/selfPickUp/getAllCity': '自提门店城市列表',
    '/xhr/front/mall/shop/selfPickUp/queryList': '自提门店列表',
    '/xhr/front/mall/shop/reservation/userAttention/list': '关注的可预约门店',
    '/xhr/front/mall/message/list/query': '站内消息列表',
    '/xhr/front/mall/message/unRead/query': '未读消息数',
    '/xhr/front/mall/message/status/update': '消息状态更新',
    '/xhr/front/mall/index/popup/get': '首页弹窗配置',
    '/xhr/front/mall/index/pop': '首页弹层',
    '/xhr/front/mall/index/checkScanCode': '扫码结果校验',
    '/xhr/front/mall/index/common/popup/get': '通用弹窗',
    '/xhr/front/mall/index/xmy/user/coin': '小茅运积分',
    '/xhr/front/mall/config/camera/white': '相机白名单配置',
    '/xhr/front/mall/config/webview/white': 'WebView 域名白名单',
    '/xhr/front/mall/config/check/phoneSegment': '号段校验（发码前检查）',
    '/xhr/front/mall/praise/status/update': '点赞状态更新',
    '/xhr/front/user/community/index': '社区首页',
    '/xhr/front/user/community/search': '社区搜索',
    '/xhr/front/user/community/search/random': '社区随机内容',
    '/xhr/front/user/community/note/detail': '笔记详情',
    '/xhr/front/user/community/note/collect': '笔记收藏',
    '/xhr/front/user/community/note/uncollect': '取消收藏',
    '/xhr/front/user/community/note/like': '笔记点赞',
    '/xhr/front/user/community/note/dislike': '笔记点踩',
    '/xhr/front/user/community/note/read': '笔记已读',
    '/xhr/front/user/community/note/share': '笔记分享',
    '/xhr/front/user/community/user/home/page': '社区用户主页',
    '/xhr/front/user/community/user/follow': '关注用户',
    '/xhr/front/user/community/user/unfollow': '取消关注',
    '/xhr/front/user/community/tab': '社区标签页配置',
    '/xhr/front/geo/city/query': 'GPS 城市查询',
    '/push/client/entry.do': '推送客户端入口',
    '/game/userinfo/getUserCoin': '游戏金币查询',
    '/xhr/front/support/feedback/report': '意见反馈上报（multipart）',
    '/xhr/front/user/copy/userInfo/sendCode': '跨账号迁移发送验证码',
    '/xhr/front/user/copy/userInfo/checkCode': '跨账号迁移校验验证码',
    '/xhr/front/user/community/note/share': '笔记分享',
}


def describe(path, http, op_id, body_model, entries_):
    if path in PATH_DESC:
        return PATH_DESC[path]
    tail = path.rstrip('/').split('/')[-1]
    pretty = tail.replace('/', ' ').replace('-', ' ').replace('/', ' ')
    label = 'GET 查询' if http == 'get' else 'POST 提交'
    extra = ''
    if body_model:
        fields = list(entries_.get(body_model, {}).get('fields', []) and
                      [f['name'] for f in entries_[body_model]['fields'] if not f['name'].startswith('$')] or [])
        if fields:
            extra = f' · 请求字段：{", ".join(fields[:10])}'
    return f'{label}（运行时还原，路径尾段 {tail}）{extra}'


TAG_MAP = [
    ('/xhr/front/user/register', '账户 · 注册与登录'),
    ('/xhr/front/user/ctdid', '账户 · 设备绑定 ctdid'),
    ('/xhr/front/user/realNameAuth|/xhr/front/user/realPersonAuth|/xhr/front/user/getRealNameAuthInfo|/xhr/front/user/birthday',
     '账户 · 实名认证'),
    ('/xhr/front/user/community', '社区'),
    ('/xhr/front/trade/order', '交易 · 订单'),
    ('/xhr/front/trade/cart', '交易 · 购物车'),
    ('/xhr/front/trade/invoice', '交易 · 发票'),
    ('/xhr/front/trade/pay', '交易 · 支付'),
    ('/xhr/front/mall/reservation', '商城 · 预约申购'),
    ('/xhr/front/mall/item', '商城 · 商品'),
    ('/xhr/front/mall/message', '商城 · 消息'),
    ('/xhr/front/mall/shop', '商城 · 门店'),
    ('/xhr/front/user/address', '账户 · 地址'),
    ('/xhr/front/user', '账户 · 其他'),
    ('/xhr/front/mall', '商城 · 其他'),
    ('/xhr/front/trade', '交易 · 其他'),
    ('/push/', '推送'),
    ('/game/', '游戏'),
    ('/xhr/', '其他'),
]


def tag_of(path):
    for pat, tag in TAG_MAP:
        if re.search(pat, path):
            return tag
    return '其他'


def short_type(t):
    return t.replace('class ', '').replace('interface ', '')


def parse_params(sig):
    """从方法签名提取参数类型列表"""
    m = re.search(r'\((.*)\)', sig)
    if not m:
        return []
    inner = m.group(1)
    if not inner:
        return []
    return [p.strip() for p in inner.split(',') if p.strip()]


def schema_of(java_type, entries, depth=0):
    t = short_type(java_type)
    if t in TYPE_MAP:
        typ, fmt = TYPE_MAP[t]
        s = {'type': typ}
        if fmt:
            s['format'] = fmt
        return s
    if t.endswith('[]'):
        return {'type': 'array', 'items': schema_of(t[:-2], entries, depth + 1)}
    if t.startswith('java.util.List') or 'List<' in t:
        return {'type': 'array', 'items': {'type': 'object'}}
    if depth < 2 and t in entries:
        return {'$ref': f'#/components/schemas/{t}'}
    return {'type': 'object', 'x-unknown': t}


def build_schema(cn, e):
    """从类字段构建请求/响应模型 schema"""
    props = {}
    required = []
    for f in e.get('fields', []):
        name = f['name']
        if name.startswith('$'):
            continue
        ft = f['type']
        if ft.startswith('class [') or ft.startswith('[L'):
            props[name] = {'type': 'array', 'items': {}, 'x-java': ft}
            continue
        props[name] = schema_of(ft, entries, 1)
        anns = [a for a in f.get('annotations', []) if not a.startswith('(')]
        if anns:
            props[name]['x-annotations'] = anns
    return {
        'type': 'object',
        'description': f'运行时反射提取的字段结构（{len(props)} 字段）',
        'properties': props,
        'required': required,
    }


# ---------- 加载 dump ----------
entries = {}
for line in open(SRC, encoding='utf-8'):
    e = json.loads(line)
    entries[e['className']] = e

api_f = entries.get('com.moutai.mall.api.f')
assert api_f, 'api.f not found'

paths = {}
schemas = {}
model_count = 0
skipped = []

for m in api_f['methods']:
    sig = m['signature']
    anns = [a for a in m.get('annotations', []) if not a.startswith('(')]
    http = None
    path = None
    for a in anns:
        am = re.match(r'@(hj\.[of])\(value=([^\)]*)\)', a)
        if am:
            http = HTTP_MAP.get(am.group(1))
            path = am.group(2)
            break
    if not http or not path:
        continue
    mn = re.search(r'\.(\w+)\(', sig)
    op_id = 'api_f_' + (mn.group(1) if mn else 'unknown')
    path = path if path.startswith('/') else '/' + path
    if path in ('/',):
        skipped.append((op_id, path, '动态 URL'))
        continue

    params = parse_params(sig)
    panns = m.get('parameterAnnotations', [])
    ptypes = []
    for i, p in enumerate(params):
        role = None
        if i < len(panns):
            flat = [a for one in panns[i] if isinstance(panns[i], list) for a in [one] if a.startswith('@')]
            for a in flat:
                r = PARAM_MAP.get(a.split('(')[0])
                if r:
                    role = r
        # kotlin.coroutines.Continuation 末参忽略
        if 'kotlin.coroutines' in p or p == 'kotlin.coroutines.d':
            continue
        ptypes.append((p, role))

    body_model_probe = None
    for p_, role_ in ptypes:
        pass

    op = {
        'operationId': op_id,
        'summary': f'[{mn.group(1) if mn else op_id}] ' + describe(path, http, op_id, None, entries),
        'tags': [tag_of(path)],
        'x-source': 'Frida 运行时反射注解 dump（annot2，2026-09-11，220 类 0 失败）',
        'x-app-method': sig[:160],
        'parameters': [],
        'responses': {'200': {'description': '服务端响应（结构待运行时二次取证）'}},
    }
    body_model = None
    for p, role in ptypes:
        if role == 'body' and p.startswith('com.moutai.mall.api.model.'):
            body_model = p
        elif role == 'headerMap':
            op['parameters'].append({'name': 'X-HeaderMap', 'in': 'header', 'required': True,
                                     'description': '@HeaderMap 动态签名头（MT-Token / clips_* 设备头等，由 okhttp 拦截器 api.a.intercept 注入；头名→值映射见 findings F3）',
                                     'schema': {'type': 'object', 'additionalProperties': {'type': 'string'}}})
        elif role == 'url':
            continue
        elif http == 'get' and p in ('class java.lang.String', 'int', 'long', 'class java.lang.Long', 'class java.lang.Integer'):
            s = schema_of(p, entries)
            op['parameters'].append({'name': f'param{len(op["parameters"]) + 1}', 'in': 'query', 'schema': s,
                                     'description': f'GET 参数（原类型 {short_type(p)}，具体名待抓包校准）'})
        elif p.startswith('com.moutai.mall.api.model.'):
            op['parameters'].append({'name': short_type(p).split('.')[-1], 'in': 'query', 'schema': {'$ref': f'#/components/schemas/{p}'}})

    op['description'] = describe(path, http, op_id, body_model, entries) +         f'｜App 内方法：{op_id[6:]}｜提取方式：运行时反射注解 dump'

    if http == 'post':
        if body_model and body_model in entries:
            op['requestBody'] = {
                'required': True,
                'content': {'application/json': {'schema': {'$ref': f'#/components/schemas/{body_model}'}},
                            'x-note': '@hj.a() Body 序列化：Moshi（字段名即 key）'},
            }
        else:
            op['requestBody'] = {
                'required': False,
                'content': {'application/json': {'schema': {'type': 'object'}, 'x-note': '请求体结构待运行时二次取证' +
                                                          (f'（模型 {body_model} 未在快照中）' if body_model else '')}},
            }

    paths.setdefault(path, {})[http] = op

# ---------- 客户指定测试目标（H5 域，App 端 api.f 无此接口——显式补充） ----------
paths.setdefault('/xhr/front/mall/item/purchaseInfoV2', {})['post'] = {
    'operationId': 'h5_purchaseInfoV2',
    'summary': '★ 测试目标：商品购买信息（H5 域专用，App 端 api.f 中不存在）',
    'description': (
        '''**本次测试指定目标接口**（客户指定，域：h5.moutai519.com.cn）。

- 注意：该接口不在 App 端 api.f 的 105 个还原接口中——它是 H5 前端专用调用，App 端 `api.f` 仅含 `popular/shopList` 等商品查询。
- 请求结构、签名头与设备号参数：待 H5 前端 JS 逆向（Phase 0 P0-1）补全。
- 风控测试用例设计见 runbook（F1 组：purchaseInfoV2 行为基线）。'''
    ),
    'tags': ['商城 · 商品'],
    'x-source': '客户指定目标 + Phase 0 待逆向',
    'parameters': [
        {'name': 'X-HeaderMap', 'in': 'header', 'required': True,
         'description': 'H5 端签名头（字段待 P0-1 逆向确认）',
         'schema': {'type': 'object', 'additionalProperties': {'type': 'string'}}},
    ],
    'requestBody': {'required': False, 'content': {'application/json': {
        'schema': {'type': 'object'},
        'x-note': '请求体结构待 H5 前端 JS 逆向补全（Phase 0 P0-1）'}}},
    'responses': {'200': {'description': '商品购买信息（结构待逆向）'}},
}

# ---------- 模型 schemas（收集所有被引用 + 已有字段数据的 model） ----------
for cn, e in entries.items():
    if not cn.startswith('com.moutai.mall.api.model.'):
        continue
    if any(cn in json.dumps(op) for op in [p for v in paths.values() for p in v.values()]) or cn in (
            'com.moutai.mall.api.model.GetVerifyCodeRequest', 'com.moutai.mall.api.model.LoginRequest'):
        schemas[cn] = build_schema(cn, e)
        model_count += 1

# ---------- 基础设施（本次取证确认的域名资产） ----------
INFRA = {
    '网关（App 端，生产）': 'https://app.moutai519.com.cn',
    'H5 前端 / 目标接口': 'https://h5.moutai519.com.cn',
    '开发环境': 'http://devapp.moutai519.com.cn',
    '贵州开发环境': 'https://testapp.moutai519.com.cn',
    'CDN / 统计 / Web': 'Endpoints 枚举中存在 getApp/cdn/statistic/web 五个 getter，本次快照仅读取了 getApp()；其余域名现场补齐',
    'IP 说明': '全部域名走 CDN 动态解析，无静态 IP；现场演示可用 nslookup/dig 现场解析佐证',
}

spec = {
    'openapi': '3.0.3',
    'info': {
        'title': 'i茅台 App 后端接口全景（运行时取证还原）',
        'version': '1.0.0-forensic',
        'description': (
            '**本文档由授权安全测试的运行时取证数据自动生成**（Frida 延迟附加 + 纯反射注解 dump，'
            '2026-09-11，220 个业务类 / 0 失败；签名算法已离线逐字节验证 15/15）。\n\n'
            '### 数据来源\n'
            '- 接口路径与 HTTP 方法：Retrofit 注解（运行时 `getDeclaredAnnotations`）\n'
            '- 请求模型字段：类字段反射（Moshi 序列化，字段名即 JSON key）\n'
            '- 签名机制：`md5(deviceKey + mobile + timestamp)`（验证码接口），HeaderMap 签名头由 okhttp 拦截器 `api.a.intercept` 集中注入\n\n'
            '### 基础设施（本次取证确认）\n' +
            '\n'.join(f'- **{k}**：{v}' for k, v in INFRA.items()) +
            '\n\n### 合规声明\n'
            '- 客户盖章授权 + 线下备案；本文档仅用于授权范围内的安全测试与开发者培训演示\n'
            '- 🚫 支付相关接口（order/pay）**仅文档化，禁止真实调用**——保护线上账务\n'
            '- 文档由脚本自动生成，可复现：`hooks/gen_openapi.py`'
        ),
        'x-extraction-stats': {
            'classes_dumped': len(entries),
            'endpoints_documented': sum(len(v) for v in paths.values()),
            'request_models': model_count,
            'skipped_dynamic_url': len(skipped),
        },
    },
    'servers': [
        {'url': 'https://app.moutai519.com.cn', 'description': 'App 网关（生产）'},
        {'url': 'https://h5.moutai519.com.cn', 'description': 'H5 前端（目标接口 purchaseInfoV2 所在）'},
    ],
    'tags': [{'name': t} for t in sorted({tag_of(p) for p in paths})],
    'paths': {k: paths[k] for k in sorted(paths)},
    'components': {'schemas': {k: schemas[k] for k in sorted(schemas)}},
    'x-infrastructure': INFRA,
}

OUT_EMBED.parent.mkdir(parents=True, exist_ok=True)
OUT_EMBED.write_text(json.dumps(spec, ensure_ascii=False, indent=1), encoding='utf-8')
OUT_DIST.parent.mkdir(parents=True, exist_ok=True)
OUT_DIST.write_text(json.dumps(spec, ensure_ascii=False, indent=1), encoding='utf-8')

eps = sum(len(v) for v in paths.values())
print(f'endpoints={eps} models={model_count} paths={len(paths)} skipped_dynamic={len(skipped)}')
print(f'written: {OUT_EMBED}')
print(f'written: {OUT_DIST}')
