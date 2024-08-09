package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.ui.post.feed.ReplyEvent


@Composable
fun ReplyItem(
    reply: Reply,
    currentUserId: String,
    level: Int,
    replyEvent: (ReplyEvent) -> Unit,
) {

    val padding = with(LocalDensity.current) { 16.dp.toPx() }
    Column {
        val color = MaterialTheme.colorScheme.onPrimaryContainer
        androidx.compose.material3.Card(
            modifier = Modifier
                .drawBehind {
                    repeat(level + 1) {
                        drawLine(
                            color = color.copy(alpha = 1f),
                            start = Offset(it * padding-7.dp.toPx(), 0f),
                            end = Offset(it * padding-7.dp.toPx(), size.height),
                            strokeWidth = 2f
                        )
                    }
                }
                .padding(start = (16.dp * level)+8.dp , end = 8.dp).clickable { replyEvent(ReplyEvent.OnReplyClicked(reply)) },
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            )
        ) {
            ReplyContent(reply, replyEvent, currentUserId)
        }
//        Spacer(modifier = Modifier.height(4.dp))
    }
}

data class ReplyDropDownItem(
    val text: String, val onClick: () -> Unit
)