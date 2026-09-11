"""Dump small IDA views around selected string xrefs for local evidence."""
import os

import ida_auto
import ida_bytes
import ida_funcs
import ida_hexrays
import idaapi
import idautils
import idc


def main():
    ida_auto.auto_wait()
    out_path = os.environ.get("IDA_XREF_OUT", os.path.join(os.getcwd(), "ida-xrefs.txt"))
    targets = [0x3560, 0x3568, 0x30DC, 0x30E4]
    with open(out_path, "w", encoding="utf-8") as out:
        out.write("input=" + idaapi.get_input_file_path() + "\n")
        for target in targets:
            out.write("\n=== xref 0x%x ===\n" % target)
            fn = ida_funcs.get_func(target)
            out.write("function=" + (hex(fn.start_ea) if fn else "NONE") + "\n")
            if fn:
                try:
                    out.write("name=" + (idc.get_name(fn.start_ea, idc.GN_VISIBLE) or "") + "\n")
                    cfunc = ida_hexrays.decompile(fn)
                    if cfunc:
                        out.write(str(cfunc)[:20000] + "\n")
                except Exception as exc:
                    out.write("decompile_error=" + repr(exc) + "\n")
            start = max(0, target - 0x80)
            ea = start
            while ea < target + 0x180:
                mnem = idc.print_insn_mnem(ea)
                if mnem:
                    out.write("%x: %s %s\n" % (ea, mnem, idc.generate_disasm_line(ea, 0) or ""))
                nxt = idc.next_head(ea, target + 0x180)
                if nxt <= ea:
                    break
                ea = nxt
        out.write("\n=== all xrefs to /proc/self/maps string ===\n")
        for s in idautils.Strings():
            if str(s) == "/proc/self/maps":
                for x in idautils.XrefsTo(int(s.ea), 0):
                    out.write(hex(x.frm) + "\n")
    print("IDA_XREF_OUT=" + out_path)
    idc.qexit(0)


if __name__ == "__main__":
    main()
