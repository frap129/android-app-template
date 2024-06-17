package dev.maples.template.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import core.ui.R
import core.ui.model.data.Destination
import core.ui.model.data.DestinationNavBarItem
import core.ui.theme.AppTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

/*
 * The destinations list holds Destination objects for all navigable screens
 * in the app. This list is consumed by NavHost to add the defined routes to
 * the NavGraph.
 */
val destinations: List<Destination> = listOf()

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AppFrame(content: @Composable () -> Unit) {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorResource(id = R.color.backgroundDark)
        ) {
            KoinAndroidContext {
                content()
            }
        }
    }
}

/*
 * This is the root composable for this application. It creates a NavHost
 * within an AppFrame, allowing any screen or flow that is in the
 * `destinations` list to be accessed.
 */
@Composable
fun App() {
    val navController = rememberNavController()
    val navBarState = rememberSaveable { (mutableStateOf(false)) }

    AppFrame {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = navBarState.value,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                    content = {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route

                            destinations.filter { it.navBarItem != null }.forEach { destination ->
                                val item: DestinationNavBarItem = destination.navBarItem!!
                                NavigationBarItem(
                                    selected = currentRoute == destination.route,
                                    onClick = {
                                        navController.navigate(destination.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = item.icon),
                                            contentDescription = item.title
                                        )
                                    }
                                )
                            }
                        }
                    }
                )
            },
            content = { padding ->
                NavHost(
                    navController = navController,
                    startDestination = destinations.first().route,
                    modifier = Modifier.padding(padding)
                ) {
                    // Add all defined destinations to the NavGraph
                    destinations.forEach { dest ->
                        composable(
                            route = dest.route,
                            content = { navBackStackEntry ->
                                navBarState.value = dest.showNavBar
                                dest.content(navController, navBackStackEntry)
                            },
                            deepLinks = dest.deepLinks,
                            arguments = dest.arguments,
                            enterTransition = dest.enterTransition,
                            exitTransition = dest.exitTransition
                        )
                    }
                }
            }
        )
    }
}
