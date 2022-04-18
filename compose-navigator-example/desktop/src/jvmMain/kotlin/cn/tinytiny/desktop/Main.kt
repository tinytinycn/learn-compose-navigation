package cn.tinytiny.desktop

import androidx.compose.desktop.DesktopTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cn.tinytiny.Main


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "demo"
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            MaterialTheme {
                DesktopTheme {
                    Main()
                }
            }
        }
    }
}