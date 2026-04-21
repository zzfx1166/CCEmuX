# CCEmuX with CCWASM Support
A new open source CC emulator, written in Java, with enhanced WebAssembly (WASM) support.

## What's New
This version includes CCWASM integration, allowing ComputerCraft computers to load and execute WebAssembly modules using the `wasm` Lua API.

## Building
As simple as running `./gradlew build`. The compiled, runnable jar will be written to `build/libs/CCEmuX-version-all.jar`.

## Running CCEmuX

### Option 1: Using the Compiled JAR Directly
1. Navigate to the project directory
2. Run the compiled JAR:
   ```bash
   java -jar build/libs/CCEmuX-1.1.0-cct.jar
   ```

### Option 2: Using CCEmuX Launcher (Recommended)
The official CCEmuX Launcher is the recommended way to run CCEmuX. Here's how to use it with your local build:

#### Step 1: Locate Your Build
Your compiled JAR will be at:
```
G:\CCEmuX\build\CCEmuX-1.1.0-cct.jar
```

#### Step 2: Configure Launcher
1. Open CCEmuX Launcher
2. Click "Add" or "Configure"
3. Fill in the details:
   - **Name**: CCEmuX with CCWASM
   - **Path**: `G:\CCEmuX\build\CCEmuX-1.1.0-cct.jar`
   - **CC:Tweaked Version**: 1.117.0 (core)
   - **Java Version**: 21 (or your installed Java version)

#### Step 3: Run
1. Select your configured instance
2. Click "Launch" or "Run"

## Using CCWASM

### Setup
1. Create a `wasm` folder in your CCEmuX data directory
   - Default location: `%APPDATA%/CCEmuX/wasm/`
   - Or where your CCEmuX instance saves computer data

2. Place your WASM files in the `wasm` folder

### Basic Usage
In a ComputerCraft computer, you can now use:

```lua
-- Load a WASM module
local ctx = wasm.load_wasm("my_module")

-- Call WASM functions
local result = ctx.add_numbers(5, 3)
print("Result:", result)

-- Load WASM from string
local wasmCode = [=[
(module
  (type $t0 (func (result i32)))
  (func $export_func (type $t0) (i32.const 42))
  (export "export_func" (func $export_func))
)
]=]
local ctx2 = wasm.load_wasm_from_string(wasmCode:toBytes())
print("From string:", ctx2.export_func())
```

### Supported Features
- Loading WASM files from the filesystem
- Loading WASM from Lua strings
- Calling WASM functions with various data types (i32, i64, f32, f64, strings, booleans)
- Object passing between Lua and WASM
- AOT compilation support for better performance

### Example WASM Programs
See the `ccwasm/example_wasm/` directory for example programs written in Rust that can be used with CCEmuX.

## Troubleshooting

### Java Version Issues
If you encounter Java-related errors:
```bash
# Specify Java path
export JAVA_HOME="/path/to/java21"
java -jar build/libs/CCEmuX-1.1.0-cct.jar
```

### CCWASM Not Working
1. Check that the `wasm` folder exists
2. Verify WASM files are in the correct location
3. Look for CCWASM plugin loaded messages in the console
4. Ensure you're using the correct Lua API syntax

### Launcher Not Detecting Build
If the launcher doesn't see your build:
1. Ensure the JAR file exists at the specified path
2. Try refreshing the launcher
3. Check that you have Java 21 installed
4. Run the JAR directly to verify it works

## Project Structure
```
CCEmuX/
├── src/main/java/           # Source code
│   ├── com/iung/ccwasm/     # CCWASM integration
│   └── net/clgd/ccemux/     # CCEmuX core
├── build/libs/              # Compiled JARs
├── ccwasm/                  # Original CCWASM source
├── CCWASM_TEST.md          # Detailed testing guide
└── README.md              # This file
```

## License
- CCEmuX: MIT License
- CCWASM: CC0 1.0 Universal
- Chicory (WASM runtime): Apache 2.0 License

## 中文文档
[简体中文 README](README_CN.md) - 中文版使用说明，包含详细的配置和测试指南。