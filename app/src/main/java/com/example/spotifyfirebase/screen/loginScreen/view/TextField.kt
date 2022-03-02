package com.example.spotifyfirebase.screen.loginScreen.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground

@Composable
fun TextFieldEmail(
    value:MutableState<String>
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = {value.value = it},
        shape = AbsoluteRoundedCornerShape(15.dp),
        label = {
            Text(text = "Email")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Go
        ),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = secondaryBackground,
            backgroundColor = primaryBackground,
            cursorColor = secondaryBackground,
            focusedLabelColor = secondaryBackground
        ), modifier = Modifier.padding(5.dp)
    )
}

@Composable
fun TextFieldPassword(
    value: MutableState<String>
){
    OutlinedTextField(
        value = value.value,
        onValueChange = {value.value = it},
        shape = AbsoluteRoundedCornerShape(15.dp),
        label = {
            Text(text = "Password")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Go
        ),
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = secondaryBackground,
            backgroundColor = primaryBackground,
            cursorColor = secondaryBackground,
            focusedLabelColor = secondaryBackground
        ), modifier = Modifier.padding(5.dp)
    )
}

@Composable
fun TextFieldBase(
    value: MutableState<String>,
    title:String
){
    OutlinedTextField(
        value = value.value,
        onValueChange = {value.value = it},
        shape = AbsoluteRoundedCornerShape(15.dp),
        label = {
            Text(text = title)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Go
        ),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = secondaryBackground,
            backgroundColor = primaryBackground,
            cursorColor = secondaryBackground,
            focusedLabelColor = secondaryBackground
        ), modifier = Modifier.padding(5.dp)
    )
}
