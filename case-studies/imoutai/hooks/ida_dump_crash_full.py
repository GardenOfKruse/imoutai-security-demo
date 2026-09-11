"""Export IDA function/decompiler evidence for runtime crash-relative offsets."""

import os

import ida_auto
import ida_bytes
import ida_funcs
import ida_hexrays
import idaapi
import idautils
import idc


TARGETS = (0x23974, 0x16704, 0x180C)


def main() -> None:
    ida_auto.auto_wait()
    out_path = os.environ.get("IDA_CRASH_OUT", os.path.join(os.getcwd(), "ida-crash-functions.txt"))
    with open(out_path, "w", encoding="utf-8") as out:
        out.write("input=" + idaapi.get_input_file_path() + "\n")
        out.write("targets=" + ",".join(hex(x) for x in TARGETS) + "\n")
        for ea in TARGETS:
            fn = ida_funcs.get_func(ea)
            out.write("\n=== target=%#x ===\n" % ea)
            if not fn:
                out.write("function=NONE\n")
                continue
            out.write(
                "function=%#x-%#x name=%s\n"
                % (fn.start_ea, fn.end_ea, idc.get_name(fn.start_ea, idc.GN_VISIBLE) or "")
            )
            out.write("target_offset=%#x\n" % (ea - fn.start_ea))
            out.write("instruction=%s\n" % (idc.generate_disasm_line(ea, 0) or ""))
            out.write("disassembly_window:\n")
            window_start = max(fn.start_ea, ea - 0x60)
            window_end = min(fn.end_ea, ea + 0x60)
            cur = window_start
            while cur < window_end:
                line = idc.generate_disasm_line(cur, 0)
                if line:
                    out.write("  %#x: %s\n" % (cur, line))
                nxt = idc.next_head(cur, window_end)
                cur = nxt if nxt > cur else cur + 4
            out.write("callers:\n")
            for xref in idautils.XrefsTo(fn.start_ea, 0):
                out.write("  %#x\n" % xref.frm)
            out.write("decompilation:\n")
            try:
                cfunc = ida_hexrays.decompile(fn)
                out.write(str(cfunc) if cfunc else "DECOMPILE_NONE")
            except Exception as exc:
                out.write("DECOMPILE_ERROR=" + repr(exc))
            out.write("\n")
            out.write("bytes:")
            try:
                out.write(" " + ida_bytes.get_bytes(fn.start_ea, min(32, fn.end_ea - fn.start_ea)).hex())
            except Exception as exc:
                out.write(" ERROR=" + repr(exc))
            out.write("\n")
    print("IDA_CRASH_OUT=" + out_path)
    idc.qexit(0)


if __name__ == "__main__":
    main()
