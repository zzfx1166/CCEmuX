# CCEmuX with CCWASM Support
一个开源的 ComputerCraft 模拟器，用 Java 编写，支持 WebAssembly (WASM) 功能。

## 新增功能
此版本集成了 CCWASM，允许 ComputerCraft 计算机通过 `wasm` Lua API 加载和执行 WebAssembly 模块。

## 构建
运行 `./gradlew build` 即可。编译后的可运行 JAR 文件位于 `build/libs/CCEmuX-version-all.jar`。

## 运行 CCEmuX

### 方式一：直接使用编译好的 JAR
1. 进入项目目录
2. 运行编译后的 JAR：
   ```bash
   java -jar build/libs/CCEmuX-1.1.0-cct.jar
   ```

### 方式二：使用 CCEmuX Launcher（推荐）
CCEmuX Launcher 是运行 CCEmuX 的推荐方式。以下是使用本地构建版本的步骤：

#### 步骤 1：定位你的构建文件
编译好的 JAR 文件位于：
```
G:\CCEmuX\build\CCEmuX-1.1.0-cct.jar
```

#### 步骤 2：配置 Launcher
1. 打开 CCEmuX Launcher
2. 点击"添加"或"配置"
3. 填写以下信息：
   - **名称**：CCEmuX with CCWASM
   - **路径**：`G:\CCEmuX\build\CCEmuX-1.1.0-cct.jar`
   - **CC:Tweaked 版本**：1.117.0 (core)
   - **Java 版本**：21（或你安装的 Java 版本）

#### 步骤 3：运行
1. 选择你配置的实例
2. 点击"启动"或"运行"

## 使用 CCWASM

### 设置
1. 在 CCEmuX 数据目录中创建 `wasm` 文件夹
   - 默认位置：`%APPDATA%/CCEmuX/wasm/`
   - 或你的 CCEmuX 实例保存计算机数据的其他位置

2. 将你的 WASM 文件放入 `wasm` 文件夹

### 基本用法
在 ComputerCraft 计算机中，你可以使用：

```lua
-- 加载 WASM 模块
local ctx = wasm.load_wasm("my_module")

-- 调用 WASM 函数
local result = ctx.add_numbers(5, 3)
print("结果:", result)

-- 从字符串加载 WASM
local wasmCode = [=[
(module
  (type $t0 (func (result i32)))
  (func $export_func (type $t0) (i32.const 42))
  (export "export_func" (func $export_func))
)
]=]
local ctx2 = wasm.load_wasm_from_string(wasmCode:toBytes())
print("来自字符串:", ctx2.export_func())
```

### 支持的功能
- 从文件系统加载 WASM 文件
- 从 Lua 字符串加载 WASM
- 调用具有各种数据类型（i32、i64、f32、f64、字符串、布尔值）的 WASM 函数
- Lua 和 WASM 之间的对象传递
- AOT 编译支持以提高性能

### 示例 WASM 程序
查看 `ccwasm/example_wasm/` 目录中用 Rust 编写的可在 CCEmuX 中使用的示例程序。

## 故障排除

### Java 版本问题
如果遇到 Java 相关错误：
```bash
# 指定 Java 路径
export JAVA_HOME="/path/to/java21"
java -jar build/libs/CCEmuX-1.1.0-cct.jar
```

### CCWASM 不工作
1. 检查 `wasm` 文件夹是否存在
2. 验证 WASM 文件是否在正确的位置
3. 在控制台中查看 CCWASM 插件加载消息
4. 确保你使用的是正确的 Lua API 语法

### Launcher 检测不到构建
如果 Launcher 没有看到你的构建：
1. 确保 JAR 文件存在于指定路径
2. 尝试刷新 Launcher
3. 检查是否安装了 Java 21
4. 直接运行 JAR 以验证其工作正常

## 项目结构
```
CCEmuX/
├── src/main/java/           # 源代码
│   ├── com/iung/ccwasm/     # CCWASM 集成
│   └── net/clgd/ccemux/     # CCEmuX 核心
├── build/libs/              # 编译后的 JAR
├── ccwasm/                  # 原始 CCWASM 源码
├── example_wasm/           # WASM 示例程序
├── CCWASM_TEST.md          # 详细测试指南
├── LAUNCHER_GUIDE.md       # Launcher 配置指南
├── README.md              # 英文版
├── README_CN.md           # 中文版
└── README.md              # 本文件
```

## 许可证
- CCEmuX: MIT 许可证
- CCWASM: CC0 1.0 通用公共领域
- Chicory (WASM 运行时): Apache 2.0 许可证

## 其他文档
- [Launcher 配置指南](LAUNCHER_GUIDE.md) - 详细说明如何使用 Launcher
- [CCWASM 测试指南](CCWASM_TEST.md) - 详细的功能测试说明

---

*本页面是 [README.md](README.md) 的中文翻译版*