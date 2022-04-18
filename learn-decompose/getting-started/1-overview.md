# Overview 概述

## 什么是 Decompose ?

Decompose 是一个 Kotlin 多平台库，用于将您的代码分解为 可感知生命周期 的业务逻辑组件（又名 BLoC），具有路由功能和可插拔 UI（Jetpack Compose、Android Views、SwiftUI、JS
React 等）。

### 支持的 targets 平台

- android
- jvm
- js (both IR and Legacy modes)
- iosX64, iosArm64
- tvosArm64, tvosX64
- watchosArm32, watchosArm64, watchosX64
- macosX64

## 为什么要 Decompose ?

- Decompose 在 UI 和非 UI 代码之间划定了清晰的界限，这将带来以下好处：
    - 更好地分离关注点
    - 可插拔的平台特定 UI（Compose、SwiftUI、React 等）
    - 业务逻辑代码可使用纯多平台单元 test 进行测试
- 通过构造函数进行适当的依赖注入 (DI) 和控制反转 (IoC)
- 可共享的导航逻辑
- 生命周期感知的组件
- 后台堆栈中的组件不会被销毁，它们在没有 UI 的情况下，继续在后台工作
- 组件和 UI 状态保存（在 Android 中最有用）
- 通过配置更改保留的实例（又名 ViewModels）（在 Android 中最有用）
  原文：`Instances retaining (aka ViewModels) over configuration changes (mostly useful in Android)`