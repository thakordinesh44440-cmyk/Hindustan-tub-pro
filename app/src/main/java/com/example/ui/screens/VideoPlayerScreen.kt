package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.MockVideoRepository
import kotlinx.coroutines.flow.first

@Composable
fun VideoPlayerScreen(videoId: String, onNavigateUp: () -> Unit) {
    val repository = remember { MockVideoRepository() }
    var video by remember { mutableStateOf<com.example.model.Video?>(null) }
    var recommended by remember { mutableStateOf(emptyList<com.example.model.Video>()) }

    LaunchedEffect(videoId) {
        val allVideos = repository.getVideos().first()
        video = allVideos.find { it.id == videoId }
        recommended = allVideos.filter { it.id != videoId }
    }

    video?.let { v ->
        Column(modifier = Modifier.fillMaxSize()) {
            // Video Player Surface
            val context = androidx.compose.ui.platform.LocalContext.current
            val exoPlayer = remember {
                androidx.media3.exoplayer.ExoPlayer.Builder(context).build().apply {
                    setMediaItem(androidx.media3.common.MediaItem.fromUri(v.videoUrl))
                    prepare()
                    playWhenReady = true
                }
            }

            DisposableEffect(Unit) {
                onDispose {
                    exoPlayer.release()
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(Color.Black)
            ) {
                androidx.compose.ui.viewinterop.AndroidView(
                    factory = {
                        androidx.media3.ui.PlayerView(context).apply {
                            player = exoPlayer
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
                
                // Navigation up
                IconButton(
                    onClick = onNavigateUp,
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Close", tint = Color.White)
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    // Video Info
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = v.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${v.views} • ${v.uploadTime}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                item {
                    // Actions
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PlayerActionButton(icon = Icons.Default.ThumbUp, label = "Like")
                        PlayerActionButton(icon = Icons.Default.ThumbDown, label = "Dislike")
                        PlayerActionButton(icon = Icons.Default.Share, label = "Share")
                        PlayerActionButton(icon = Icons.Default.Download, label = "Save")
                    }
                }
                
                item {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                }
                
                item {
                    // Channel Info
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = v.channelAvatarUrl),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(48.dp).clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = v.channelName, fontWeight = FontWeight.Bold)
                            Text(text = "100K Subscribers", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Subscribe", color = Color.White)
                        }
                    }
                }
                
                item {
                    // Description box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
                            .padding(12.dp)
                    ) {
                        Text(text = v.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                item {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = "Up next",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                items(recommended.size) { index ->
                    val rec = recommended[index]
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = rec.thumbnailUrl),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.width(160.dp).aspectRatio(16f/9f).clip(MaterialTheme.shapes.small)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = rec.title, maxLines = 2, fontWeight = FontWeight.SemiBold)
                            Text(text = rec.channelName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(text = "${rec.views} • ${rec.uploadTime}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerActionButton(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = label)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, style = MaterialTheme.typography.labelSmall)
    }
}
