package com.example.gemipost.ui.post.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.gemipost.R
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.ui.post.feed.components.FeedPostItem
import com.example.gemipost.ui.post.feed.components.isUnsafe
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.isNotIdle
import com.example.gemipost.utils.userMessage
import org.koin.androidx.compose.koinViewModel

@Composable
fun FeedScreen(
    viewModel: FeedScreenModel = koinViewModel(),
    onSharePost: (String) -> Unit,
    navigateToEditPost: (String) -> Unit,
    navigateToPostDetails: (String) -> Unit,
    navigateToCreatePost: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToSearchResult: (Tag) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    FeedContent(
        action = { action ->
            when (action) {
                is PostEvent.OnPostEdited -> navigateToEditPost(action.post.id)
                is PostEvent.OnPostClicked -> navigateToPostDetails(action.post.id)
                is PostEvent.OnCreatePostClicked -> navigateToCreatePost()
                is PostEvent.OnSearchClicked -> navigateToSearch()
                is PostEvent.OnPostShareClicked -> {
                    onSharePost(action.post.id)
                }

                is PostEvent.OnTagClicked -> navigateToSearchResult(action.tag)
                else -> Unit
            }
            viewModel.handleEvent(action)
        },
        state = state,
        navigateToLogin = navigateToLogin
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedContent(
    action: (PostEvent) -> Unit,
    state: FeedUiState,
    navigateToLogin: () -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val snackbarHostState = remember { SnackbarHostState() }
    var menuVisibility by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = state.actionResult, key2 = state.loginStatus) {
        println("FeedContent: ${state.actionResult.userMessage()}")
        if (state.actionResult.isNotIdle()) {
            if (state.actionResult == AuthResults.LOGOUT_SUCCESS) {
                navigateToLogin()
            } else {
                snackbarHostState.showSnackbar(
                    state.actionResult.userMessage(),
                    withDismissAction = true
                )
            }
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
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        stringResource(id = R.string.app_name),
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {

                    IconButton(onClick = { menuVisibility = !menuVisibility }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                    DropdownMenu(
                        expanded = menuVisibility,
                        onDismissRequest = { menuVisibility = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = { action(PostEvent.OnLogoutClicked) }
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { action(PostEvent.OnSearchClicked) }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Localized description"
                        )
                    }
                    if (state.loginStatus != AuthResults.LOGIN_FAILED)
                        AsyncImage(
                            model = state.user.profilePictureURL,
                            modifier = Modifier
                                .clip(
                                    CircleShape
                                )
                                .size(35.dp),
                            contentDescription = "userImage"

                        )

                },
                scrollBehavior = scrollBehavior
            )
        },

        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            if (state.loginStatus != AuthResults.LOGIN_FAILED) {
                FloatingActionButton(onClick = { action(PostEvent.OnCreatePostClicked) })
                {
                    Icon(
                        imageVector = Icons.Filled.Add, contentDescription = null
                    )
                }
            }

        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            FeedPosts(
                posts = state.posts,
                onPostEvent = action,
                currentUserId = state.user.id
            )
        }
    }
}


@Composable
fun FeedPosts(
    posts: List<Post>,
    onPostEvent: (PostEvent) -> Unit,
    currentUserId: String
) {
    println("FeedPosts: ${posts.size}")
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(posts) { post ->
                println("0000FeedPosts: ${post.upvoted}")
                println("0000FeedPosts: ${post.downvoted}")
                if (!post.moderationStatus.isUnsafe()) {
                    FeedPostItem(
                        post = post, onPostEvent = onPostEvent, currentUserId = currentUserId
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                )
            }
        }
    }
}


