package com.example.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Shorts : Screen("shorts", "Shorts", Icons.Filled.PlayArrow)
    object Create : Screen("create", "Create", Icons.Filled.AddCircleOutline)
    object Subscriptions : Screen("subscriptions", "Subscriptions", Icons.Filled.Subscriptions)
    object Library : Screen("library", "Library", Icons.Filled.VideoLibrary)
}
