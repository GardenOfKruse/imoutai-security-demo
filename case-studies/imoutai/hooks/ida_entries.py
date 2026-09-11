"""Export IDA entry points and segment layout for a local ELF image."""
import os
import ida_auto
import idaapi
import idc


def main():
    ida_auto.auto_wait()
    out_path = os.environ.get("IDA_ENTRIES_OUT", os.path.join(os.getcwd(), "ida-entries.txt"))
    with open(out_path, "w", encoding="utf-8") as out:
        out.write("input=" + idaapi.get_input_file_path() + "\n")
        out.write("entry_qty=%d\n" % idaapi.get_entry_qty())
        for i in range(idaapi.get_entry_qty()):
            ea = idaapi.get_entry(idaapi.get_entry_ordinal(i))
            out.write("entry ordinal=%d ea=%s name=%s\n" %
                      (idaapi.get_entry_ordinal(i), hex(ea), idaapi.get_entry_name(ea)))
        out.write("segments:\n")
        for i in range(idaapi.get_segm_qty()):
            seg = idaapi.getnseg(i)
            if seg:
                out.write("  %s-%s %s perm=%x\n" %
                          (hex(seg.start_ea), hex(seg.end_ea), idaapi.get_segm_name(seg), seg.perm))
    print("IDA_ENTRIES_OUT=" + out_path)
    idc.qexit(0)


if __name__ == "__main__":
    main()
