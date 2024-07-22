package com.example.gemipost.ui.post.create.component

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyExistingTagAlertDialog(
    existingTagsDialogState: (Boolean) -> Unit,
    channelTags: List<Tag>,
    selectedTags: (Set<Tag>) -> Unit
) {
    var tempTags by remember { mutableStateOf(emptySet<Tag>()) }
    AlertDialog(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
        onDismissRequest = { existingTagsDialogState(false) },
        title = { Text(text = "Select Tag") },
        text = {
            if (channelTags.isEmpty()) {
                Text(text = "No tags available")
            } else
                FlowRow {
                    channelTags.toSet().forEach { tag ->
                        AssistChip(
                            onClick = {
                                tempTags = if (tempTags.contains(tag)) {
                                    tempTags - tag
                                } else {
                                    tempTags + tag
                                }
                            },
                            label = { Text(text = tag.label) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color(tag.intColor)
                            ),
                            leadingIcon = {
                                if (tempTags.contains(tag)) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = null
                                    )
                                }
                            })
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
        },
        confirmButton = {
            Button(onClick = {
                selectedTags(tempTags)
                existingTagsDialogState(false)
            }) {
                Text(
                    text = "Add"
                )
            }
        },
        dismissButton = {
            Button(
                onClick = { existingTagsDialogState(false) }
            ) {
                Text(text = "Cancel")
            }
        })
}