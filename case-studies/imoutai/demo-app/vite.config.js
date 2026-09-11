import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// 本地代理：解决浏览器跨域（CORS）。
// 教学点：CORS 只是浏览器内的防线——真实攻击者用自定义客户端根本不受它约束。
// 代理同时用于 dev(server) 与 preview(preview)，实弹演示两种方式皆可。
const proxy = {
  // 实弹统一走本地原生服务（live-server.mjs，零浏览器指纹）；/mt-* 直连代理仅作备用
  '/api': { target: 'http://localhost:8787', changeOrigin: true },
  '/mt-app': {
    target: 'https://app.moutai519.com.cn',
    changeOrigin: true,
    rewrite: (p) => p.replace(/^\/mt-app/, ''),
  },
  '/mt-h5': {
    target: 'https://h5.moutai519.com.cn',
    changeOrigin: true,
    rewrite: (p) => p.replace(/^\/mt-h5/, ''),
  },
}

export default defineConfig({
  plugins: [react()],
  base: './',
  server: { port: 5173, proxy },
  preview: { port: 4173, proxy },
})
