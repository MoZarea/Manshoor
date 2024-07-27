package com.example.gemipost.ui.post.postDetails

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gemipost.R
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.ui.post.feed.PostEvent
import com.example.gemipost.ui.post.feed.ReplyEvent
import com.example.gemipost.ui.post.feed.components.FeedPostItem
import com.example.gemipost.ui.post.postDetails.components.AddReplySheet
import com.example.gemipost.ui.post.postDetails.components.RepliesList
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.PostResults
import com.example.gemipost.utils.isNotIdle
import com.example.gemipost.utils.userMessage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailsScreen(
    postId: String,
    onBackPressed: () -> Unit,
    onTagClicked: (Tag) -> Unit,
    onSharePost: (String) -> Unit,
    viewModel: PostDetailsViewModel = koinViewModel(),
    navigateToEditPost: (String) -> Unit,
    navigateToLogin: () -> Unit
) {
    LaunchedEffect(true) {
        viewModel.initScreenModel(postId)
    }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    var clickedReply by remember { mutableStateOf<Reply?>(null) }
    var isReportDialogVisible by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    var isEditingReply by remember { mutableStateOf(false) }
    PostDetailsContent(
        state = state,
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
                is PostEvent.OnPostEdited -> {
                    navigateToEditPost(postEvent.post.id)
                    viewModel.resetState()
                }

                is PostEvent.OnPostShareClicked -> {
                    onSharePost(postEvent.post.id)
                }

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
        onBackPressed = { onBackPressed() },
        isEditingReply = isEditingReply,
        navigateToLogin = navigateToLogin
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostDetailsContent(
    state: PostDetailsUiState,
    modifier: Modifier = Modifier,
    onPostEvent: (PostEvent) -> Unit,
    onReplyEvent: (ReplyEvent) -> Unit,
    clickedReply: Reply?,
    isEditingReply: Boolean,
    bottomSheetState: SheetState,
    isReportDialogVisible: Boolean = false,
    onDismissReportDialog: () -> Unit,
    onConfirmReport: () -> Unit,
    onDismissAddReplyBottomSheet: () -> Unit,
    onBackPressed: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = state.actionResult, key2 = state.loginStatus) {
        if (state.actionResult.isNotIdle()) {
            if (state.actionResult == PostResults.POST_DELETED) {
                onBackPressed()
            }
            SnackbarHostState().showSnackbar(
                message = state.actionResult.userMessage(),
            )
        } else if (state.loginStatus == AuthResults.LOGIN_FAILED) {
            snackbarHostState.showSnackbar(
                message = AuthResults.LOGIN_FIRST_TO_ACCESS_ALL_FEATURES.userMessage(),
                actionLabel = "Back to Login",
            ).run {
                when (this) {
                    SnackbarResult.Dismissed -> {}
                    SnackbarResult.ActionPerformed -> navigateToLogin()
                }
            }
        }
    }
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
                    AnimatedVisibility(state.isLoading) {
                        LinearProgressIndicator()

                    }
                }
                item {
                    FeedPostItem(
                        post = state.post,
                        onPostEvent = onPostEvent,
                        currentUserId = state.currentUser.id
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }
                RepliesList(
                    replies = state.currentReplies,
                    onReplyEvent = onReplyEvent,
                    currentUserId = state.currentUser.id
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
                            onPostEvent(PostEvent.OnCommentAdded(textReply, state.post.id))
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
