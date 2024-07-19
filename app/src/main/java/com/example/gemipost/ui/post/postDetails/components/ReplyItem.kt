package com.gp.socialapp.presentation.post.postDetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.gp.socialapp.presentation.post.feed.ReplyEvent


@Composable
fun ReplyItem(
    nestedReply: NestedReply,
    currentUserId: String,
    level: Int,
    replyEvent: (ReplyEvent) -> Unit,
) {
    val padding = with(LocalDensity.current) { 16.dp.toPx() }
    Column {
        val color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer
        androidx.compose.material3.Card(
            modifier = Modifier.drawBehind {
                repeat(level + 1) {
                    drawLine(
                        color = color.copy(alpha = 1f),
                        start = Offset(it * padding, 0f),
                        end = Offset(it * padding, size.height),
                        strokeWidth = 2f
                    )
                }
            }.padding(start = (16.dp * level) + 8.dp, end = 8.dp),
            shape = ShapeDefaults.Medium,
//            border = BorderStroke(1.dp, Color.Gray),
            colors = CardDefaults.cardColors(
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.03f),
                )
        ) {
            ReplyContent(nestedReply, replyEvent, currentUserId)
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}

data class ReplyDropDownItem(
    val text: String, val onClick: () -> Unit
)