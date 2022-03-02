package com.example.spotifyfirebase.navigation.host.buttonBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class ButtonBarDate(val icon:ImageVector) {
    Home(icon = Icons.Default.Home),
    Search(icon = Icons.Default.Search),
    Profile(icon = Icons.Default.Person)
}