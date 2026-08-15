package com.example.data

import com.example.R
import com.example.model.Category
import com.example.model.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockVideoRepository {
    fun getCategories(): Flow<List<Category>> = flow {
        emit(listOf(
            Category("1", "All"),
            Category("2", "Gaming"),
            Category("3", "Music"),
            Category("4", "News"),
            Category("5", "Shorts"),
            Category("6", "Vlogs"),
            Category("7", "Education"),
            Category("8", "Podcasts")
        ))
    }

    fun getVideos(): Flow<List<Video>> = flow {
        emit(listOf(
            Video("1", "Stunning Mountain Landscape at Golden Hour", R.drawable.img_thumbnail_1_1786427156113, "Travel Vloggers", R.drawable.img_avatar_1_1786427185098, "1.2M views", "2 days ago", "12:34"),
            Video("2", "My Ultimate Cyberpunk Gaming Setup 2026", R.drawable.img_thumbnail_2_1786427170393, "Gamer Tech", R.drawable.img_avatar_2_1786427199229, "850K views", "5 hours ago", "8:15"),
            Video("3", "How to Build a Mobile App in 10 Minutes", R.drawable.img_thumbnail_1_1786427156113, "Code Master", R.drawable.img_avatar_1_1786427185098, "2.4M views", "1 month ago", "10:00"),
            Video("4", "Relaxing Rain Sounds for Sleep", R.drawable.img_thumbnail_2_1786427170393, "Zen Vibes", R.drawable.img_avatar_2_1786427199229, "5M views", "1 year ago", "10:00:00")
        ))
    }
    
    fun getShorts(): Flow<List<Video>> = flow {
         emit(listOf(
            Video("s1", "Best Mountain View Ever!", R.drawable.img_thumbnail_1_1786427156113, "Travel Vloggers", R.drawable.img_avatar_1_1786427185098, "500K views", "1 day ago", "0:59", isShort = true),
            Video("s2", "New RGB Keyboard Unboxing", R.drawable.img_thumbnail_2_1786427170393, "Gamer Tech", R.drawable.img_avatar_2_1786427199229, "1.1M views", "3 days ago", "0:45", isShort = true)
        ))
    }
}
