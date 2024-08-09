package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
    isLast: Boolean,
    currentUserId: String,
    level: Int,
    replyEvent: (ReplyEvent) -> Unit,
) {

    val padding = with(LocalDensity.current) { 16.dp.toPx() }
    Column {
        val color = MaterialTheme.colorScheme.outline
        androidx.compose.material3.Card(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .drawBehind {
                    if(level > 0){
                        repeat(level) {
                            drawLine(
                                color = color.copy(alpha = 1f),
                                start = Offset((it+1) * padding + 4.dp.toPx(), -8.dp.toPx() ),
                                end = Offset((it+1) * padding + 4.dp.toPx(),if(isLast && level == 1) size.height / 2 else size.height+8.dp.toPx()),
                                strokeWidth = 2.dp.toPx()
                            )
                            if(isLast && level == 1){
                                drawLine(
                                    color = color.copy(alpha = 1f),
                                    start = Offset((it+1) * padding + 4.dp.toPx(), size.height / 2),
                                    end = Offset((it+1) * padding + 8.dp.toPx(), size.height / 2),
                                    strokeWidth = 2.dp.toPx()
                                )
                            }
                        }
                    }
                }
                .padding(start = (16.dp * level)+8.dp , end = 8.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.03f),
            )
        ) {
            ReplyContent(reply, replyEvent, currentUserId)
        }
        if(isLast && level == 1){
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

data class ReplyDropDownItem(
    val text: String, val onClick: () -> Unit
)