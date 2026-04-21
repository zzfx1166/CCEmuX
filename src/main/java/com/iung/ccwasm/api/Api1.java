package com.iung.ccwasm.api;



import com.iung.ccwasm.Ccwasm;
import com.iung.ccwasm.WasmCtx;
import dan200.computercraft.api.lua.*;
import dan200.computercraft.core.filesystem.FileMount;


import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Path;


public class Api1 implements ILuaAPI {
    static String[] TARGET_GLO = {"wasm"};
    static FileMount WASM_ROOT;

    static {
        WASM_ROOT = new FileMount(Ccwasm.WASM_ROOT);
    }

    private final IComputerSystem cs;

    public Api1(IComputerSystem iComputerSystem) {
        cs = iComputerSystem;
    }


    @Override
    public String[] getNames() {
        return TARGET_GLO;
    }


    @LuaFunction
    public final WasmCtx load_wasm(ILuaContext ctx, IArguments args) throws LuaException {
        try {
            Path p = Path.of(args.getString(0).chars().filter(c -> Character.isDigit(c) | Character.isAlphabetic(c) | c == '_' | c == '-').collect(StringBuilder::new, StringBuilder::appendCodePoint,
                    StringBuilder::append) + ".wasm");
            Path p1 = Ccwasm.WASM_ROOT.resolve(p);
            boolean useAoT = true;
            if (args.count() >= 2 && args.get(1) instanceof Boolean) {
                useAoT = (Boolean) args.get(1);
//                Ccwasm.LOGGER.info("use aot: {}", useAoT);
//                Ccwasm.LOGGER.info("type: {}", args.getType(1));
            }
            return new WasmCtx(p1.toFile(), useAoT);
        } catch (Exception e) {
            throw new LuaException(e.getMessage());
        }
    }

    @LuaFunction
    public final WasmCtx load_wasm_from_string(ILuaContext ctx, IArguments args) throws LuaException {
        try {
            ByteBuffer buf = args.getBytes(0);
            byte[] bytes = new byte[buf.remaining()];
            buf.get(bytes);
            boolean useAoT = true;
            if (args.count() >= 2 && args.get(1) instanceof Boolean) {
                useAoT = (Boolean) args.get(1);
            }
            return new WasmCtx(bytes, useAoT);
        } catch (Exception e) {
            throw new LuaException(e.getMessage());
        }
    }

    @Override
    public void startup() {
        cs.mount("wasm", WASM_ROOT);
        ILuaAPI.super.startup();
    }
}