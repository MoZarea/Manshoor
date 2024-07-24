package com.example.gemipost.ui.post.postDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gemipost.R
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.ui.post.feed.PostEvent
import com.example.gemipost.ui.post.feed.ReplyEvent
import com.example.gemipost.ui.post.feed.components.FeedPostItem
import com.example.gemipost.ui.post.postDetails.components.AddReplySheet
import com.example.gemipost.ui.post.postDetails.components.RepliesList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailsScreen(
    postId: String,
    onBackPressed: () -> Unit,
    onTagClicked: (Tag) -> Unit,
    viewModel: PostDetailsViewModel = koinViewModel()
) {
    LaunchedEffect(true){
        viewModel.initScreenModel(postId)
    }
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    var clickedReply by remember { mutableStateOf<Reply?>(null) }
    var isReportDialogVisible by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    var isEditingReply by remember { mutableStateOf(false) }
    PostDetailsContent(
        replies = state.currentReplies,
        onPostEvent = { postEvent ->
            when (postEvent) {
                is PostEvent.OnCommentClicked -> {
                    scope.launch {
                        if (bottomSheetState.isVisible) {
                            bottomSheetState.hide()
                        } else {
                            bottomSheetState.show()
                        }
                    }
                }

                is PostEvent.OnTagClicked -> {
                    onTagClicked(postEvent.tag)
                }

                is PostEvent.OnPostAuthorClicked -> {}

                else -> viewModel.handlePostEvent(postEvent)
            }
        },
        onReplyEvent = { replyEvent ->
            when (replyEvent) {
                is ReplyEvent.OnReportReply -> {
                    isReportDialogVisible = true
                    clickedReply = replyEvent.reply
                }

                is ReplyEvent.OnAddReply -> {
                    scope.launch {
                        if (bottomSheetState.isVisible) {
                            clickedReply = null
                            bottomSheetState.hide()
                        } else {
                            bottomSheetState.show()
                            clickedReply = replyEvent.reply
                        }
                    }
                }

                is ReplyEvent.OnEditReply -> {
                    scope.launch {
                        if (bottomSheetState.isVisible) {
                            isEditingReply = false
                            clickedReply = null
                            bottomSheetState.hide()
                        } else {
                            clickedReply = replyEvent.reply
                            isEditingReply = true
                            bottomSheetState.show()
                        }
                    }
                }

                is ReplyEvent.OnReplyAuthorClicked -> {}

                else -> viewModel.handleReplyEvent(replyEvent)
            }
        },
        currentUserID = state.currentUserId,
        clickedReply = clickedReply,
        bottomSheetState = bottomSheetState,
        onDismissAddReplyBottomSheet = {
            scope.launch {
                bottomSheetState.hide()
            }
        },
        isReportDialogVisible = isReportDialogVisible,
        onDismissReportDialog = {
            isReportDialogVisible = false
        },
        onConfirmReport = {
            isReportDialogVisible = false
            viewModel.handleReplyEvent(ReplyEvent.OnReplyReported(reply = clickedReply!!))
            clickedReply = null
        },
        onResetActionResult = viewModel::resetActionResult,
        onBackPressed = { onBackPressed() },
        isEditingReply = isEditingReply,
        post = state.post
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostDetailsContent(
    modifier: Modifier = Modifier,
    post: Post,
    replies: List<NestedReply>,
    onPostEvent: (PostEvent) -> Unit,
    onReplyEvent: (ReplyEvent) -> Unit,
    currentUserID: String,
    actionResult: PostDetailsActionResult = PostDetailsActionResult.NoActionResult,
    scope: CoroutineScope = rememberCoroutineScope(),
    onResetActionResult: () -> Unit,
    clickedReply: Reply?,
    isEditingReply: Boolean,
    bottomSheetState: SheetState,
    isReportDialogVisible: Boolean = false,
    onDismissReportDialog: () -> Unit,
    onConfirmReport: () -> Unit,
    onDismissAddReplyBottomSheet: () -> Unit,
    onBackPressed: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, topBar = {
        TopAppBar(title = { Text("Post Details") }, navigationIcon = {
            IconButton(onClick = {
                onBackPressed()
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        })
    }, modifier = modifier
    ) {
        if (actionResult !is PostDetailsActionResult.NoActionResult) {
            val message = when (actionResult) {
                is PostDetailsActionResult.NetworkError -> actionResult.message
                is PostDetailsActionResult.ReplyDeleted -> stringResource(R.string.reply_delete_completed)
                is PostDetailsActionResult.ReplyUpdated -> stringResource(R.string.reply_update_completed)
                is PostDetailsActionResult.ReplyReported -> stringResource(R.string.reply_report_completed)
                is PostDetailsActionResult.PostDeleted -> stringResource(R.string.post_delete_completed)
                is PostDetailsActionResult.PostReported -> stringResource(R.string.post_report_completed)
                is PostDetailsActionResult.PostUpdated -> stringResource(R.string.post_update_completed)
                else -> stringResource(R.string.unknown_action_result)
            }
            LaunchedEffect(key1 = message){
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = message
                    )
                    delay(1500)
                    onResetActionResult()
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            LazyColumn(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxSize()
            ) {
                item {
                    FeedPostItem(
                        post = post, onPostEvent = onPostEvent
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }
                RepliesList(
                    replies = replies,
                    onReplyEvent = onReplyEvent,
                    currentUserId = currentUserID
                )
            }
            if (isReportDialogVisible) {
                AlertDialog(title = {
                    Text(text = stringResource(R.string.report_reply))
                }, text = {
                    Text(text = stringResource(R.string.confirm_report_reply))
                }, onDismissRequest = {
                    onDismissReportDialog()
                }, confirmButton = {
                    TextButton(onClick = {
                        onConfirmReport()
                    }) {
                        Text(text = stringResource(R.string.confirm))
                    }
                }, dismissButton = {
                    TextButton(onClick = {
                        onDismissReportDialog()
                    }) {
                        Text(text = stringResource(R.string.dismiss))
                    }
                })
            }
            if (bottomSheetState.isVisible) {
                AddReplySheet(
                    onDismiss = onDismissAddReplyBottomSheet,
                    initialValue = if (isEditingReply) clickedReply?.content.orEmpty() else "",
                    onDone = { textReply ->
                        if (isEditingReply && clickedReply != null) {
                            onReplyEvent(ReplyEvent.OnReplyEdited(clickedReply.copy(content = textReply)))
                            onReplyEvent(ReplyEvent.OnEditReply(clickedReply))
                        } else if (clickedReply == null) {
                            onPostEvent(PostEvent.OnCommentAdded(textReply, post.id))
                        } else {
                            onReplyEvent(ReplyEvent.OnReplyAdded(textReply, clickedReply))
                            onReplyEvent(ReplyEvent.OnAddReply(clickedReply))
                        }
                        onDismissAddReplyBottomSheet()
                    },
                    bottomSheetState = bottomSheetState
                )
            }
        }
    }
}
