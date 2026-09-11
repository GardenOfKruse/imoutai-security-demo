"""Export a bounded disassembly window from an already analysed IDA database."""
import os

import ida_auto
import idaapi
import idc


def main():
    ida_auto.auto_wait()
    start = int(os.environ.get("IDA_RANGE_START", "0x3000"), 0)
    end = int(os.environ.get("IDA_RANGE_END", "0x4100"), 0)
    out_path = os.environ.get("IDA_RANGE_OUT", os.path.join(os.getcwd(), "ida-disasm-range.txt"))
    with open(out_path, "w", encoding="utf-8") as out:
        out.write(f"input={idaapi.get_input_file_path()}\n")
        out.write(f"range={start:#x}-{end:#x}\n")
        ea = start
        while ea < end:
            line = idc.generate_disasm_line(ea, 0)
            if line:
                out.write(f"{ea:08x}: {line}\n")
            nxt = idc.next_head(ea, end)
            if nxt <= ea:
                ea += 4
            else:
                ea = nxt
    print("IDA_RANGE_OUT=" + out_path)
    idc.qexit(0)


if __name__ == "__main__":
    main()
