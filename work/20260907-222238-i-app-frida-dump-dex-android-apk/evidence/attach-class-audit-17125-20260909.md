# late attach 类审计记录

日期：2026-09-09  
设备：`82e459fc0920`  
目标进程：PID `17125`

## 实际执行

- 编译了 `case-studies/imoutai/hooks/attach-class-audit.js`。
- 使用 `case-studies/imoutai/hooks/attach-class-audit-driver.py` 对已经运行的干净进程做 late attach。
- 审计脚本仅请求已加载类和类加载器列表；没有修改返回值，没有调用网络，没有主动加载库或调用初始化函数。

## 原始结果

~~~text
[*] attached pid=17125 (read-only audit)
[!] audit rpc failed: script has been destroyed
[!] session detached reason=process-terminated (None,)
[*] pid_alive_after_attach=False exit=1
~~~

没有生成 `class-audit.json` 或 `app-classes.txt`。现象只证明该进程在审计 RPC 返回前终止；具体终止原因未从本次实验中确认。

## 原始日志

`extract/attach-class-audit-17125/events.log`
