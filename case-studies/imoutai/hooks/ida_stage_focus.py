#!/usr/bin/env python3
'''Export focused IDA cross-references for the recovered libDexHelper stage.'''
import os

import ida_auto
import ida_funcs
import ida_hexrays
import idaapi
import idautils
import idc

NEEDLES = (
    "JNI_OnLoad",
    "KEY_RES_ENC",
    "SHAREFOLDER",
    "BLACKDEXA",
    "Anonymous-DexFile",
    "InMemoryDexClassLoader",
    "makeInMemoryDexElements",
    "dex\n035",
    "classes.dve",
    "/proc/self/maps",
    "stamp-cert-sha256",
    "base.apk",
)

def function_info(ea):
    fn = ida_funcs.get_func(ea)
    if not fn:
        return None
    return {
        "start": fn.start_ea,
        "end": fn.end_ea,
        "name": idc.get_name(fn.start_ea, idc.GN_VISIBLE) or "",
    }

def main():
    ida_auto.auto_wait()
    out_path = os.environ.get("IDA_STAGE_FOCUS_OUT", os.path.join(os.getcwd(), "ida-stage-focus.txt"))
    string_hits = []
    function_eas = set()

    for item in idautils.Strings():
        value = str(item)
        if not any(needle in value for needle in NEEDLES):
            continue
        refs = []
        for ref in idautils.XrefsTo(item.ea, 0):
            info = function_info(ref.frm)
            refs.append({"xref": ref.frm, "function": info})
            if info:
                function_eas.add(info["start"])
        string_hits.append({
            "ea": item.ea,
            "text": value[:1000],
            "xrefs": refs,
        })

    jni = ida_funcs.get_func(idaapi.get_name_ea(idaapi.BADADDR, "JNI_OnLoad"))
    if jni:
        function_eas.add(jni.start_ea)

    ordered = sorted(function_eas)
    with open(out_path, "w", encoding="utf-8") as out:
        out.write("input=" + idaapi.get_input_file_path() + "\n")
        out.write("string_hits=" + str(len(string_hits)) + "\n")
        for hit in string_hits:
            out.write("\nSTRING ea=%#x text=%r\n" % (hit["ea"], hit["text"]))
            for ref in hit["xrefs"]:
                out.write("  XREF %#x function=%s\n" % (
                    ref["xref"],
                    repr(ref["function"]) if ref["function"] else "NONE",
                ))
        out.write("\nFOCUSED_FUNCTIONS=" + str(len(ordered)) + "\n")
        for ea in ordered[:32]:
            fn = ida_funcs.get_func(ea)
            if not fn:
                continue
            name = idc.get_name(ea, idc.GN_VISIBLE) or ""
            out.write("\n=== FUNCTION %#x-%#x %s ===\n" % (fn.start_ea, fn.end_ea, name))
            try:
                cfunc = ida_hexrays.decompile(fn)
                out.write((str(cfunc) if cfunc else "DECOMPILE_NONE")[:24000] + "\n")
            except Exception as exc:
                out.write("DECOMPILE_ERROR=" + repr(exc) + "\n")
    print("IDA_STAGE_FOCUS_OUT=" + out_path)
    print("IDA_STAGE_FOCUS_STRINGS=" + str(len(string_hits)))
    print("IDA_STAGE_FOCUS_FUNCTIONS=" + str(len(ordered)))
    idc.qexit(0)

if __name__ == "__main__":
    main()

