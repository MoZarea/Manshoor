package com.example.gemipost.ui.post.create.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddNewFileButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 20f))
    )

    Box(
        modifier = modifier
            .size(64.dp)
            .drawBehind {
                drawRoundRect(
                    color = Color.Gray,
                    style = stroke,
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            }
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }

    ) {

        Text(
            text = "+",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
@Preview
@Composable
fun AddNewFileButtonPreview() {
    AddNewFileButton {}
}