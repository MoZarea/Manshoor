package com.example.gemipost.ui.post.create.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gp.socialapp.presentation.post.create.component.MyOutlinedTextButton

@Composable
fun BottomOptionRow(
    modifier: Modifier = Modifier,
    onAddTagClicked: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MyOutlinedTextButton(
            onClick = {
                onAddTagClicked()
            },
            label = "Tags"
        )
    }

}



