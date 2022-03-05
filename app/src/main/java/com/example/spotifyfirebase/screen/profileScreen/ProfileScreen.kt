package com.example.spotifyfirebase.screen.profileScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.spotifyfirebase.api.model.user.User
import com.example.spotifyfirebase.navigation.navGraph.loginNavGraph.constants.LoginRoute.Route.LOGIN_ROUTE
import com.example.spotifyfirebase.navigation.navGraph.profileNavGraph.constants.ProfileRouteScreen
import com.example.spotifyfirebase.screen.profileScreen.viewModel.ProfileViewModel
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground
import com.example.spotifyfirebase.utils.Converters.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val user = remember { mutableStateOf(User()) }
    val statusRegistration:MutableState<Boolean?> = remember { mutableStateOf(null) }

    profileViewModel.readStatusRegistration()
    profileViewModel.responseStatusRegistration.onEach {
        statusRegistration.value = it
    }.launchWhenStarted(lifecycleScope)

    statusRegistration.value?.let {
        if (!it){
            LaunchedEffect(key1 = Unit, block = {
                navController.navigate(LOGIN_ROUTE)
            })
        }
    }

    profileViewModel.getUserInfo()
    profileViewModel.responseUserInfo.onEach {
        user.value = it
    }.launchWhenStarted(lifecycleScope)

    Scaffold(
     topBar = {
         Surface(
             modifier = Modifier.fillMaxSize(),
             color = primaryBackground
         ) {
             Row(
                 modifier = Modifier.fillMaxWidth(),
                 horizontalArrangement = Arrangement.SpaceBetween
             ) {
                 Text(
                     text = "Hello, ${user.value.username}",
                     modifier = Modifier.padding(
                         start = 20.dp,
                         top = 5.dp,
                         bottom = 5.dp,
                         end = 5.dp
                     ),
                     fontWeight = FontWeight.Bold,
                     color = secondaryBackground
                 )

                 IconButton(onClick = { navController.navigate(ProfileRouteScreen.UserSetting.route) }) {
                     Icon(
                         imageVector = Icons.Default.Settings,
                         contentDescription = "Setting",
                         tint = secondaryBackground,
                         modifier = Modifier.padding(
                             start = 20.dp,
                             top = 5.dp,
                             bottom = 5.dp,
                             end = 5.dp
                         )
                     )
                 }
             }
         }
     }, content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = primaryBackground
            ) {

            }
        }
    )
}