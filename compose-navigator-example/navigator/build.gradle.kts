plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0"
}

kotlin {
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                api("com.arkivanov.decompose:decompose:0.6.0")
                api("com.arkivanov.decompose:extensions-compose-jetbrains:0.6.0")
            }
        }
    }
}