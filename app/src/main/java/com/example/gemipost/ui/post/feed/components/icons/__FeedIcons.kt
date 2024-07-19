package com.gp.socialapp.presentation.post.feed.components.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Attachment1
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Attachment2
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Comment
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Dislikefilled
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Dislikeoutlined
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Likefilled
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Likeoutlined
import kotlin.collections.List as ____KtList

public object FeedIcons

private var __AllIcons: ____KtList<ImageVector>? = null

public val FeedIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Attachment1, Attachment2, Comment, Dislikefilled, Dislikeoutlined,
        Likefilled, Likeoutlined)
    return __AllIcons!!
  }
