package com.example.gemipost.ui.post.feed.components

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Tag

@Composable
fun TagItem(
    modifier: Modifier = Modifier,
    onTagClicked: (Tag) -> Unit,
    tag: Tag
) {
    SuggestionChip(
        onClick = {
            onTagClicked(tag)
        },
        shape = RoundedCornerShape(8.dp),
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = Color(tag.intColor),
        ),
        label = {
            Text(
                text = tag.label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )
        },
        modifier = modifier
            .sizeIn(
                maxHeight = 24.dp,
            )

    )
}
