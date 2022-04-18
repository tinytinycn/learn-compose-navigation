# Router Overview 路由概述

## 路由

一个关键单元是 `Router` 。它负责管理组件，就像 `FragmentManager` 一样。

`Router` 支持back stack，因此每个组件都有自己的 `Lifecycle`。每次push一个新组件时，当前活动的组件就会停止。当一个组件从后栈中pop时，前一个组件将被恢复。当组件位于后台堆栈时，允许运行业务逻辑。

`Router` 的状态由当前活动的组件和后退堆栈组成，因此它可以呈现为任何其他状态。

子组件也可以有 `Router`（嵌套导航），每个组件可以有多个 `Router`。

### 组件配置

`Router` 创建和管理的每个组件都有其配置 `Configuration` 。它只是一个包含组件实例化所需的所有数据的类。

配置 `Configuration` 必须满足以下要求：

1. 不可变
2. 正确实现` equals()` 和 `hashCode()` 方法
3. 在 `Router` 堆栈中是唯一的（通过相等）
4. 实现 `Parcelable` 接口

#### Configurations are the keys

每个配置都是组件的唯一键。 `Router` 使用配置来检查哪些组件应该是活动的，哪些应该被销毁。在客户端，配置允许您使用适当的输入参数来实例化组件。 为了方便和安全，您可以将配置定义为 data class ，并且仅使用 `val`
属性和不可变数据结构。

#### Configurations are Parcelable

配置可以通过 Android
的 [saved state](https://developer.android.com/guide/components/activities/activity-lifecycle#save-simple,-lightweight-ui-state-using-onsaveinstancestate)
持久化，从而允许在配置更改或进程死亡后进行回栈恢复。恢复后堆栈时，仅重新创建当前活动的组件。后退堆栈中的所有其他人仍然被破坏，并在导航返回时根据需要重新创建。

Decompose 使用 [Essenty](https://github.com/arkivanov/Essenty) 库，该库使用 expect/actual 在通用代码中提供 `Parcelable` 接口和 `@Parcelize`
注解，与 Kotlin Multiplatform 配合得很好。请熟悉 Essenty 库。

> 在 Android 上，可以保留的数据量是有限的。请注意配置大小。

## 路由示例

这是两个子组件之间导航的一个非常基本的示例：

```kotlin
// ItemList component

interface ItemList {

    // Omitted code

    fun onItemClicked(id: Long)
}

class ItemListComponent(
    componentContext: ComponentContext,
    private val onItemSelected: (id: Long) -> Unit
) : ItemList, ComponentContext by componentContext {

    // Omitted code

    override fun onItemClicked(id: Long) {
        onItemSelected(id)
    }
}
```

```kotlin
// ItemDetails component

interface ItemDetails {

    // Omitted code

    fun onCloseClicked()
}

class ItemDetailsComponent(
    componentContext: ComponentContext,
    itemId: Long,
    private val onFinished: () -> Unit
) : ItemDetails, ComponentContext by componentContext {

    // Omitted code

    override fun onCloseClicked() {
        onFinished()
    }
}
```

```kotlin
// Root component

interface Root {

    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        class List(val component: ItemList) : Child()
        class Details(val component: ItemDetails) : Child()
    }
}

class RootComponent(
    componentContext: ComponentContext
) : Root, ComponentContext by componentContext {

    private val router =
        router<Config, Root.Child>(
            initialConfiguration = Config.List,
            handleBackButton = true, // Pop the back stack on back button press
            childFactory = ::createChild
        )

    override val routerState: Value<RouterState<*, Root.Child>> = router.state

    private fun createChild(config: Config, componentContext: ComponentContext): Root.Child =
        when (config) {
            is Config.List -> Root.Child.List(itemList(componentContext))
            is Config.Details -> Root.Child.Details(itemDetails(componentContext, config))
        }

    private fun itemList(componentContext: ComponentContext): ItemList =
        ItemListComponent(
            componentContext = componentContext,
            onItemSelected = { router.push(Config.Details(itemId = it)) }
        )

    private fun itemDetails(componentContext: ComponentContext, config: Config.Details): ItemDetails =
        ItemDetailsComponent(
            componentContext = componentContext,
            itemId = config.itemId,
            onFinished = router::pop
        )

    private sealed class Config : Parcelable {
        @Parcelize
        object List : Config()

        @Parcelize
        data class Details(val itemId: Long) : Config()
    }
}
```

## 一个组件中的多个路由器

当一个组件中需要多个 `Routers` 时，每个这样的 `Router` 都必须具有关联的唯一键。keys 仅在组件内是唯一的，因此不同的组件可以拥有具有相同键的路由器。 如果在一个组件中检测到多个具有相同键的路由器，则会引发异常。

```kotlin
class Root(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val topRouter =
        router<TopConfig, TopChild>(
            key = "TopRouter",
            // Omitted code
        )

    private val bottomRouter =
        router<BottomConfig, BottomChild>(
            key = "BottomRouter",
            // Omitted code
        )
}
```