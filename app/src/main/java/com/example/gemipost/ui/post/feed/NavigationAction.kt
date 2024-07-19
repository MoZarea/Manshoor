package com.gp.socialapp.presentation.post.feed

import com.example.gemipost.data.post.source.remote.model.Post

sealed class NavigationAction {
    object NavigateToSearch : NavigationAction()
    data class NavigateToPostDetails(val post: Post) : NavigationAction()
}