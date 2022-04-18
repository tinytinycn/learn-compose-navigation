# Deep linking 深度链接

在设备上点击链接的用户有一个目标：访问他们想要查看的内容。 Decompose 提供了覆盖初始目标和返回堆栈的能力。一种典型的做法是在平台端解析深度链接，然后将初始数据传递给根组件，然后再向下传递树到所有需要的组件。

在平台端解析深度链接超出了本文档的范围。此信息应在平台的特定文档中提供。例如这里是 Android 的[相关文档](https://developer.android.com/training/app-links/deep-linking)
。

## Handling deep links 处理深层链接

鉴于路由器概述页面中的基本示例，我们可以轻松处理深层链接。假设我们有一个类似 `http://myitems.com?itemId=3` 的链接。当用户点击它时，我们想要打开带有提供的 id
的项目的详细信息屏幕。当用户关闭详细信息屏幕时，他们应该被导航回列表屏幕。这个想法是将解析后的数据从深层链接传递给负责导航的组件，在我们的例子中是根组件。

```kotlin
class RootComponent(
    componentContext: ComponentContext,
    initialItemId: Long? = null
) : Root, ComponentContext by componentContext {

    private val router =
        router<Config, Root.Child>(
            initialStack = {
                listOfNotNull(
                    Config.List,
                    if (initialItemId != null) Config.Details(itemId = initialItemId) else null,
                )
            },
            handleBackButton = true,
            childFactory = ::createChild
        )

    // Omitted code
}
```

现在，如果提供了 `initialItemId`，则初始屏幕将是 `ItemDetails` 组件。 `ItemList` 组件将位于后退堆栈中，因此用户将能够返回。