//[ui](../../../index.md)/[core.ui.model.data](../index.md)/[Destination](index.md)/[Destination](-destination.md)

# Destination

[release]\
constructor(route: String, content: @Composable(NavController, NavBackStackEntry) -&gt; Unit, deepLinks: List&lt;NavDeepLink&gt; = emptyList(), enterTransition: AnimatedContentTransitionScope&lt;NavBackStackEntry&gt;.() -&gt; EnterTransition?? = {
        fadeIn(animationSpec = tween(ANIMATION_DURATION))
    }, exitTransition: AnimatedContentTransitionScope&lt;NavBackStackEntry&gt;.() -&gt; ExitTransition?? = {
        fadeOut(animationSpec = tween(ANIMATION_DURATION))
    }, arguments: List&lt;NamedNavArgument&gt; = emptyList(), showNavBar: Boolean = false, navBarItem: [DestinationNavBarItem](../-destination-nav-bar-item/index.md)? = null)

Creates a new destination object
