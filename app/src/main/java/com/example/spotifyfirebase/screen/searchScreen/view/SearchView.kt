package com.example.spotifyfirebase.screen.searchScreen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.spotifyfirebase.ui.theme.primaryBackground
import com.example.spotifyfirebase.ui.theme.secondaryBackground

@Composable
fun SearchView(
    search:MutableState<String>,
    onClick:() -> Unit,
    onSearch:(KeyboardActionScope.() -> Unit)? = null
) {
    TextField(
        value = search.value,
        onValueChange = {
            search.value = it
        }, placeholder = {
            Text(text = "Search")
        }, leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = secondaryBackground
            )
        }, keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ), shape = AbsoluteRoundedCornerShape(5.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = secondaryBackground,
            backgroundColor = primaryBackground,
            cursorColor = secondaryBackground
        ), trailingIcon = {
            if (search.value.isNotEmpty()){
                IconButton(onClick = { search.value = "" }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = secondaryBackground
                    )
                }
            }
        }, keyboardActions = KeyboardActions(onSearch = onSearch),
        modifier = Modifier.onFocusChanged {
            if (it.isFocused){
                onClick()
            }
        }
    )
}