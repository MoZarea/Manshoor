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
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.ui.post.feed.components.FeedPostItem
import com.example.gemipost.ui.post.feed.components.isUnsafe
import com.gp.socialapp.util.PostError
import org.koin.androidx.compose.koinViewModel

@Composable
fun FeedScreen(
    viewModel: FeedScreenModel = koinViewModel(),
    navigateToEditPost: (String) -> Unit,
    navigateToPostDetails: (String) -> Unit,
    navigateToCreatePost: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = state.isLoggedIn) {
        if (!state.isLoggedIn) {
            navigateToLogin()
        }
    }
    FeedContent(
        action = { action ->
            when (action) {
                is PostEvent.OnPostEdited -> navigateToEditPost(action.post.id)
                is PostEvent.OnPostClicked -> navigateToPostDetails(action.post.id)
                is PostEvent.OnCreatePostClicked -> navigateToCreatePost()
                is PostEvent.OnSearchClicked -> navigateToSearch()
                else -> viewModel.handleEvent(action)
            }
        },
        state = state,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedContent(
    action: (PostEvent) -> Unit,
    state: FeedUiState,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val snackbarHostState = remember { SnackbarHostState() }
    var menuVisibility by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = state.error) {
        if (state.error != PostError.NO_ERROR) {
            snackbarHostState.showSnackbar(state.error.userMessage, withDismissAction = true)
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
                        "GemiPost",
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { action(PostEvent.OnSearchClicked) }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
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
                scrollBehavior = scrollBehavior
            )
        },

        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { action(PostEvent.OnCreatePostClicked) })
            {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = null
                )
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
            )
        }
    }
}


@Composable
fun FeedPosts(
    posts: List<Post>,
    onPostEvent: (PostEvent) -> Unit,
) {
    println("FeedPosts: ${posts.size}")
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(posts) { post ->
                if (!post.moderationStatus.isUnsafe()) {
                    FeedPostItem(
                        post = post, onPostEvent = onPostEvent
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


