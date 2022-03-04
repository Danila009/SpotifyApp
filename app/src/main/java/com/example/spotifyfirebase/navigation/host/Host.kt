package com.example.spotifyfirebase.navigation.host

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.spotifyfirebase.navigation.host.buttonBar.ButtonBarDate
import com.example.spotifyfirebase.navigation.navGraph.homeNavGraph.constants.RouteAndArgumentsHome.Route.HOME_ROUTE
import com.example.spotifyfirebase.navigation.navGraph.homeNavGraph.homeNavGraph
import com.example.spotifyfirebase.navigation.navGraph.profileNavGraph.constants.ProfileRoute.Route.PROFILE_ROUTE
import com.example.spotifyfirebase.navigation.navGraph.profileNavGraph.profileNavGraph
import com.example.spotifyfirebase.navigation.navGraph.searchNavGraph.constants.RouteSearch.Route.SEARCH_ROUTE
import com.example.spotifyfirebase.navigation.navGraph.searchNavGraph.searchNavGraph
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground

@Composable
fun Host(
    navHostController: NavHostController
) {
    val idButtonBar = remember { mutableStateOf(ButtonBarDate.Home) }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = primaryBackground,
                elevation = 12.dp
            ) {
                ButtonBarDate.values().forEach { item ->
                    BottomNavigationItem(
                        selected = idButtonBar.value == item,
                        onClick = {
                            idButtonBar.value = item

                            when(idButtonBar.value){
                                ButtonBarDate.Home -> navHostController.navigate(HOME_ROUTE)

                                ButtonBarDate.Search -> navHostController.navigate(SEARCH_ROUTE)

                                ButtonBarDate.Profile -> navHostController.navigate(PROFILE_ROUTE)
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }, selectedContentColor = secondaryBackground, unselectedContentColor = Color.Gray
                    )
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = primaryBackground
        ) {
            NavHost(
                navController = navHostController,
                startDestination = HOME_ROUTE,
                route = "route",
                builder = {
                    searchNavGraph(
                        navController = navHostController
                    )

                    homeNavGraph(
                        navController = navHostController
                    )

                    profileNavGraph(
                        navController = navHostController
                    )
                }
            )
        }
    }
}