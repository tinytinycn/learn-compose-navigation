plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0"
}

kotlin {
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":navigator"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                api("com.arkivanov.decompose:decompose:0.6.0")
            }
        }
    }
}