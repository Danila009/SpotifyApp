package com.example.spotifyfirebase.screen.loginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spotifyfirebase.navigation.navGraph.loginNavGraph.constants.LoginRouteScreen
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground

@Composable
fun LoginScreen(
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                elevation = ButtonDefaults.elevation(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = secondaryBackground
                ), modifier = Modifier.padding(5.dp),
                shape = AbsoluteRoundedCornerShape(15.dp),
                onClick = { navController.navigate(LoginRouteScreen.Authorization.route) }
            ) {
                Text(
                    text = "Authorization",
                    color = Color.Black
                )
            }

            OutlinedButton(
                elevation = ButtonDefaults.elevation(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = secondaryBackground
                ), modifier = Modifier.padding(5.dp),
                shape = AbsoluteRoundedCornerShape(15.dp),
                onClick = { navController.navigate(LoginRouteScreen.Registration.route) }
            ) {
                Text(
                    text = "Registration",
                    color = Color.Black
                )
            }
        }
    }
}