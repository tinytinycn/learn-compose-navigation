package cn.tinytiny

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.childAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.scale
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

/**
 * 列表UI-子组件
 * */
@Composable
fun List(onItemClick: (String) -> Unit) {
    val items = remember { List(100) { "Item $it" } }

    LazyColumn {
        items(items) { item ->
            Text(
                text = item,
                modifier = Modifier
                    .clickable { onItemClick(item) }
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

/**
 * 详情页UI-子组件
 * */
@Composable
fun Details(text: String, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = text)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text(text = "Back")
        }
    }
}

/**
 * 主页面UI
 * */
@Composable
fun Main() {
    // 1 在开始Compose之前，创建root ComponentContext
    // 2 定义路由的初始配置、返回按钮处理逻辑等
    // 默认显示应该是列表Screen.list 子组件
    val router = rememberRouter<Screen>(
        initialConfiguration = { Screen.List },
        handleBackButton = true
    )

    // 3 在 Composable UI 子组件之间导航
    //   Router 提供 RouterState 作为 Value<RouterState> ，可以在 Composable 组件中观察到。这使得跟随 Router 切换 child Composable 组件成为可能。
    Children(
        routerState = router.state,
        animation = childAnimation(fade() + scale())
    ) { screen ->
        when (val configuration = screen.configuration) {
            is Screen.List -> List(onItemClick = { router.push(Screen.Details(text = it)) })
            is Screen.Details -> Details(text = configuration.text, onBack = router::pop)
        }
    }
}

/**
 * 组件配置-密封类
 * Router 使用配置来检查哪些组件应该是活动的，哪些应该被销毁。在客户端，配置允许您使用适当的输入参数来实例化组件。
 * */
sealed class Screen : Parcelable {

    @Parcelize
    object List : Screen()

    @Parcelize
    data class Details(val text: String) : Screen()
}