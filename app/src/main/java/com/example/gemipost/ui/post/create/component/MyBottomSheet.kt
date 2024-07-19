package com.gp.socialapp.presentation.post.create.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomSheet(
    openBottomSheet: (Boolean) -> Unit,
    bottomSheetState: SheetState,
    newTagDialogState: (Boolean) -> Unit,
    existingTagsDialogState: (Boolean) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { openBottomSheet(false) },
        sheetState = bottomSheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                onClick = { newTagDialogState(true) },
            ) {
                Text(
                    text = "Add New Tag",
                )
            }
            Button(
                onClick = { existingTagsDialogState(true) },
            ) {
                Text(
                    text = "Select Old Tag",
                )
            }
        }
    }
}