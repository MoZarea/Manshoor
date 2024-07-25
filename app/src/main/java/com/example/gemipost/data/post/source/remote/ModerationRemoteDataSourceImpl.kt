package com.example.gemipost.data.post.source.remote

import android.graphics.Bitmap
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

class ModerationRemoteDataSourceImpl(
    private val model: GenerativeModel
) : ModerationRemoteDataSource {
    override suspend fun validateText(vararg texts: String): Boolean {
        val inputContent = content {
            texts.forEach {
                text(it)
            }
            text("Act as a judge for a content moderation system. Our platform doesn't allow nudity or gore or any toxicity, should this content be removed from the platform or not? reply with only 1 if yes and 0 if no")
        }
        val response = model.generateContent(inputContent)
        Log.d("seerde", "validateText: ${response.text}")
        return response.text == "1"
    }

    override suspend fun validateTextWithImages(vararg texts: String, images: List<Bitmap>): Boolean {
        val inputContent = content {
            texts.forEach {
                text(it)
            }
            images.forEach {
                image(it)
            }
            text("Act as a judge for a content moderation system. Our platform doesn't allow nudity or gore or any toxicity, should this content be removed from the platform or not? reply with only 1 if yes and 0 if no")
        }
        val response = model.generateContent(inputContent)
        Log.d("seerde", "validateTextWithImages: ${response.text}")
        return response.text == "1"
    }

}