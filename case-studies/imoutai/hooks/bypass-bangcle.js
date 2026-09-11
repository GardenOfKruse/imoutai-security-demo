
function hook_dlopen(so_name) {
    Interceptor.attach(Module.findGlobalExportByName("android_dlopen_ext"), {
        onEnter: function (args) {
            var pathptr = args[0]
            if (pathptr != undefined && pathptr != null) {
                var path = pathptr.readCString()
                console.log(path)
                if (path.indexOf(so_name) !== -1) {
                    this.match = true
                }
            }
        },
        onLeave: function () {
            if (this.match) {
                console.log(so_name, "加载成功");
            }
        }
    })
}

function create_pthread_create() {
    const pthread_create_addr = Module.findGlobalExportByName("pthread_create")
    const pthread_create = new NativeFunction(pthread_create_addr, "int", ["pointer", "pointer", "pointer", "pointer"]);
    return new NativeCallback((parg0, parg1, parg2, parg3) => {
        const module = Process.findModuleByAddress(parg2);
        if (module) {
            const so_name = module.name;
            const baseAddr = module.base
            console.log("[pthread_create] ----->", so_name);
            if (so_name.indexOf("libutils.so") !== -1 ||
                so_name.indexOf("libDexHelper" !== -1)) {
                console.log("pthread_create", so_name, "0x" + parg2.sub(baseAddr).toString(16), "0x" + parg3.toString(16))
                return 0;
            }
            // 成功的返回值是0
        }
        return pthread_create(parg0, parg1, parg2, parg3)
    }, "int", ["pointer", "pointer", "pointer", "pointer"])
}

// 或者
function replace_thread() {
    var new_pthread_create = create_pthread_create()
    var pthread_create_addr = Module.findGlobalExportByName("pthread_create")
    // 函数替换
    Interceptor.replace(pthread_create_addr, new_pthread_create);
}

replace_thread()

hook_dlopen("libDexHelper.so")