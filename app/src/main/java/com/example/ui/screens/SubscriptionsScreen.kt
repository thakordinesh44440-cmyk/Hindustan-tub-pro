package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.data.MockVideoRepository
import com.example.ui.components.VideoCard
import kotlinx.coroutines.flow.first

@Composable
fun SubscriptionsScreen(onVideoClick: (String) -> Unit) {
    val repository = remember { MockVideoRepository() }
    var videos by remember { mutableStateOf(emptyList<com.example.model.Video>()) }

    LaunchedEffect(Unit) {
        videos = repository.getVideos().first()
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            // Horizontal list of subscribed channels
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Mock channel avatars
                val avatars = videos.distinctBy { it.channelName }
                items(avatars.size) { index ->
                    val video = avatars[index]
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = video.channelAvatarUrl),
                            contentDescription = video.channelName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(64.dp).clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = video.channelName,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1
                        )
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))
        }

        // Recent videos from subscriptions
        items(videos.size) { index ->
            VideoCard(video = videos[index], onClick = { onVideoClick(videos[index].id) })
        }
    }
}
