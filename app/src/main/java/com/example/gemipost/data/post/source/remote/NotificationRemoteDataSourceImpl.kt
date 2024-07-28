package com.example.gemipost.data.post.source.remote

import com.example.gemipost.utils.AccessToken
import com.google.firebase.messaging.FirebaseMessaging
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom

class NotificationRemoteDataSourceImpl(
    private val firebaseMessaging: FirebaseMessaging,
    private val httpClient: HttpClient
) : NotificationRemoteDataSource {
    override suspend fun sendNotificationToAuthor(
        replyId: String,
        replyContent: String,
        authorName: String,
        parentReplyId: String,
        postId: String
    ) {
        val accessToken = AccessToken.getAccessToken()
        val response = httpClient.post {
            url {
                takeFrom("https://fcm.googleapis.com/")
                path("v1/projects/gemipost/messages:send")
                contentType(ContentType.Application.Json)
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.Authorization, "Bearer $accessToken")
            }
            setBody{

            }
        }
    }

    override fun subscribeToTopic(postId: String, replyId: String) {
        val topic = "post-$postId-reply-$replyId"
        firebaseMessaging.subscribeToTopic(topic)
    }
}