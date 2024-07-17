package core.ui.model.data

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import core.ui.theme.ANIMATION_DURATION

/**
 * A "Destination" represents a screen or flow that can be navigated to
 * through the NavController. This class should include any data necessary
 * for registering the destination in the NavHost, including resources
 * needed for setting up a navigation bar.
 *
 * @property route name of the route
 * @property content Composable providing [NavController] and [NavBackStackEntry]
 * @property deepLinks list of [NavDeepLink] that link to this destination
 * @property enterTransition animation to use when entering the destination
 * @property exitTransition animation to use when exiting the destination
 * @property arguments list of [NamedNavArgument] supported by the destination
 * @property showNavBar whether a navbar should be shown at the destination
 * @property navBarItem a [DestinationNavBarItem] that links to this destination
 * @see DestinationNavBarItem
 * @constructor Creates a new destination object
 */
open class Destination(
    open val route: String,
    open val content: @Composable (NavController, NavBackStackEntry) -> Unit,
    open val deepLinks: List<NavDeepLink> = emptyList(),
    @Suppress("ktlint:standard:max-line-length")
    @JvmSuppressWildcards()
    open val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        fadeIn(animationSpec = tween(ANIMATION_DURATION))
    },
    @Suppress("ktlint:standard:max-line-length")
    open val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        fadeOut(animationSpec = tween(ANIMATION_DURATION))
    },
    open val arguments: List<NamedNavArgument> = emptyList(),
    open val showNavBar: Boolean = false,
    open val navBarItem: DestinationNavBarItem? = null
)

/**
 * A "Destination" represents a screen or flow that can be navigated to
 * through the NavController. This class should include any data necessary
 * for registering the destination in the NavHost, including resources
 * needed for setting up a navigation bar.
 *
 * @property icon resource int of the icon for this destination
 * @property title user-facing name of this destination
 * @see Destination
 * @constructor Creates a new DestinationNavBarItem
 */
open class DestinationNavBarItem(val icon: Int, val title: String)
