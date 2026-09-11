"""Export callers and compact control-flow evidence for selected local functions."""
import os
import ida_auto
import ida_hexrays
import idaapi
import ida_funcs
import idautils
import idc


def main():
    ida_auto.auto_wait()
    out_path = os.environ.get("IDA_CALLERS_OUT", os.path.join(os.getcwd(), "ida-callers.txt"))
    targets = [0x303C, 0x3184, 0x33F8, 0x2A60, 0x2A80, 0x2AE0]
    with open(out_path, "w", encoding="utf-8") as out:
        out.write("input=" + idaapi.get_input_file_path() + "\n")
        for ea in targets:
            fn = ida_funcs.get_func(ea)
            out.write("\n=== target 0x%x function=%s ===\n" % (ea, hex(fn.start_ea) if fn else "NONE"))
            if fn:
                out.write("name=" + (idc.get_name(fn.start_ea, idc.GN_VISIBLE) or "") + "\n")
                try:
                    c = ida_hexrays.decompile(fn)
                    if c:
                        text = str(c)
                        out.write(text[:30000] + "\n")
                except Exception as exc:
                    out.write("decompile_error=" + repr(exc) + "\n")
            out.write("callers:\n")
            for x in idautils.XrefsTo(ea, 0):
                caller = ida_funcs.get_func(x.frm)
                out.write("  " + hex(x.frm) + " from " + (hex(caller.start_ea) if caller else "NONE") + "\n")
        out.write("\n=== named functions containing load/check keywords ===\n")
        for ea in idautils.Functions():
            name = idc.get_name(ea, idc.GN_VISIBLE) or ""
            if any(k in name.lower() for k in ("jni", "load", "check", "verify", "init")):
                out.write(hex(ea) + " " + name + "\n")
    print("IDA_CALLERS_OUT=" + out_path)
    idc.qexit(0)


if __name__ == "__main__":
    main()
