package com.example.gemipost.ui.post.create

import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.ui.post.feed.FeedTab


data class CreatePostUIState(
    var userProfilePicURL: String = "",
    var title: String = "",
    var body: String = "",
    var createdState: Boolean = false,
    var cancelPressed: Boolean = false,
    var tags: List<Tag> = emptyList(),
    var type: String = FeedTab.entries.first().title,
    var files: List<PostAttachment> = emptyList(),
    var currentTab: FeedTab = FeedTab.entries.first(),
    var communityId: String = "",
)
