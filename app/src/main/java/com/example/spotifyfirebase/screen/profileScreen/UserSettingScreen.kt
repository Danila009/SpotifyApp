package com.example.spotifyfirebase.screen.profileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.spotifyfirebase.navigation.navGraph.profileNavGraph.constants.ProfileRouteScreen
import com.example.spotifyfirebase.screen.profileScreen.viewModel.ProfileViewModel

@Composable
fun UserSettingScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController,
) {
    LazyColumn(content = {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedButton(onClick = {

                    profileViewModel.saveStatusRegistration(
                        userRegistration = false
                    )

                    navController.navigate(ProfileRouteScreen.ProfileScreen.route)

                }) {
                    Text(text = "Выйти")
                }
            }
        }
    })
}