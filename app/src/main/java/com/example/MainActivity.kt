package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigation.Screen
import com.example.ui.components.BottomNavigationBar
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ShortsScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.SubscriptionsScreen
import com.example.ui.screens.UploadVideoScreen
import com.example.ui.screens.VideoPlayerScreen
import com.example.ui.theme.VideoHubTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoHubTheme {
                VideoHubApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoHubApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val hideBars = currentRoute == "video_player/{videoId}" || currentRoute == Screen.Shorts.route || currentRoute == "chat" || currentRoute == "profile"

    Scaffold(
        topBar = {
            if (!hideBars) {
                TopAppBar(
                    title = {
                        Text(
                            text = "VideoHub",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("chat") }) {
                            Icon(imageVector = Icons.Default.Chat, contentDescription = "Chat")
                        }
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                        }
                        IconButton(onClick = { navController.navigate("profile") }) {
                            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        },
        bottomBar = {
            if (!hideBars) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onVideoClick = { videoId ->
                        navController.navigate("video_player/$videoId")
                    }
                )
            }
            composable(Screen.Shorts.route) { ShortsScreen() }
            composable(Screen.Create.route) { UploadVideoScreen() }
            composable(Screen.Subscriptions.route) {
                SubscriptionsScreen(
                    onVideoClick = { videoId ->
                        navController.navigate("video_player/$videoId")
                    }
                )
            }
            composable(Screen.Library.route) { LibraryScreen() }
            composable("chat") { 
                com.example.ui.screens.ChatScreen(onNavigateUp = { navController.navigateUp() }) 
            }
            composable("profile") {
                com.example.ui.screens.ProfileScreen(
                    onNavigateUp = { navController.navigateUp() },
                    onVideoClick = { videoId -> navController.navigate("video_player/$videoId") }
                )
            }
            composable("video_player/{videoId}") { backStackEntry ->
                val videoId = backStackEntry.arguments?.getString("videoId") ?: return@composable
                VideoPlayerScreen(
                    videoId = videoId,
                    onNavigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}
