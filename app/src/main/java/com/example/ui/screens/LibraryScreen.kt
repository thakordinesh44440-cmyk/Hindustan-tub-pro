package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LibraryScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            LibraryMenuItem(icon = Icons.Default.History, title = "History")
            LibraryMenuItem(icon = Icons.Default.SmartDisplay, title = "Your videos")
            LibraryMenuItem(icon = Icons.Default.FileDownload, title = "Downloads")
            LibraryMenuItem(icon = Icons.Default.ThumbUp, title = "Liked videos")
        }
        
        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            Text(
                text = "Playlists",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        
        item {
            LibraryMenuItem(icon = Icons.Default.Add, title = "New playlist", color = MaterialTheme.colorScheme.primary)
            LibraryMenuItem(icon = Icons.AutoMirrored.Filled.PlaylistPlay, title = "Watch Later", subtitle = "12 videos")
            LibraryMenuItem(icon = Icons.AutoMirrored.Filled.PlaylistPlay, title = "Favorites", subtitle = "45 videos")
        }
    }
}

@Composable
fun LibraryMenuItem(icon: ImageVector, title: String, subtitle: String? = null, color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = title, tint = color, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.width(24.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, color = color)
            if (subtitle != null) {
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
