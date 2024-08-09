package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.example.gemipost.utils.LocalDateTimeUtil.getPostDate

@Composable
 fun ReplyDate(createdAt: Long) {
    Text(
        text = createdAt.getPostDate(),
        modifier = Modifier.padding(
            start = 4.dp, end = 8.dp
        ), color = Color.Gray,
        fontSize = 12.sp
    )
}