package com.bangkit.jajananapp_bintang

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bangkit.jajananapp_bintang.ui.navigation.NavigationItem
import com.bangkit.jajananapp_bintang.ui.navigation.Screen
import com.bangkit.jajananapp_bintang.ui.screen.cartscreen.CartScreen
import com.bangkit.jajananapp_bintang.ui.screen.detail.DetailScreen
import com.bangkit.jajananapp_bintang.ui.screen.homepage.HomeScreen
import com.bangkit.jajananapp_bintang.ui.screen.profile.ProfileScreen

@Composable
fun JajananApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    var isDarkTheme by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Scaffold(
            bottomBar = {
                if (currentRoute != Screen.Detail.route) {
                    BottomBar(navController)
                }
            },
            modifier = modifier
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        navigateToDetail = { makananId ->
                            navController.navigate(Screen.Detail.createRoute(makananId))
                        }
                    )
                }
                composable(Screen.Cart.route) {
                    val context = LocalContext.current
                    CartScreen(
                        onOrderButtonClicked = { message ->
                            shareOrder(context, message)
                        }
                    )
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(
                        isDarkTheme = isDarkTheme,
                        onThemeChange = { isDarkTheme = it }
                    )
                }
                composable(
                    route = Screen.Detail.route,
                    arguments = listOf(navArgument("makananId") { type = NavType.IntType })
                ) {
                    val id = it.arguments?.getInt("makananId") ?: -1
                    DetailScreen(
                        makananId = id,
                        navigateBack = { navController.navigateUp() },
                        navigateToCart = {
                            navController.popBackStack()
                            navController.navigate(Screen.Cart.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_cart),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(icon = {
                Icon(
                    imageVector = item.icon, contentDescription = item.title
                )
            },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                })
        }
    }
}

private fun shareOrder(context: Context, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent, context.getString(R.string.app_name)
        )
    )
}
