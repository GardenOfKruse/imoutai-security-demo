"""IDA headless triage for the local libDexHelper runtime image."""
import json
import os

import ida_auto
import ida_bytes
import ida_funcs
import ida_idaapi
import ida_nalt
import idaapi
import idautils
import idc


def safe_name(ea):
    try:
        return idc.get_name(ea, idc.GN_VISIBLE) or ""
    except Exception:
        return ""


def main():
    ida_auto.auto_wait()
    out_path = os.environ.get("IDA_TRIAGE_OUT")
    if not out_path:
        out_path = os.path.join(os.getcwd(), "ida-triage-libdexhelper.json")
    imports = []
    for idx in range(ida_nalt.get_import_module_qty()):
        mod = ida_nalt.get_import_module_name(idx) or ""
        names = []

        def cb(ea, name, ordinal):
            names.append({"ea": hex(ea), "name": name or "", "ordinal": ordinal})
            return True

        ida_nalt.enum_import_names(idx, cb)
        imports.append({"module": mod, "symbols": names})

    strings = []
    needles = ("/proc", "maps", "frida", "gum", "ptrace", "dlopen", "JNI",
               "RegisterNatives", "Dex", "abort", "tgkill", "pthread", "secret",
               "sign", "hmac", "md5", "sha", "encrypt", "decrypt")
    for s in idautils.Strings():
        text = str(s)
        if any(n.lower() in text.lower() for n in needles):
            ea = int(s.ea)
            xrefs = [hex(x.frm) for x in idautils.XrefsTo(ea, 0)]
            strings.append({"ea": hex(ea), "text": text[:500], "xrefs_to": xrefs[:64]})

    funcs = []
    for ea in idautils.Functions():
        name = safe_name(ea)
        if any(n.lower() in name.lower() for n in needles) or name in ("JNI_OnLoad",):
            funcs.append({"ea": hex(ea), "name": name, "size": ida_funcs.calc_func_size(ida_funcs.get_func(ea))})

    exports = []
    for i in range(idaapi.get_segm_qty()):
        seg = idaapi.getnseg(i)
        if not seg:
            continue
        ea = seg.start_ea
        while ea < seg.end_ea:
            name = safe_name(ea)
            if name:
                flags = ida_bytes.get_full_flags(ea)
                if ida_bytes.is_code(flags):
                    exports.append({"ea": hex(ea), "name": name})
            ea = idaapi.next_head(ea, seg.end_ea)
    data = {
        "input": idaapi.get_input_file_path(),
        "imagebase": hex(idaapi.get_imagebase()),
        "imports": imports,
        "import_symbol_count": sum(len(x["symbols"]) for x in imports),
        "interesting_strings": strings,
        "interesting_functions": funcs,
        "named_code_count": len(exports),
        "named_code": exports[:1000],
    }
    with open(out_path, "w", encoding="utf-8") as fh:
        json.dump(data, fh, ensure_ascii=False, indent=2)
    print("IDA_TRIAGE_OUT=" + out_path)
    print("IDA_IMPORT_MODULES=" + str(len(imports)))
    print("IDA_IMPORT_SYMBOLS=" + str(data["import_symbol_count"]))
    print("IDA_INTERESTING_STRINGS=" + str(len(strings)))
    print("IDA_INTERESTING_FUNCTIONS=" + str(len(funcs)))
    idc.qexit(0)


if __name__ == "__main__":
    main()
