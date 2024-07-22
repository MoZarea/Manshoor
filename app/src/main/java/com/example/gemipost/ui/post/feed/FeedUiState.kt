package com.example.gemipost.ui.post.feed

import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Tag
import com.gp.socialapp.util.PostError
import com.gp.socialapp.util.Result

data class FeedUiState(
    val posts: List<Post> = emptyList(),
    val isFeedLoaded: Result<Unit,PostError> = Result.Loading,
    val isSortedByNewest: Boolean = true,
    val allTags: Set<Tag> = emptySet(),
    val selectedTags: Set<Tag> = emptySet(),
    val error: FeedError = FeedError.NoError,
    val openedTabItem: FeedTab = FeedTab.GENERAL,
    val currentUserID: String = "",
    val currentUser: User = User(),
    val isLoggedOut: Boolean = false
)


