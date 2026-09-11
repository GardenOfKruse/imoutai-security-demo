// 直连登录脚本：node login_and_capture.js <mobile> <code> → 明文响应 + set-cookie → 提取 token 存档
const https = require('https');
const zlib = require('zlib');
const fs = require('fs');
const [mobile, vcode] = process.argv.slice(2);
const profile = JSON.parse(fs.readFileSync('E:/code/逆向/android/case-studies/imoutai/demo-app/docs/real-app-profile.json', 'utf8'));
const body = JSON.stringify({ mobile, vCode: vcode, ydLogId: '', ydToken: '' });
const h = { ...profile.headers };
h['Content-Type'] = 'application/json'; h['Content-Length'] = Buffer.byteLength(body); h['Accept-Encoding'] = 'gzip';
const req = https.request({ hostname: 'app.moutai519.com.cn', port: 443, path: '/xhr/front/user/register/login', method: 'POST', headers: h }, (res) => {
  const ch = [];
  res.on('data', c => ch.push(c));
  res.on('end', () => {
    let b = Buffer.concat(ch);
    try { b = zlib.gunzipSync(b); } catch (e) {}
    const out = { status: res.statusCode, setCookie: res.headers['set-cookie'] || [], body: b.toString('utf8').slice(0, 2500) };
    console.log(JSON.stringify(out, null, 2));
    if (out.status === 200) {
      fs.writeFileSync('E:/code/逆向/android/case-studies/imoutai/demo-app/docs/login-last.json', JSON.stringify(out));
      console.error('[saved] demo-app/docs/login-last.json');
    }
  });
});
req.on('error', e => console.error('ERR', String(e)));
req.write(body); req.end();
