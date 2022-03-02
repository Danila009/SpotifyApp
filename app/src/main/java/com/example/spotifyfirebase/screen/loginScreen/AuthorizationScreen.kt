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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.spotifyfirebase.api.model.user.Authorization
import com.example.spotifyfirebase.screen.loginScreen.view.TextFieldEmail
import com.example.spotifyfirebase.screen.loginScreen.view.TextFieldPassword
import com.example.spotifyfirebase.screen.loginScreen.viewModel.LoginViewModel
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground
import com.example.spotifyfirebase.utils.Converters.launchWhenStarted
import kotlinx.coroutines.flow.onEach

@Composable
fun AuthorizationScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val validateAuthorization = remember { mutableStateOf("") }

    loginViewModel.responseValidate.onEach {
        validateAuthorization.value = it
    }.launchWhenStarted(lifecycleScope)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = validateAuthorization.value,
                color = Color.Red,
                modifier = Modifier.padding(5.dp)
            )

            TextFieldEmail(
                value = email
            )

            TextFieldPassword(
                value = password
            )

            OutlinedButton(
                modifier = Modifier.padding(5.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = secondaryBackground
                ),
                shape = AbsoluteRoundedCornerShape(15.dp),
                onClick = {
                    loginViewModel.authorization(
                        authorization =  Authorization(
                            email = email.value,
                            password = password.value
                        ),
                        navController = navController
                    )
                }
            ) {
                Text(
                    text = "Authorization",
                    color = Color.Black
                )
            }
        }
    }
}