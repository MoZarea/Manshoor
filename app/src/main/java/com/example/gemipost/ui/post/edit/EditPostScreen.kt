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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.ui.post.create.component.CreatePostTopBar
import com.example.gemipost.ui.post.create.component.FilesRow
import com.gp.socialapp.presentation.post.create.component.MyTextFieldBody
import com.gp.socialapp.presentation.post.create.component.MyTextFieldTitle
import com.gp.socialapp.presentation.post.create.component.NewTagAlertDialog
import com.gp.socialapp.presentation.post.create.component.TagsRow
import com.gp.socialapp.presentation.post.create.component.uploadPostFiles
import com.gp.socialapp.presentation.post.edit.EditPostAction
import com.gp.socialapp.presentation.post.edit.EditPostScreenModel
import com.gp.socialapp.presentation.post.edit.EditPostUIState
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.edit_post

class EditPostScreen(val post: Post) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<EditPostScreenModel>()
        LifecycleEffect(
            onStarted = { screenModel.init(post) },
            onDisposed = { screenModel.dispose() }
        )
        val state by screenModel.uiState.collectAsState()
        if (state.editSuccess) {
            navigator.pop()
        }
        MaterialTheme {
            EditPostContent(
                state = state,
                action = {
                    when (it) {
                        EditPostAction.NavigateBack -> navigator.pop()
                        else -> screenModel.handleAction(it)
                    }
                }

            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditPostContent(
    action: (EditPostAction) -> Unit,
    state: EditPostUIState,

    ) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val skipPartiallyExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    var newTagDialogState by remember { mutableStateOf(false) }
    val context = LocalPlatformContext.current
    var pickedFileType: FilePickerFileType by remember { mutableStateOf(FilePickerFileType.All) }
    val filePicker = rememberFilePickerLauncher(
        type = pickedFileType,
        selectionMode = FilePickerSelectionMode.Multiple
    ) { files ->
        uploadPostFiles(
            scope,
            files,
            context,
        ) { action(EditPostAction.OnFileAdded(it)) }
        pickedFileType = FilePickerFileType.All
    }
    Scaffold(
        topBar = {
            CreatePostTopBar(
                onBackClick = { action(EditPostAction.NavigateBack) },
                stringResource(Res.string.edit_post)
            )
        }
    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {


            Spacer(modifier = Modifier.height(8.dp))
            FilesRow(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                state.postAttachments,
                onFileDelete = { file ->
                    action(EditPostAction.OnFileRemoved(file))
                },
                onAddFile = {
                    filePicker.launch()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

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
            Spacer(modifier = Modifier.weight(1f))
            TagsRow(
                allTags = state.channelTags.toList(),
                selectedTags = state.postTags.toList(),
                onTagClick = { tag ->
                    action(EditPostAction.OnTagAdded(setOf(tag)))
                },
                onAddNewTagClick = {
                    newTagDialogState = true
                }
            )
            Button(
                onClick = {
                    action(EditPostAction.OnApplyEditClicked)
                },
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
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







