# IDA MCP supervisor 启动失败记录

日期：2026-09-09

## 实际执行

- 确认本机 `E:/Program Files/IDA Professional 9.3` 路径存在。
- 设置当前进程 `IDADIR` 后，按确定性启动脚本启动 supervisor。
- 启动尝试返回超时，随后检查到服务端口 `13337` 未监听、进程已退出。

## 原始错误

~~~text
WARN: streamable-http patch skipped: No module named 'ida_pro_mcp.idalib_supervisor'
Traceback ...
ModuleNotFoundError: No module named 'ida_pro_mcp.idalib_supervisor'
~~~

本次未通过 IDA MCP 打开或继续分析样本。该结果是工具环境状态，不是 APK、壳或检测逻辑判断。

## 原始日志

`C:/Users/76327/AppData/Local/reverse-skill/ida-mcp/supervisor.log`
