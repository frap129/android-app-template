package dev.maples.template.ui.model.data

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import dev.maples.template.ui.theme.ANIMATION_DURATION

/*
 * A "Destination" represents a screen or flow that can be navigated to
 * through the NavController. This class should include any data necessary
 * for registering the destination in the NavHost, including resources
 * needed for setting up a navigation bar.
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
    open val showNavBar: Boolean = false,
    open val navBarItem: DestinationNavBarItem? = null
)

open class DestinationNavBarItem(val icon: Int, val title: String)
