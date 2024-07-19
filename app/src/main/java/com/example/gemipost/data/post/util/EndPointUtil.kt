package com.example.gemipost.data.post.util

import com.gp.socialapp.util.AppConstants
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom

fun HttpRequestBuilder.endPoint(path: String) {
    url {
        takeFrom(AppConstants.BASE_URL)
        path(path)
        contentType(ContentType.Application.Json)
    }
}