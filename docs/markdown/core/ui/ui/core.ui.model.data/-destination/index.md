//[ui](../../../index.md)/[core.ui.model.data](../index.md)/[Destination](index.md)

# Destination

open class [Destination](index.md)(val route: String, val content: @Composable(NavController, NavBackStackEntry) -&gt; Unit, val deepLinks: List&lt;NavDeepLink&gt; = emptyList(), val enterTransition: AnimatedContentTransitionScope&lt;NavBackStackEntry&gt;.() -&gt; EnterTransition?? = {
        fadeIn(animationSpec = tween(ANIMATION_DURATION))
    }, val exitTransition: AnimatedContentTransitionScope&lt;NavBackStackEntry&gt;.() -&gt; ExitTransition?? = {
        fadeOut(animationSpec = tween(ANIMATION_DURATION))
    }, val arguments: List&lt;NamedNavArgument&gt; = emptyList(), val showNavBar: Boolean = false, val navBarItem: [DestinationNavBarItem](../-destination-nav-bar-item/index.md)? = null)

A &quot;Destination&quot; represents a screen or flow that can be navigated to through the NavController. This class should include any data necessary for registering the destination in the NavHost, including resources needed for setting up a navigation bar.

#### See also

| |
|---|
| [DestinationNavBarItem](../-destination-nav-bar-item/index.md) |

## Constructors

| | |
|---|---|
| [Destination](-destination.md) | [release]<br>constructor(route: String, content: @Composable(NavController, NavBackStackEntry) -&gt; Unit, deepLinks: List&lt;NavDeepLink&gt; = emptyList(), enterTransition: AnimatedContentTransitionScope&lt;NavBackStackEntry&gt;.() -&gt; EnterTransition?? = {         fadeIn(animationSpec = tween(ANIMATION_DURATION))     }, exitTransition: AnimatedContentTransitionScope&lt;NavBackStackEntry&gt;.() -&gt; ExitTransition?? = {         fadeOut(animationSpec = tween(ANIMATION_DURATION))     }, arguments: List&lt;NamedNavArgument&gt; = emptyList(), showNavBar: Boolean = false, navBarItem: [DestinationNavBarItem](../-destination-nav-bar-item/index.md)? = null)<br>Creates a new destination object |

## Properties

| Name | Summary |
|---|---|
| [arguments](arguments.md) | [release]<br>open val [arguments](arguments.md): List&lt;NamedNavArgument&gt;<br>list of NamedNavArgument supported by the destination |
| [content](content.md) | [release]<br>open val [content](content.md): @Composable(NavController, NavBackStackEntry) -&gt; Unit<br>Composable providing NavController and NavBackStackEntry |
| [deepLinks](deep-links.md) | [release]<br>open val [deepLinks](deep-links.md): List&lt;NavDeepLink&gt;<br>list of NavDeepLink that link to this destination |
| [enterTransition](enter-transition.md) | [release]<br>@JvmSuppressWildcards<br>open val [enterTransition](enter-transition.md): AnimatedContentTransitionScope&lt;NavBackStackEntry&gt;.() -&gt; EnterTransition??<br>animation to use when entering the destination |
| [exitTransition](exit-transition.md) | [release]<br>open val [exitTransition](exit-transition.md): AnimatedContentTransitionScope&lt;NavBackStackEntry&gt;.() -&gt; ExitTransition??<br>animation to use when exiting the destination |
| [navBarItem](nav-bar-item.md) | [release]<br>open val [navBarItem](nav-bar-item.md): [DestinationNavBarItem](../-destination-nav-bar-item/index.md)?<br>a [DestinationNavBarItem](../-destination-nav-bar-item/index.md) that links to this destination |
| [route](route.md) | [release]<br>open val [route](route.md): String<br>name of the route |
| [showNavBar](show-nav-bar.md) | [release]<br>open val [showNavBar](show-nav-bar.md): Boolean<br>whether a navbar should be shown at the destination |
