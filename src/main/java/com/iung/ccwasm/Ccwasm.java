package com.iung.ccwasm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * CCWASM constants and utilities for CCEmuX integration.
 * Original source from CCWASM mod (CC0 1.0 Universal).
 */
public class Ccwasm {
    public static final String MOD_ID = "ccwasm";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Path WASM_ROOT = Path.of("./wasm");

    // No mod constructor needed for CCEmuX
}