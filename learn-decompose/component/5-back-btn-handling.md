# Back button handling 返回按钮处理

某些设备（例如 Android）具有硬件后退按钮。一个非常常见的用例是关闭当前屏幕，或者如果堆栈中只有一个屏幕，则关闭应用程序。 另一个可能的用例是在关闭应用程序之前显示一个确认对话框。

## 带返回按钮的导航

当按下返回按钮时， `Router` 可以自动返回。您需要做的就是在创建路由器时提供 `handleBackButton=true`
参数。有关详细信息，请参阅相关[文档页面](https://arkivanov.github.io/Decompose/router/overview/) 。

## 手动后退按钮处理

可以使用 `ComponentContext` 提供的 `BackPressedHandler`（来自 [Essenty](https://github.com/arkivanov/Essenty)
库）手动处理后退按钮。 `decompose`
模块将 Essenty 的 `back-pressed` 模块添加为 `api` 依赖项，因此您无需将其显式添加到您的项目中。**请熟悉 Essenty 库**，尤其是 `BackPressedDispatcher`。

### 使用示例

```kotlin
import com.arkivanov.decompose.ComponentContext

class SomeComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    init {
        backPressedHandler.register(::onBackPressed)
    }

    private fun onBackPressed(): Boolean {
        // Handle the back button.
        // Return true to consume the event, or false to allow other consumers.
        return false
    }
}
```