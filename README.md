# OJOCL - OpenCL Java Bindings

[![License](https://img.shields.io/badge/license-Apache_License,_Version_2.0-blue.svg)](LICENSE)
[![GitHub Release](https://img.shields.io/github/v/release/SYpiece/OJOCL?label=GitHub%20Release)](https://github.com/SYpiece/OJOCL/packages)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.SYpiece/ojocl)](https://search.maven.org/artifact/io.github.SYpiece/ojocl)
[![Java Version](https://img.shields.io/badge/java-8+-brightgreen.svg)](https://www.oracle.com/java/technologies/javase-downloads.html)

OJOCL是一个轻量级的OpenCL Java绑定库，提供了简洁易用的API来访问OpenCL功能。

## 📋 目录

- [简介](#简介)
- [特性](#特性)
- [系统要求](#系统要求)
- [安装](#安装)
- [快速开始](#快速开始)
- [API文档](#api文档)
- [示例](#示例)
- [构建](#构建)
- [测试](#测试)
- [贡献](#贡献)
- [许可证](#许可证)

<a id="简介"></a>
## 📖 简介 

OJOCL（OpenCL Java Object）是一个面向对象的OpenCL Java绑定库，旨在简化在Java应用程序中使用OpenCL进行并行计算的过程。它封装了底层的JOCL库，提供了更加直观和类型安全的API。

## ✨ 特性

- 🎯 **面向对象设计** - 清晰的类层次结构
- 🔧 **类型安全** - 强类型的内存管理和参数传递
- 📦 **自动资源管理** - 实现AutoCloseable接口，支持try-with-resources
- 🚀 **高性能** - 直接映射到原生OpenCL API
- 🛠️ **灵活配置** - 支持各种OpenCL平台和设备配置
- 📊 **完整的OpenCL支持** - 涵盖上下文、程序、内核、内存对象等核心概念

## ⚙️ 系统要求

- Java 8 或更高版本
- 支持OpenCL的硬件和驱动程序
- Maven 3.6+ (用于构建)

## 📦 安装

### Maven

```xml
<dependency>
    <groupId>io.github.SYpiece</groupId>
    <artifactId>ojocl</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Gradle

```gradle
implementation 'io.github.SYpiece:ojocl:0.1.0'
```

### 手动下载

从[GitHub Releases](https://github.com/SYpiece/OJOCL/releases)下载JAR文件并添加到classpath中。

## 🚀 快速开始

以下是一个简单的向量加法示例：

```java
import io.github.sypiece.opencl.*;

public class VectorAddExample {
    public static void main(String[] args) {
        // 获取OpenCL平台
        OpenCLPlatform[] platforms = OpenCL.getPlatforms();
        if (platforms.length == 0) {
            System.err.println("未找到OpenCL平台");
            return;
        }

        OpenCLPlatform platform = platforms[0];
        System.out.println("使用平台: " + platform.getName());

        // 获取设备
        OpenCLDevice[] devices = platform.getDevices(OpenCLDevice.Type.GPU);
        if (devices.length == 0) {
            System.err.println("未找到GPU设备");
            return;
        }

        OpenCLDevice device = devices[0];
        System.out.println("使用设备: " + device.getName());

        // 创建上下文
        try (OpenCLContext context = OpenCLContext.create(device)) {
            // 创建命令队列
            try (OpenCLCommandQueue queue = OpenCLCommandQueue.create(context, device)) {

                // 准备数据
                int n = 1024;
                float[] a = new float[n];
                float[] b = new float[n];
                float[] c = new float[n];

                for (int i = 0; i < n; i++) {
                    a[i] = i;
                    b[i] = i * 2;
                }

                // 创建内存对象
                OpenCLMemory.Flags flags = new OpenCLMemory.Flags()
                        .setReadWrite();

                try (OpenCLMemory.Float bufferA = OpenCLMemory.createFloatBuffer(context, flags, a);
                     OpenCLMemory.Float bufferB = OpenCLMemory.createFloatBuffer(context, flags, b);
                     OpenCLMemory.Float bufferC = OpenCLMemory.createFloatBuffer(context, flags, n)) {

                    // OpenCL内核源码
                    String kernelSource =
                            "__kernel void vector_add(__global const float* a, " +
                                    "                        __global const float* b, " +
                                    "                        __global float* c) {" +
                                    "    int i = get_global_id(0);" +
                                    "    c[i] = a[i] + b[i];" +
                                    "}";

                    // 创建和构建程序
                    try (OpenCLProgram program = OpenCLProgram.create(context, kernelSource).build()) {

                        // 创建内核
                        try (OpenCLKernel kernel = program.createKernel("vector_add")) {

                            // 设置内核参数
                            kernel.setArg(0, bufferA);
                            kernel.setArg(1, bufferB);
                            kernel.setArg(2, bufferC);

                            // 执行内核
                            OpenCLCommandQueue.Range range = OpenCLCommandQueue.Range.create(n);
                            queue.executeKernel(kernel, range);

                            // 读取结果
                            queue.enqueueReadBuffer(bufferC, c);
                            queue.finish();

                            // 验证结果
                            for (int i = 0; i < 10; i++) {
                                System.out.printf("c[%d] = %.1f%n", i, c[i]);
                            }
                        }
                    }
                }
            }
        }
    }
}
```

## 📚 API文档

### 核心类概览

#### OpenCL
主入口类，用于获取平台信息：
- `getPlatformCount()` - 获取平台数量
- `getPlatforms()` - 获取所有平台

#### OpenCLPlatform
表示OpenCL平台：
- `getName()` - 平台名称
- `getVendor()` - 平台供应商
- `getVersion()` - 平台版本
- `getDevices()` - 获取设备列表

#### OpenCLDevice
表示OpenCL设备：
- `getName()` - 设备名称
- `getType()` - 设备类型（CPU/GPU等）
- `getMaxComputeUnits()` - 最大计算单元数
- `getMaxWorkGroupSize()` - 最大工作组大小

#### OpenCLContext
OpenCL上下文：
- `create(OpenCLDevice...)` - 创建上下文
- `getDevices()` - 获取上下文中的设备

#### OpenCLCommandQueue
命令队列：
- `create(OpenCLContext, OpenCLDevice)` - 创建命令队列
- `enqueueReadBuffer()` - 读取缓冲区数据
- `enqueueWriteBuffer()` - 写入缓冲区数据
- `enqueueNDRangeKernel()` - 执行内核

#### OpenCLProgram
OpenCL程序：
- `create(OpenCLContext, String...)` - 从源码创建程序
- `build()` - 构建程序
- `createKernel()` - 创建内核

#### OpenCLKernel
OpenCL内核：
- `setArg()` - 设置内核参数
- `getNumArgs()` - 获取参数数量

#### OpenCLMemory
内存对象管理：
- 各种类型缓冲区创建方法
- 内存标志设置
- 子缓冲区创建

## 💡 示例

### 矩阵乘法

```java
// 矩阵乘法内核示例
String matrixMulKernel = 
    "__kernel void matrix_mul(__global const float* A," +
    "                        __global const float* B," +
    "                        __global float* C," +
    "                        const int N) {" +
    "    int row = get_global_id(0);" +
    "    int col = get_global_id(1);" +
    "    " +
    "    if (row < N && col < N) {" +
    "        float sum = 0.0f;" +
    "        for (int k = 0; k < N; k++) {" +
    "            sum += A[row * N + k] * B[k * N + col];" +
    "        }" +
    "        C[row * N + col] = sum;" +
    "    }" +
    "}";
```

### 图像处理

```java
// Sobel边缘检测示例
String sobelKernel =
    "__kernel void sobel_filter(__read_only image2d_t input," +
    "                          __write_only image2d_t output) {" +
    "    int2 coord = (int2)(get_global_id(0), get_global_id(1));" +
    "    // Sobel算子实现..." +
    "}";
```

## 🔧 构建

```bash
# 克隆仓库
git clone https://github.com/SYpiece/OJOCL.git
cd OJOCL

# 编译项目
mvn compile

# 运行测试
mvn test

# 打包
mvn package

# 安装到本地仓库
mvn install
```

## 🧪 测试

```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn -Dtest=OJOCLTest test
```

## 🤝 贡献

欢迎贡献代码！请遵循以下步骤：

1. Fork 仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

### 开发指南

- 遵循现有的代码风格
- 添加适当的单元测试
- 更新相关文档
- 确保所有测试通过

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🔗 相关链接

- [OpenCL官方文档](https://www.khronos.org/opencl/)
- [JOCL项目](http://www.jocl.org/)
- [Java官方文档](https://docs.oracle.com/javase/8/docs/)

## 📞 联系方式

- 作者: SYpiece
- 邮箱: sypiece@example.com
- GitHub: [https://github.com/SYpiece](https://github.com/SYpiece)

## 🙏 致谢

特别感谢以下项目和贡献者：

- [JOCL](http://www.jocl.org/) - 底层OpenCL绑定
- [Khronos Group](https://www.khronos.org/) - OpenCL规范
- 所有开源贡献者

---

**注意**: 请确保您的系统已正确安装OpenCL驱动程序才能正常使用此库。