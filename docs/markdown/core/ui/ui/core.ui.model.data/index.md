//[ui](../../index.md)/[core.ui.model.data](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [Destination](-destination/index.md) | [release]<br>open class [Destination](-destination/index.md)(val route: String, val content: @Composable(NavController, NavBackStackEntry) -&gt; Unit, val deepLinks: List&lt;NavDeepLink&gt; = emptyList(), val enterTransition: AnimatedContentTransitionScope&lt;NavBackStackEntry&gt;.() -&gt; EnterTransition?? = {         fadeIn(animationSpec = tween(ANIMATION_DURATION))     }, val exitTransition: AnimatedContentTransitionScope&lt;NavBackStackEntry&gt;.() -&gt; ExitTransition?? = {         fadeOut(animationSpec = tween(ANIMATION_DURATION))     }, val arguments: List&lt;NamedNavArgument&gt; = emptyList(), val showNavBar: Boolean = false, val navBarItem: [DestinationNavBarItem](-destination-nav-bar-item/index.md)? = null)<br>A &quot;Destination&quot; represents a screen or flow that can be navigated to through the NavController. This class should include any data necessary for registering the destination in the NavHost, including resources needed for setting up a navigation bar. |
| [DestinationNavBarItem](-destination-nav-bar-item/index.md) | [release]<br>open class [DestinationNavBarItem](-destination-nav-bar-item/index.md)(val icon: Int, val title: String)<br>A &quot;Destination&quot; represents a screen or flow that can be navigated to through the NavController. This class should include any data necessary for registering the destination in the NavHost, including resources needed for setting up a navigation bar. |
