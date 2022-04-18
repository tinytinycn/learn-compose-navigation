# Custom ComponentContext 自定义组件上下文

如果需要 `ComponentContext` 来获得尚未提供的额外功能。可以创建一个自定义的 `ComponentContext`，它可以用您选择的所需功能进行装饰。

## 创建并实现自定义 ComponentContext

例如，要创建自己的自定义 `ComponentContext`，必须首先创建一个扩展 `ComponentContext` 的接口，然后提供其实现。

```kotlin
interface AppComponentContext : ComponentContext {

    // Custom things here

}

class DefaultAppComponentContext(
    componentContext: ComponentContext,
    // Additional dependencies here
) : AppComponentContext, ComponentContext by componentContext {

    // Custom things implementation here
}
```

## 如何使用自定义 ComponentContext 创建路由器

为了将此 `AppComponentContext` 从 `Router` 传递给应用程序中的其他组件，请在 `AppComponentContext` 接口上创建扩展函数。此自定义扩展函数将创建路由器并提供
child `AppComponentContext`。

```kotlin
fun <C : Parcelable, T : Any> AppComponentContext.appRouter(
    initialStack: () -> List<C>,
    configurationClass: KClass<out C>,
    key: String = "DefaultRouter",
    handleBackButton: Boolean = false,
    childFactory: (configuration: C, AppComponentContext) -> T
): Router<C, T> =
    router(
        initialStack = initialStack,
        configurationClass = configurationClass,
        key = key,
        handleBackButton = handleBackButton
    ) { configuration, componentContext ->
        childFactory(
            configuration,
            DefaultAppComponentContext(
                componentContext = componentContext
            )
        )
    }
```

最后，在您的组件中，您可以创建一个新的路由器，该路由器将利用新的自定义组件上下文。

```kotlin
class MyComponent(componentContext: AppComponentContext) : AppComponentContext by componentContext {

    private val router = appRouter(
        initialStack = { listOf(Configuration.Home) },
        childFactory = { configuration, appComponentContext ->
            // return child components using the custom component context
        }
    )
}
```