package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.gp.socialapp.util.ModerationSafety

@Composable
fun ReplyBody(
    body: String,
    moderationStatus: String
) {
    var text = body
    if(moderationStatus == ModerationSafety.UNSAFE_REPLY.name){
        text = "This content has been removed due to violation of community guidelines"
    }
    Text(
        text = text,
        modifier = Modifier.padding(
            start = 8.dp, end = 4.dp, top = 4.dp
        ),
        fontSize = 14.sp,
    )
}