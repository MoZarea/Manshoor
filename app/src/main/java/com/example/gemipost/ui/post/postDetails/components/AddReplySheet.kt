package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReplySheet(
    modifier: Modifier = Modifier,
    initialValue: String = "",
    onDismiss: () -> Unit,
    onDone: (String) -> Unit,
    bottomSheetState: SheetState = rememberModalBottomSheetState()
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = bottomSheetState,
        onDismissRequest = { onDismiss()},
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 8.dp,
        dragHandle = {}
    ) {
        var value by remember { mutableStateOf(initialValue) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                label = { Text(text = "Add your comment") },
                keyboardActions = KeyboardActions(onDone = {
                    onDone(value)
                }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
            IconButton(onClick = {
                onDone(value)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null
                )
            }
        }
    }
}