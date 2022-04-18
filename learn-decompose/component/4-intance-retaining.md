# Instance retaining 实例保留

有时可能需要在组件被销毁时， 保留其状态或数据。这通常在发生配置更改时在 Android 中使用。 `ComponentContext` 接口扩展了 `InstanceKeeperOwner` 接口，该接口提供了
`InstanceKeeper` 它是多平台的一个用于实例保留的抽象。它由 [Essenty](https://github.com/arkivanov/Essenty) 库（来自同一作者）提供。

`decompose` 模块将 Essenty 的 `instance-keeper` 模块添加为 `api` 依赖项，因此您无需将其显式添加到您的项目中。**请熟悉 Essenty 库**，尤其是 `InstanceKeeper`。

## 使用示例

```kotlin
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate

class SomeComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val someLogic = instanceKeeper.getOrCreate(::SomeLogic)

    /*
     * Instances of this class will be retained.
     * ⚠️ Pay attention to not leak any dependencies.
     */
    private class SomeLogic : InstanceKeeper.Instance {
        override fun onDestroy() {
            // Clean-up
        }
    }
}

```