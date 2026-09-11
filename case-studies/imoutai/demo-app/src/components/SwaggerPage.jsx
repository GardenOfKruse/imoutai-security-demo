import { useEffect, useRef } from 'react'
import SwaggerUIBundle from 'swagger-ui-dist/swagger-ui-bundle.js'
import 'swagger-ui-dist/swagger-ui.css'
import spec from '../lib/openapi.json'

/** API 全景 Swagger 文档页（本地打包，离线可用） */
export default function SwaggerPage({ onBack }) {
  const ref = useRef(null)
  useEffect(() => {
    SwaggerUIBundle({
      domNode: ref.current,
      spec,
      docExpansion: 'list',
      defaultModelsExpandDepth: 1,
      tryItOutEnabled: false,
      supportedSubmitMethods: [],
    })
  }, [])
  return (
    <div className="swaggerpage">
      <div className="swagger-top">
        <button className="btn ghost" onClick={onBack}>← 返回</button>
        <div className="swagger-title">
          <b>📚 i茅台 App 后端接口全景</b>
          <span className="swagger-stats">
            {spec.info['x-extraction-stats'].endpoints_documented} 个接口 ·{' '}
            {spec.info['x-extraction-stats'].request_models} 个请求模型 · 运行时取证自动生成
          </span>
        </div>
      </div>
      <div className="warnbox" style={{ margin: '12px 0' }}>
        🚫 红线：本文档为<b>授权安全测试的取证产出</b>，仅用于开发者培训演示与授权窗口内的测试执行。
        支付相关接口（order/pay）<b>仅文档化，禁止真实调用</b>。文档由 <code>hooks/gen_openapi.py</code> 从运行时取证数据自动生成，可复现。
      </div>
      <div ref={ref} className="swagger-mount" />
    </div>
  )
}
