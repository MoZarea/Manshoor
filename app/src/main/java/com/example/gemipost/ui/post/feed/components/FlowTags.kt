package com.example.gemipost.ui.post.feed.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Tag

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsFlowRow(
    selectedTags: Set<Tag>,
    onTagClicked: (Tag) -> Unit,
) {
    if (selectedTags.isEmpty()) return
    FlowRow(
        horizontalArrangement = Arrangement.Start,
    ) {
        selectedTags.toList().forEach { tag ->
            TagItem(
                onTagClicked = onTagClicked,
                tag = tag
            )
            Spacer(modifier = Modifier.padding(2.dp))
        }
    }
}