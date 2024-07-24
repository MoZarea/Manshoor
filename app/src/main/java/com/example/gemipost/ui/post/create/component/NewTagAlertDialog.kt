package com.example.gemipost.ui.post.create.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.ui.theme.TagsColorPalette.fixedColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewTagAlertDialog(
    newTagDialogState: (Boolean) -> Unit,
    confirmNewTags: (Tag) -> Unit
) {
    var tempTag by remember { mutableStateOf(Tag("", 0)) }
    val keyboardController = LocalSoftwareKeyboardController.current
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onDismissRequest = { newTagDialogState(false) },
        title = { Text(text = "Add new Tag") },
        text = {
            Column {
                OutlinedTextField(
                    value = tempTag.label,
                    onValueChange = {
                        tempTag = tempTag.copy(label = it)
                    },
                    label = { Text(text = "Tag") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Label,
                            contentDescription = null,
                            tint = Color(tempTag.intColor)
                        )
                    })
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow {
                    fixedColor.forEach { color ->
                        AssistChip(
                            onClick = {
                                tempTag = tempTag.copy(intColor = color.toArgb())
                            },
                            label = { Text(text = "") },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = color
                            ),
                            leadingIcon = {
                                if (tempTag.intColor == color.toArgb()) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = null
                                    )
                                }
                            })
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                confirmNewTags(tempTag)
                newTagDialogState(false)
            }) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            Button(onClick = { newTagDialogState(false) }) {
                Text(text = "Cancel")
            }
        }
    )
}