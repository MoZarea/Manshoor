import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gemipost.R
import com.example.gemipost.ui.post.create.component.CreatePostTopBar
import com.example.gemipost.ui.post.create.component.FilesRow
import com.example.gemipost.ui.post.create.component.MyTextFieldBody
import com.example.gemipost.ui.post.create.component.MyTextFieldTitle
import com.example.gemipost.ui.post.create.component.NewTagAlertDialog
import com.example.gemipost.ui.post.create.component.TagsRow
import com.example.gemipost.ui.post.edit.EditPostAction
import com.example.gemipost.ui.post.edit.EditPostUIState
import com.example.gemipost.ui.post.edit.EditPostViewModel
import com.example.gemipost.utils.PostResults
import com.example.gemipost.utils.isNotIdle
import com.example.gemipost.utils.userMessage
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditPostScreen(
    viewModel: EditPostViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    postId: String
) {
    viewModel.init(postId)
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    EditPostContent(
        action = viewModel::handleActions,
        state = state,
        navigationBack = {
            onNavigateBack()
            viewModel.resetState()
        }
    )
}

@Composable
fun EditPostContent(
    action: (EditPostAction) -> Unit,
    state: EditPostUIState,
    navigationBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = state.actionResult) {
        if (state.actionResult.isNotIdle()) {
            if (state.actionResult == PostResults.POST_UPDATED) {
                navigationBack()
            }
            snackbarHostState.showSnackbar(state.actionResult.userMessage())
        }
    }
    var newTagDialogState by remember { mutableStateOf(false) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { selectedUri ->
                action(EditPostAction.OnFileAdded(selectedUri))
            }
        }

    )
    Scaffold(
        topBar = {
            CreatePostTopBar(
                onBackClick = navigationBack,
                stringResource(R.string.edit_post)
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { it ->

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            AnimatedVisibility (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            MyTextFieldTitle(
                value = state.title,
                label = "Title",
                onValueChange = { newTitle ->
                    action(EditPostAction.OnTitleChanged(newTitle))
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
                    action(EditPostAction.OnContentChanged(newBody))
                },
                tags = state.postTags.toList(),
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
                state.postAttachments,
                onFileDelete = { file ->
                    action(EditPostAction.OnFileRemoved(file))
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
                selectedTags = state.postTags.toList(),
                onTagClick = { tag ->
                    action(EditPostAction.OnTagAdded(tag))
                },
                onAddNewTagClick = {
                    newTagDialogState = true
                }
            )
            Button(
                onClick = {
                    action(EditPostAction.OnApplyEditClicked)
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
                    action(EditPostAction.OnTagAdded(it))
                },
            )

        }
    }
}







