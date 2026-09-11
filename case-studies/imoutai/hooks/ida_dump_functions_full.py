#!/usr/bin/env python3
"""Export complete Hex-Rays text for selected functions from an existing IDA DB."""

import os

import ida_auto
import ida_funcs
import ida_hexrays
import idaapi
import idc


def main() -> None:
    ida_auto.auto_wait()
    out_path = os.environ.get(
        "IDA_FULL_DECOMP_OUT",
        os.path.join(os.getcwd(), "ida-full-decomp.txt"),
    )
    names = tuple(
        name.strip()
        for name in os.environ.get(
            "IDA_FULL_DECOMP_NAMES",
            "sub_3A178,sub_12F00,sub_10220,sub_1E33C",
        ).split(",")
        if name.strip()
    )
    with open(out_path, "w", encoding="utf-8") as out:
        out.write("input=" + idaapi.get_input_file_path() + "\n")
        out.write("names=" + ",".join(names) + "\n")
        for name in names:
            ea = idc.get_name_ea(idaapi.BADADDR, name)
            out.write("\n=== REQUESTED %s ea=%#x ===\n" % (name, ea))
            if ea == idaapi.BADADDR:
                out.write("NOT_FOUND\n")
                continue
            fn = ida_funcs.get_func(ea)
            if not fn:
                out.write("NO_FUNCTION\n")
                continue
            out.write(
                "range=%#x-%#x actual_name=%s\n"
                % (fn.start_ea, fn.end_ea, idc.get_name(fn.start_ea, idc.GN_VISIBLE))
            )
            try:
                cfunc = ida_hexrays.decompile(fn)
                out.write(str(cfunc) if cfunc else "DECOMPILE_NONE")
                out.write("\n")
            except Exception as exc:
                out.write("DECOMPILE_ERROR=" + repr(exc) + "\n")
    print("IDA_FULL_DECOMP_OUT=" + out_path)
    idc.qexit(0)


if __name__ == "__main__":
    main()
