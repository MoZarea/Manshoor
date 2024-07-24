package com.example.gemipost.ui.post.create

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gemipost.R
import com.example.gemipost.ui.post.create.component.CreatePostTopBar
import com.example.gemipost.ui.post.create.component.FilesRow
import com.example.gemipost.ui.post.create.component.MyTextFieldBody
import com.example.gemipost.ui.post.create.component.MyTextFieldTitle
import com.example.gemipost.ui.post.create.component.NewTagAlertDialog
import com.example.gemipost.ui.post.create.component.TagsRow
import com.example.gemipost.ui.theme.GemiPostTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreatePostScreen(
    onNavigateBack: () -> Unit
) {
    val viewModel: CreatePostViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    CreatePostContent(
        action = viewModel::handleEvent,
        state = state,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
fun CreatePostContent(
    action: (CreatePostEvents) -> Unit,
    state: CreatePostUIState,
    onNavigateBack: () -> Unit,

    ) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { selectedUri ->
                action(CreatePostEvents.OnAddFile(selectedUri))
            }
        }

    )
    var newTagDialogState by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CreatePostTopBar(
                onBackClick = onNavigateBack,
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
            MyTextFieldTitle(
                value = state.title,
                label = "Title",
                onValueChange = { newTitle ->
                    action(CreatePostEvents.OnTitleChanged(newTitle))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            MyTextFieldBody(
                value = state.body,
                label = "Body",
                onValueChange = { newBody ->
                    action(CreatePostEvents.OnBodyChanged(newBody))
                },
                tags = state.tags,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            FilesRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                state.files,
                onFileDelete = { file ->
                    action(CreatePostEvents.OnRemoveFile(file))
                },
                onAddFile = {
                    galleryLauncher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            TagsRow(
                selectedTags = state.tags,
                onTagClick = { tag ->
                    action(CreatePostEvents.OnRemoveTag(tag))
                },
                onAddNewTagClick = {
                    newTagDialogState = true
                }
            )
            Button(
                onClick = {
                    action(CreatePostEvents.OnCreatePostClicked)
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                enabled = state.title.isNotBlank() && state.body.isNotBlank(),
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
                confirmNewTags = {
                    action(CreatePostEvents.OnAddTag(it))
                },
            )

        }
    }
}

@Composable
@Preview
fun CreatePostContentPreview() {
    GemiPostTheme {
        CreatePostContent(
            action = {},
            state = CreatePostUIState(),
            onNavigateBack = {}
        )
    }
}



