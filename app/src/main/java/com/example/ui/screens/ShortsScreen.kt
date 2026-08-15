package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
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
fun ShortsScreen() {
    val repository = remember { MockVideoRepository() }
    var shorts by remember { mutableStateOf(emptyList<com.example.model.Video>()) }

    LaunchedEffect(Unit) {
        shorts = repository.getShorts().first()
    }

    if (shorts.isNotEmpty()) {
        val pagerState = rememberPagerState(pageCount = { shorts.size })
        
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize().background(Color.Black)
        ) { page ->
            val video = shorts[page]
            val context = androidx.compose.ui.platform.LocalContext.current
            val isActive = pagerState.currentPage == page
            
            val exoPlayer = remember {
                androidx.media3.exoplayer.ExoPlayer.Builder(context).build().apply {
                    setMediaItem(androidx.media3.common.MediaItem.fromUri(video.videoUrl))
                    repeatMode = androidx.media3.common.Player.REPEAT_MODE_ALL
                    prepare()
                }
            }

            LaunchedEffect(isActive) {
                if (isActive) {
                    exoPlayer.play()
                } else {
                    exoPlayer.pause()
                }
            }

            DisposableEffect(Unit) {
                onDispose {
                    exoPlayer.release()
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                androidx.compose.ui.viewinterop.AndroidView(
                    factory = {
                        androidx.media3.ui.PlayerView(context).apply {
                            player = exoPlayer
                            useController = false
                            // Hide buffering and other overlays
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
                
                // Overlay Content
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Left side details
                    Column(
                        modifier = Modifier.weight(1f).padding(bottom = 16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = video.channelAvatarUrl),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(32.dp).clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = video.channelName,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                modifier = Modifier.height(30.dp)
                            ) {
                                Text("Subscribe", color = Color.White)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = video.title,
                            color = Color.White,
                            maxLines = 2
                        )
                    }
                    
                    // Right side actions
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        ActionIcon(Icons.Filled.ThumbUp, "Like", "12K")
                        Spacer(modifier = Modifier.height(16.dp))
                        ActionIcon(Icons.Filled.ThumbDown, "Dislike", "Dislike")
                        Spacer(modifier = Modifier.height(16.dp))
                        ActionIcon(Icons.AutoMirrored.Filled.Comment, "Comment", "420")
                        Spacer(modifier = Modifier.height(16.dp))
                        ActionIcon(Icons.Filled.Share, "Share", "Share")
                    }
                }
            }
        }
    }
}

@Composable
fun ActionIcon(icon: androidx.compose.ui.graphics.vector.ImageVector, contentDescription: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = {}) {
            Icon(imageVector = icon, contentDescription = contentDescription, tint = Color.White, modifier = Modifier.size(32.dp))
        }
        Text(text = label, color = Color.White, style = MaterialTheme.typography.labelSmall)
    }
}
