# Web Browser History 网络浏览器历史

默认情况下，`Router` 导航不会影响浏览器地址栏中的 URLs。但有时需要为不同的路由器目标设置不同的 URL。为此，Decompose 提供了一个 **实验性** API
[WebHistoryController](https://github.com/arkivanov/Decompose/blob/master/decompose/src/jsMain/kotlin/com/arkivanov/decompose/router/webhistory/DefaultWebHistoryController.kt)
。

控制器监听 `Router` 状态变化并相应地更新浏览器 URL 和历史记录：

- 当一个或多个组件被推送到 `Router` 堆栈时，控制器将相应的页面推送到历史记录
- 当从堆栈中弹出一个或多个组件时，控制器从历史记录中弹出相应的页面
- 当堆栈中的某些组件被替换时，控制器会尽力保持页面历史对齐（存在极端情况）
- 当用户按下浏览器的返回按钮（或在历史下拉菜单中选择之前的页面之一）时，控制器会从路由器中弹出相应的配置
- 当用户在浏览器历史中向前导航时，控制器将相应的配置推送到路由器

## 角落案例

由于 History API 的限制，存在一种已知的极端情况。当堆栈中的所有配置都替换为另一个配置时（A<-B<-C ===> D），对应于第二个和后续删除的配置（B 和
C）的页面仍保留在历史记录中。如果此时用户将向前移动（通过单击浏览器中的前进按钮），先前删除的配置将被推回堆栈（堆栈将变为 D<-B 或 D<-B<-C） .

## 限制

只有一个 `Router` 可以附加到 `WebHistoryController` 的实例。不允许有多个控制器实例。

## 配置应用程序

在单页应用程序中使用 `WebHistoryController` 需要额外的配置 - 一个包罗万象的策略来为所有路径返回相同的 html 资源。对于不同的服务器配置，此策略会有所不同。

### 开发配置

Kotlin/JS browser target 使用 [webpack-dev-server](https://github.com/webpack/webpack-dev-server) 作为本地开发服务器。通过设置
`devServer.historyApiFallback` 标志，可以将其配置为对所有路径使用相同的 `index.html` 文件（或您的主 html 文件）。 Kotlin webpack 的 Gradle DSL 目前不支持
historyApiFallback 标志，因此应该使用特殊的配置文件来代替。

首先，在 JS 应用模块的目录中创建一个名为 `webpack.config.d` 的目录。然后在该目录中创建一个名为 `devServerConfig.js` 的新文件。最后，将以下内容放入文件中：

```js
// <js app module>/webpack.config.d/devServerConfig.js

config.devServer = {
    ...config.devServer, // Merge with other devServer settings
    "historyApiFallback": true
};
```

## 使用 WebHistoryController

使用 `WebHistoryController` 很简单：

1. 在 JS 应用程序中创建一个新的 `WebHistoryController` 实例，并通过构造函数将其传递给负责导航的组件（通常是根组件）
2. 在组件中，调用 `WebHistoryController.attach` 方法并提供所有参数
3. 在 JS 应用程序中，将初始 deeplink 传递给组件
4. 使用组件中的 deeplink 去生成初始 back stack 返回栈

### 示例

Counter 示例演示了 WebHistoryController 的使用：

- [App.kt](https://github.com/arkivanov/Decompose/blob/master/sample/counter/app-js/src/main/kotlin/com/arkivanov/sample/counter/app/App.kt)
  演示通过构造函数将 `WebHistoryController` 和 deeplink 传递给 `CounterRootComponent`
- [CounterRootComponent](https://github.com/arkivanov/Decompose/blob/master/sample/counter/shared/src/commonMain/kotlin/com/arkivanov/sample/counter/shared/root/CounterRootComponent.kt)
  演示从 deeplink 生成初始返回栈，以及调用 WebHistoryController.attach 并提供参数