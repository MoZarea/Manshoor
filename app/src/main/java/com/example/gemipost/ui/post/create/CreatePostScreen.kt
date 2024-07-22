package com.example.gemipost.ui.post.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gemipost.R
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.ui.post.create.component.CreatePostTopBar
import com.example.gemipost.ui.post.create.component.FilesRow
import com.example.gemipost.ui.post.create.component.MyTextFieldBody
import com.example.gemipost.ui.post.create.component.MyTextFieldTitle
import com.example.gemipost.ui.post.create.component.NewTagAlertDialog
import com.example.gemipost.ui.post.create.component.TagsRow


@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CreatePostContent(
    state: CreatePostUIState,
    channelTags: List<Tag>,
    onBackClick: () -> Unit,
    onPostClick: (String, String, tags: Set<Tag>) -> Unit,
    onAddTag: (Tag) -> Unit,
    confirmNewTags: (Set<Tag>) -> Unit,
    onAddFile: (PostAttachment) -> Unit,
    onRemoveFile: (PostAttachment) -> Unit
    ) {

        var tags by remember { mutableStateOf(setOf<Tag>()) }
        var openBottomSheet by rememberSaveable { mutableStateOf(false) }
        val skipPartiallyExpanded by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded
        )
        var existingTagsDialogState by remember { mutableStateOf(false) }
        var newTagDialogState by remember { mutableStateOf(false) }
        var title by remember { mutableStateOf("") }
        var body by remember { mutableStateOf("") }
        Scaffold(
            topBar = {
                CreatePostTopBar(
                    onBackClick = onBackClick,
                    stringResource(id = R.string.create_post),
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                FilesRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    state.files,
                    onFileDelete = { file ->
                        onRemoveFile(file)
                    },
                    onAddFile = {
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                MyTextFieldTitle(
                    value = title,
                    label = "Title",
                    onValueChange = { newTitle ->
                        title = newTitle
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                MyTextFieldBody(
                    value = body,
                    label = "Body",
                    onValueChange = { newBody ->
                        body = newBody
                    },
                    tags = state.tags,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                TagsRow(
                    allTags = channelTags,
                    selectedTags = state.tags,
                    onTagClick = { tag ->
                        onAddTag(tag)
                    },
                    onAddNewTagClick = {
                        newTagDialogState = true
                    }
                )
                Button(
                    onClick = {
                        onPostClick(title, body, tags)
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    enabled = title.isNotBlank() && body.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text(
                        text = "+ Post"
                    )
                }
            }
            if (newTagDialogState) {
                NewTagAlertDialog(
                    newTagDialogState = { value ->
                        newTagDialogState = value
                    },
                    confirmNewTags = confirmNewTags,
                )

            }
        }
    }

@Composable
@Preview
fun CreatePostContentPreview() {
    CreatePostContent(
        state = CreatePostUIState(),
        channelTags = emptyList(),
        onBackClick = {},
        onPostClick = { _, _, _ -> },
        onAddTag = {},
        confirmNewTags = {},
        onAddFile = {},
        onRemoveFile = {}
    )
}



