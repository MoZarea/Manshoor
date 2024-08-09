package com.example.gemipost.ui.post.postDetails.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
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
    Log.d("seerde", "ReplyItem: ${reply.content}")
    val padding = with(LocalDensity.current) { 16.dp.toPx() }

    Column(
        modifier = Modifier.padding(start = (16.dp * level) + 8.dp, end = 8.dp),
    ) {
        val color = MaterialTheme.colorScheme.onPrimaryContainer
        androidx.compose.material3.Card(
            modifier = Modifier.padding(vertical = 4.dp).drawBehind {
                if (level != 0) {
                    val xOffset = -8.dp.toPx()
//                    val xOffset = level * padding - 7.dp.toPx()*level
                    val yOffset = size.height / 3
                    drawLine(
                        color = color.copy(alpha = 1f),
                        start = Offset(xOffset, -9.dp.toPx()),
                        end = Offset(xOffset, yOffset)
                    )
                    drawLine(
                        color = color.copy(alpha = 1f),
                        start = Offset(xOffset, yOffset),
                        end = Offset(xOffset + 8.dp.toPx(), yOffset)
                    )
//                    // Draw the curved path
//                    val path = androidx.compose.ui.graphics.Path().apply {
//                        moveTo(xOffset, 0f)
//                        cubicTo(
//                            x1 = xOffset,
//                            y1 = 0f,
//                            x2 = xOffset + 4.dp.toPx(),
//                            y2 = yOffset,
//                            x3 = xOffset + 8.dp.toPx(),
//                            y3 = size.height / 2
//                        )
//                    }
//                    drawPath(
//                        path = path, color = color.copy(alpha = 1f), style = Stroke(width = 2f)
//                    )
                }

            }, shape = MaterialTheme.shapes.medium, colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                    alpha = 0.03f
                ),
            )
        ) {
            ReplyContent(reply, replyEvent, currentUserId)
        }
    }
}

data class ReplyDropDownItem(
    val text: String, val onClick: () -> Unit
)
