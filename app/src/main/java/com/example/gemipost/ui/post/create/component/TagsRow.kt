package com.example.gemipost.ui.post.create.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Tag

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TagsRow(
    selectedTags: List<Tag>,
    onTagClick: (Tag) -> Unit,
    onAddNewTagClick: () -> Unit,
) {

    var tagsVisible by remember { mutableStateOf(false) }
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
    ) {
        item {
            MyOutlinedTextButton(
                onClick = {
                    tagsVisible = !tagsVisible
                },
                label = "Tags"
            )

        }
        if (tagsVisible) {

            item {
                Spacer(modifier = Modifier.width(4.dp))
                MyIconButton(
                    onClick = {
                        onAddNewTagClick()
                    },
                    icon = Icons.Default.Add
                )
                Spacer(modifier = Modifier.width(4.dp))

            }
            val tags = (selectedTags ).distinct()
            items(tags) { tag ->
                AssistChip(
                    label = { Text(tag.label) },
                    onClick = {
                        onTagClick(tag)
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(tag.intColor),
                        labelColor = Color.White,
                        leadingIconContentColor = Color.White
                    ),
                    leadingIcon = {
                        if (selectedTags.contains(tag)) Icon(
                            Icons.Filled.Check,
                            contentDescription = null
                        )
                        else null
                    },
                    modifier = Modifier.animateItemPlacement()

                )
                Spacer(modifier = Modifier.width(2.dp))
            }
        }
    }

}