package com.gp.socialapp.presentation.post.create

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
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
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
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.ui.post.create.component.CreatePostTopBar
import com.example.gemipost.ui.post.create.component.FilesRow
import com.gp.socialapp.presentation.post.create.component.MyTextFieldBody
import com.gp.socialapp.presentation.post.create.component.MyTextFieldTitle
import com.gp.socialapp.presentation.post.create.component.NewTagAlertDialog
import com.gp.socialapp.presentation.post.create.component.TagsRow
import com.gp.socialapp.presentation.post.create.component.uploadPostFiles
import com.gp.socialapp.presentation.post.feed.FeedTab
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.create_post

data class CreatePostScreen(val openedFeedTab: FeedTab, val communityId: String) : Screen {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {


        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<CreatePostScreenModel>()
        val state by screenModel.uiState.collectAsState()
        LifecycleEffect(
            onStarted = {
                screenModel.init(openedFeedTab, communityId)
            }
        )
        val existingTags by screenModel.channelTags.collectAsState()
        if (state.createdState) {
            navigator.pop()
            screenModel.resetUiState()
        }
        MaterialTheme {
            CreatePostContent(
                state = state,
                channelTags = existingTags,
                onBackClick = { navigator.pop() },
                onPostClick = { title, body, tags ->
                    screenModel.onCreatePost(
                        title = title,
                        body = body,
                    )
                },
                confirmNewTags = {
                    screenModel.insertNewTags(it)
                },
                onAddTag = screenModel::onAddTag,
                onAddFile = screenModel::onAddFile,
                onRemoveFile = screenModel::onRemoveFile
            )
        }

    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
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
        val context = LocalPlatformContext.current
        var pickedFileTypes: FilePickerFileType by remember { mutableStateOf(FilePickerFileType.All) }
        val filePicker = rememberFilePickerLauncher(
            type = pickedFileTypes,
            selectionMode = FilePickerSelectionMode.Multiple,
            onResult = { files ->
                uploadPostFiles(
                    scope,
                    files,
                    context,
                    onAddFile,
                )
                pickedFileTypes = FilePickerFileType.All
            }
        )
        Scaffold(
            topBar = {
                CreatePostTopBar(
                    onBackClick = onBackClick,
                    stringResource(Res.string.create_post),
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
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    state.files,
                    onFileDelete = { file ->
                        onRemoveFile(file)
                    },
                    onAddFile = {
                        filePicker.launch()
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
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
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


}


