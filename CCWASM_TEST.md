# CCWASM 集成测试指南

## 概述
CCEmuX 已集成 CCWASM 功能，支持在模拟计算机中加载和执行 WebAssembly (WASM) 模块。

## 启动方式

### 使用 CCEmuX Launcher（推荐）
1. 参考 `LAUNCHER_GUIDE.md` 配置 Launcher
2. 确保使用编译后的 JAR：`G:\CCEmuX\build\CCEmuX-1.1.0-cct.jar`
3. 启动后检查控制台是否有 CCWASM 加载信息

### 直接运行 JAR
```cmd
java -jar build/libs/CCEmuX-1.1.0-cct.jar
```

## 测试步骤

### 1. 编译项目
```bash
./gradlew build
```
构建成功后，编译后的 JAR 文件位于 `build/libs/CCEmuX-1.1.0-cct.jar`。

### 2. 验证插件加载
启动 CCEmuX 并检查日志，应看到以下内容：
```
[CCWASM] CCWASM mod initialized
```
或者类似的插件加载信息。

### 3. 创建 WASM 文件
将以下简单的 WASM 示例保存为 `simple.wasm`，放置在 CCEmuX 数据目录的 `wasm` 文件夹中：

#### simple.wasm (Rust 示例)
```rust
#![no_std]
#[no_mangle]
pub extern "C" fn add(a: i32, b: i32) -> i32 {
    a + b
}

#[no_mangle]
pub extern "C" fn greet(name: i32) -> i32 {
    // 假设传入的是字符串地址
    let msg = unsafe { core::str::from_utf8_unchecked(core::slice::from_raw_parts(name as *const u8, 5)) };
    print!("Hello, {}!", msg);
    0
}

#[no_mangle]
pub extern "C" fn export_func() {
    // 导出函数，触发 Lua 方法的注册
}
```

### 4. 在 CCEmuX 中测试
1. 启动 CCEmuX
2. 创建一个新的计算机
3. 在计算机中运行以下 Lua 代码：

```lua
-- 检查 wasm API 是否存在
print("wasm API type:", type(wasm))

-- 加载 WASM 模块
local ctx = wasm.load_wasm("simple")
print("WASM module loaded:", ctx ~= nil)

-- 调用 add 函数
if ctx then
    local result = ctx.add(5, 3)
    print("5 + 3 =", result)
    
    -- 调用 greet 函数
    local result2 = ctx.greet(1)  -- 传入地址
    print("Greet result:", result2)
end
```

### 5. 预期结果
- 打印 `wasm API type: table`，表明 API 已成功注册
- 打印 `WASM module loaded: true`，表明 WASM 模块加载成功
- 调用 `add(5, 3)` 应返回 `8`
- 调用 `greet` 应在控制台打印出问候信息

### 6. 错误排查

#### 如果出现 "wasm API type: nil"：
- 检查 CCWASMPlugin 是否加载成功（查看日志）
- 确认 `src/main/resources/META-INF/services/net.clgd.ccemux.api.plugins.Plugin` 文件正确

#### 如果出现 "Failed to read WASM file"：
- 确保 `wasm` 文件夹存在
- 将 `simple.wasm` 放入正确的位置
- 检查文件权限

#### 如果出现 WASM 执行错误：
- 检查 WASM 文件格式是否正确
- 确保导出的函数名称匹配
- 查看 CCEmuX 日志中的详细错误信息

## 高级测试

### 测试 WASM 字符串加载
```lua
-- 从字符串加载 WASM
local wasmCode = [=[
(module
  (type $t0 (func (result i32)))
  (type $t1 (func (param i32) (result i32)))
  (func $add (type $t1) (local $l0 i32) (local $l1 i32) (i32.add
    (local.get $l0)
    (local.get $l1)
  ))
  (func $export_func (type $t0) (i32.const 0))
  (export "export_func" (func $export_func))
  (export "add" (func $add))
)
]=]

local ctx2 = wasm.load_wasm_from_string(wasmCode:toBytes())
if ctx2 then
    local result = ctx2.add(10, 20)
    print("10 + 20 =", result)
end
```

### 测试文件系统挂载
```lua
-- WASM 根目录应该被挂载在 /wasm
local handle = fs.open("/wasm/simple.wasm", "r")
if handle then
    print("WASM file accessible")
    handle.close()
else
    print("WASM file not accessible")
end
```

## 故障排除

### 常见问题
1. **插件未加载**：检查 `auto-service` 注解是否生效
2. **依赖缺失**：确认 Chicory 库已正确添加到构建中
3. **路径问题**：确保 `WASM_ROOT` 路径正确指向 wasm 目录
4. **API 兼容性**：确认 ComputerCraft API 版本兼容

### 日志调试
启用详细日志查看：
```bash
./gradlew run --args="--debug"
```

或查看 CCEmuX 配置文件中的日志级别设置。

## 性能考虑
- WASM 模块首次加载时会有编译开销（如果使用 AOT）
- 内存使用量取决于 WASM 模块的大小和复杂度
- 可以通过配置禁用 AOT 编译以加快首次加载速度

## 完成测试清单
- [ ] CCEmuX 编译成功
- [ ] CCWASMPlugin 加载成功
- [ ] WASM 文件加载成功
- [ ] WASM 函数调用成功
- [ ] 字符串加载功能正常
- [ ] 文件系统挂载正常
- [ ] 错误处理机制正常