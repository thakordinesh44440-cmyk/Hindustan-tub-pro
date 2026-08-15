package com.example.model

data class Video(
    val id: String,
    val title: String,
    val thumbnailUrl: Int, // Using drawable res ID for local mock
    val channelName: String,
    val channelAvatarUrl: Int,
    val views: String,
    val uploadTime: String,
    val duration: String,
    val description: String = "This is a great video about something interesting. Don't forget to like and subscribe!",
    val videoUrl: String = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
    val isShort: Boolean = false
)

data class Category(
    val id: String,
    val name: String
)
