# State preservation 状态保存

有时可能需要在组件被销毁时，保留其状态或数据。一个非常常见的用例是由于配置更改或进程死亡而导致的 Android Activity 重新创建。 `ComponentContext` 接口扩展了 `StateKeeperOwner`
接口，它提供了
`StateKeeper` 它是多平台的一个用于状态保存的抽象。它由 [Essenty](https://github.com/arkivanov/Essenty) 库（来自同一作者）提供。

`decompose` 模块将 Essenty 的 `state-keeper` 模块添加为 `api` 依赖项，因此您无需将其显式添加到您的项目中。**请熟悉 Essenty 库**，尤其是 `StateKeeper`。

## 使用示例

```kotlin
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.essenty.statekeeper.consume

class SomeComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private var state: State = stateKeeper.consume(key = "SAVED_STATE") ?: State()

    init {
        stateKeeper.register(key = "SAVED_STATE") { state }
    }

    @Parcelize
    private class State(val someValue: Int = 0) : Parcelable
}

```