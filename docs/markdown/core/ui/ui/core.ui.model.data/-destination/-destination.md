//[ui](../../../index.md)/[core.ui.model.data](../index.md)/[Destination](index.md)/[Destination](-destination.md)

# Destination

[androidJvm]\
constructor(route: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), content: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)([NavController](https://developer.android.com/reference/kotlin/androidx/navigation/NavController.html), [NavBackStackEntry](https://developer.android.com/reference/kotlin/androidx/navigation/NavBackStackEntry.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), deepLinks: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[NavDeepLink](https://developer.android.com/reference/kotlin/androidx/navigation/NavDeepLink.html)&gt; = emptyList(), enterTransition: [AnimatedContentTransitionScope](https://developer.android.com/reference/kotlin/androidx/compose/animation/AnimatedContentTransitionScope.html)&lt;[NavBackStackEntry](https://developer.android.com/reference/kotlin/androidx/navigation/NavBackStackEntry.html)&gt;.() -&gt; [EnterTransition](https://developer.android.com/reference/kotlin/androidx/compose/animation/EnterTransition.html)?? = {
        fadeIn(animationSpec = tween(ANIMATION_DURATION))
    }, exitTransition: [AnimatedContentTransitionScope](https://developer.android.com/reference/kotlin/androidx/compose/animation/AnimatedContentTransitionScope.html)&lt;[NavBackStackEntry](https://developer.android.com/reference/kotlin/androidx/navigation/NavBackStackEntry.html)&gt;.() -&gt; [ExitTransition](https://developer.android.com/reference/kotlin/androidx/compose/animation/ExitTransition.html)?? = {
        fadeOut(animationSpec = tween(ANIMATION_DURATION))
    }, arguments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[NamedNavArgument](https://developer.android.com/reference/kotlin/androidx/navigation/NamedNavArgument.html)&gt; = emptyList(), showNavBar: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, navBarItem: [DestinationNavBarItem](../-destination-nav-bar-item/index.md)? = null)

Creates a new destination object
