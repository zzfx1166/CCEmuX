# CCEmuX Launcher 配置指南

## 概述
本指南说明如何使用 CCEmuX Launcher 运行本地编译的 CCEmuX 版本（包含 CCWASM 支持）。

## 方法一：直接使用 JAR 文件（推荐用于测试）

### 步骤
1. **打开命令提示符或 PowerShell**
2. **导航到项目目录**
   ```cmd
   cd G:\CCEmuX
   ```
3. **运行 JAR 文件**
   ```cmd
   java -jar build/libs/CCEmuX-1.1.0-cct.jar
   ```

### 可选参数
```cmd
# 指定 Java 路径（如果需要）
java -Dorg.gradle.java.home="C:\Program Files\Java\jdk-21" -jar build/libs/CCEmuX-1.1.0-cct.jar

# 增加内存（如果需要）
java -Xmx2G -jar build/libs/CCEmuX-1.1.0-cct.jar

# 启用调试模式
java -Dccemux.debug=true -jar build/libs/CCEmuX-1.1.0-cct.jar
```

## 方法二：配置 CCEmuX Launcher

### 步骤 1：查找编译好的 JAR
编译后的 JAR 文件位置：
```
G:\CCEmuX\build\CCEmuX-1.1.0-cct.jar
```

### 步骤 2：打开 Launcher
1. 启动 CCEmuX Launcher
2. 如果已有实例，点击"Edit"或"Add New"
3. 如果没有实例，点击"Add Instance"

### 步骤 3：填写配置
| 字段 | 值 | 说明 |
|------|-----|------|
| **Name** | CCEmuX with CCWASM | 自定义名称 |
| **Path** | `G:\CCEmuX\build\CCEmuX-1.1.0-cct.jar` | JAR 文件完整路径 |
| **CC:Tweaked Version** | 1.117.0 | core 版本 |
| **Java Version** | 21 | 使用 Java 21 |
| **Memory** | 1-2 GB | 根据需要调整 |
| **Working Directory** | `G:\CCEmuX` | 可选，保持默认 |

### 步骤 4：保存并运行
1. 点击"Save"或"Done"
2. 选择你的新实例
3. 点击"Launch"或"Play"

## 方法三：创建批处理文件（一键启动）

创建一个 `launch.bat` 文件：
```batch
@echo off
echo Starting CCEmuX with CCWASM support...
java -Xmx2G -jar "G:\CCEmuX\build\CCEmuX-1.1.0-cct.jar"
pause
```

使用方法：
1. 将上述代码保存为 `launch.bat` 在 `G:\CCEmuX` 目录
2. 双击运行批处理文件

## 方法四：创建桌面快捷方式

### 创建快捷方式步骤：
1. 右键点击桌面 → 新建 → 快捷方式
2. 位置：
   ```
   javaw.exe -Xmx2G -jar "G:\CCEmuX\build\CCEmuX-1.1.0-cct.jar"
   ```
3. 命名：CCEmuX with CCWASM
4. 完成创建

### 高级快捷方式选项（可选）：
```
javaw.exe -Dccemux.debug=true -Dccemux.data.dir="G:\MyCCEmuX" -Xmx2G -jar "G:\CCEmuX\build\CCEmuX-1.1.0-cct.jar"
```

## 验证 CCWASM 是否工作

启动后，你可以通过以下步骤验证 CCWASM 是否正确集成：

1. **创建新计算机**
   - 在 CCEmuX 界面创建一个新的 ComputerCraft 计算机

2. **检查插件加载**
   - 查看 CCEmuX 控制台输出，应有类似信息：
   ```
   [CCWASM] CCWASM plugin loaded
   ```

3. **测试 WASM 功能**
   - 在计算机中运行：
   ```lua
   print("wasm API type:", type(wasm))
   ```
   - 应输出：`wasm API type: table`

4. **加载示例 WASM**
   - 将示例 WASM 文件放入 `wasm` 文件夹
   - 运行测试代码

## 常见问题

### Q: Launcher 找不到 JAR 文件
**A:** 确保路径正确，并且 JAR 文件存在。路径中不要有中文或特殊字符。

### Q: Java 版本不兼容
**A:**
```cmd
# 查看已安装的 Java 版本
java -version

# 如果没有 Java 21，下载安装：
# https://adoptium.net/temurin/releases/?version=21
```

### Q: 内存不足
**A:** 在快捷方式或命令中增加内存：
```cmd
java -Xmx4G -jar build/libs/CCEmuX-1.1.0-cct.jar
```

### Q: CCWASM 没有加载
**A:**
1. 检查 `wasm` 文件夹是否存在
2. 查看控制台日志是否有错误
3. 确保 JAR 文件包含 CCWASM 相关类

## 数据目录位置

CCEmuX 默认数据目录：
- **Windows**: `%APPDATA%/CCEmuX/`
- **自定义数据目录**: 通过 `-Dccemux.data.dir="path"` 指定

在数据目录中创建：
```
wasm/
├── example1.wasm
├── example2.wasm
└── ...
```

## 更新说明

当需要更新时：
1. 运行 `./gradlew build` 重新编译
2. 更新 Launcher 中的 JAR 路径（如果编译输出位置变化）
3. 或者直接使用新的 JAR 路径创建新的 Launcher 实例