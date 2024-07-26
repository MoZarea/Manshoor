package com.example.gemipost.data.post.source.remote

import android.graphics.Bitmap

interface ModerationRemoteDataSource {
    suspend fun validateText(vararg texts: String): Boolean
    suspend fun validateTextWithImages(vararg texts: String, images: List<Bitmap>): Boolean
}