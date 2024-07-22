package com.example.gemipost.ui.post.create.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Tag

@Composable
fun MyTextFieldTitle(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = ""
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 4.dp, start = 16.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyTextFieldBody(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    tags: List<Tag> = emptyList(),
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 4.dp, start = 16.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            supportingText = {
                FlowRow {
                    tags.forEach { tag ->
                        Text(
                            text = "#" + tag.label,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(tag.intColor),
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }

            }
        )

    }
}