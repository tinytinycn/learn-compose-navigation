# Installation 安装

Decompose 提供了许多模块，它们都发布到 Maven Central Repository。

## Decompose 主模块

主要功能由分解模块提供。它包含核心功能，如 Router、ComponentContext 等。

该模块支持以下 Kotlin Multiplatform targets 目标平台：

- android,
- jvm
- js (both IR and Legacy modes)
- iosX64, iosArm64
- tvosArm64, tvosX64
- watchosArm32, watchosArm64, watchosX64
- macosX64

### Gradle 设置

Groovy

`implementation "com.arkivanov.decompose:decompose:<version>"`

Kotlin Script

`implementation("com.arkivanov.decompose:decompose:<version>")`

### 依赖 Essenty 库

一些功能实际上是由 [Essenty](https://github.com/arkivanov/Essenty) 库提供的。 Essenty 由同一作者实现，并提供了 Lifecycle、StateKeeper 等非常基本的东西。最重要的
Essenty 模块作为 api 依赖添加到分解模块中，因此您不必手动将它们添加到项目中。请熟悉 Essenty 库。

## 对 Jetpack/JetBrains Compose 的扩展

Compose UI 目前以两种不同的变体发布：

- 由 Google 开发和维护的只有 Android，称为 [Jetpack Compose](https://developer.android.com/jetpack/compose)
- 由 JetBrains 和 Google 维护的 Jetpack Compose 的 Kotlin Multiplatform
  变体，我们称之为 [JetBrains Compose](https://github.com/JetBrains/compose-jb)

由于这种碎片化，Decompose 为 Compose UI 提供了两个独立的扩展模块：

- `extensions-compose-jetpack` Jetpack Compose 的 Android 库
- `extensions-compose-jetbrains` JetBrains Compose 的 Kotlin 多平台库，支持 android 和 jvm targets

这两个模块都用于将 Compose UI 连接到 Decompose 组件。请参阅相应的[文档页面](https://arkivanov.github.io/Decompose/extensions/compose/) 。

### Gradle 设置

通常只应选择一个模块，具体取决于所使用的 Compose UI 变体。

Groovy

```groovy
implementation "com.arkivanov.decompose:extensions-compose-jetpack:<version>"
// or
implementation "com.arkivanov.decompose:extensions-compose-jetbrains:<version>"
```

kotlin

```kotlin
implementation("com.arkivanov.decompose:extensions-compose-jetpack:<version>")
// or
implementation("com.arkivanov.decompose:extensions-compose-jetbrains:<version>")
```

## 对 Android views 的扩展

`extensions-android` 模块提供了将基于 Android views 的 UI 连接到 Decompose
组件的扩展。请前往相应的[文档页面](https://arkivanov.github.io/Decompose/extensions/android/) 了解更多信息。

### Gradle 设置

Groovy

```groovy
implementation "com.arkivanov.decompose:extensions-android:<version>"
```

Kotlin

```kotlin
implementation("com.arkivanov.decompose:extensions-android:<version>")
```