package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.delete
import socialmultiplatform.composeapp.generated.resources.edit

@Composable
fun OptionButton(
    onEditPostClicked: () -> Unit,
    onDeletePostClicked: () -> Unit,
    onReportPostClicked: () -> Unit,
    isAuthor: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                )
        ) {
            Icon(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = "More Options",
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                clippingEnabled = true

            )
        ) {
            if (isAuthor) {
                DropdownMenuItem(
                    onClick = {
                        onEditPostClicked()
                        expanded = false
                    },
                    text = {
                        Text(text = stringResource(Res.string.edit))
                    }
                )
                DropdownMenuItem(
                    onClick = {
                        onDeletePostClicked()
                        expanded = false
                    },
                    text = {
                        Text(text = stringResource(Res.string.delete))
                    }
                )
                DropdownMenuItem(
                    onClick = {
                        onReportPostClicked()
                        expanded = false
                    },
                    text = {
                        Text(text = "Report")
                    }
                )

            }
        }
    }
}