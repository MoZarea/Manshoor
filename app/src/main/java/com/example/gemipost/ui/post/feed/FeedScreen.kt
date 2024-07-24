package com.example.gemipost.ui.post.feed

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.ui.post.feed.components.FeedPostItem
import com.example.gemipost.ui.post.feed.components.isUnsafe
import org.koin.androidx.compose.koinViewModel

@Composable
fun FeedScreen(
    viewModel: FeedScreenModel = koinViewModel(),
    navigateToEditPost: (String) -> Unit,
    navigateToPostDetails: (String) -> Unit,
    navigateToCreatePost: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    FeedContent(
        action = {action->
                    when(action){
                        is PostEvent.OnPostEdited->navigateToEditPost(action.post.id)
                        is PostEvent.OnPostClicked->navigateToPostDetails(action.post.id)
                        is PostEvent.OnCreatePostClicked->navigateToCreatePost()
                        else-> viewModel.handleEvent(action)
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
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {action(PostEvent.OnCreatePostClicked)})
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


