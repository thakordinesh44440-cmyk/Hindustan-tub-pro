package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.data.MockVideoRepository
import com.example.ui.components.VideoCard
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onVideoClick: (String) -> Unit) {
    val repository = remember { MockVideoRepository() }
    var categories by remember { mutableStateOf(emptyList<com.example.model.Category>()) }
    var videos by remember { mutableStateOf(emptyList<com.example.model.Video>()) }
    var selectedCategoryId by remember { mutableStateOf("1") }

    LaunchedEffect(Unit) {
        categories = repository.getCategories().first()
        videos = repository.getVideos().first()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Categories Row
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = category.id == selectedCategoryId,
                    onClick = { selectedCategoryId = category.id },
                    label = { Text(category.name) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.onSurface,
                        selectedLabelColor = MaterialTheme.colorScheme.surface,
                    )
                )
            }
        }
        
        // Video Feed
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp) // space for bottom nav
        ) {
            items(videos) { video ->
                VideoCard(video = video, onClick = { onVideoClick(video.id) })
            }
        }
    }
}
