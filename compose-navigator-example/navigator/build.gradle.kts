plugins {
    // kotlin-multiplatform 多平台插件
    id("org.jetbrains.kotlin.multiplatform") version "1.6.10"
    // jetbrains-compose 插件，注意这个版本要和kotlin编译版本统一
    id("org.jetbrains.compose") version "1.1.0"
}

kotlin {
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                // 安装decompose主模块
                api("com.arkivanov.decompose:decompose:0.6.0")
                // 安装decompose 针对jetbrains compose 的独立扩展模块
                api("com.arkivanov.decompose:extensions-compose-jetbrains:0.6.0")
            }
        }
    }
}