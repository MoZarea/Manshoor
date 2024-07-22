package com.example.gemipost.navigation

import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Tag
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object SignUp

@Serializable
object ForgotPassword

@Serializable
object Feed
@Serializable
data class PostDetails(val post: Post)
@Serializable
object Search
@Serializable
data class SearchResult(val query: String, val isTag: Boolean, val tag: Tag)
@Serializable
object CreatePost
@Serializable
data class EditPost(val post: Post)
