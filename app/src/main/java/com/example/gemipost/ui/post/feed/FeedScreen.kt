package com.example.gemipost.ui.post.feed

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.ui.post.feed.components.FeedPostItem
import com.example.gemipost.ui.post.feed.components.FilesBottomSheet
import com.example.gemipost.ui.post.feed.components.isUnsafe
import com.example.gemipost.utils.MimeType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    private fun FeedContent(
    modifier: Modifier = Modifier,
    onPostEvent: (PostEvent) -> Unit,
    onNavigationAction: (NavigationAction) -> Unit,
    currentUserID: String,
    state: FeedUiState,
    scope: CoroutineScope = rememberCoroutineScope(),
    currentAttachments: List<PostAttachment>,
    isFileBottomSheetOpen: Boolean,
    tabItems: List<TabItem>,
    onChangeOpenedTab: (Int) -> Unit = { },
    onDismissBottomSheet: () -> Unit = { },
    onResetError: () -> Unit,
    onShowBottomSheet: () -> Unit = { },
    bottomSheetState: SheetState,
    ) {
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val pagerState = rememberPagerState { tabItems.size }
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            floatingActionButton = {
                if (true
                ) {
                    FloatingActionButton(onClick = { onPostEvent(PostEvent.OnAddPost) }) {
                        Icon(
                            imageVector = Icons.Filled.Add, contentDescription = null
                        )
                    }
                }
            },
        ) { paddingValues ->
            if (state.error !is FeedError.NoError) {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = (state.error as FeedError.NetworkError).message,
                    )
                    delay(1500)
                    onResetError()
                }
            }
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                TabRow(
                    modifier = Modifier.height(40.dp).fillMaxWidth().clip(
                        RoundedCornerShape(
                            bottomStart = 8.dp, bottomEnd = 8.dp
                        )
                    ),
                    selectedTabIndex = selectedTabIndex,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .padding(horizontal = 50.dp).clip(CircleShape)
                                .align(Alignment.CenterHorizontally),
                            height = 8.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ) {
                    tabItems.forEachIndexed { index, tabItem ->
                        Tab(
                            selected = (index == selectedTabIndex),
                            onClick = {
                                selectedTabIndex = index
                                onChangeOpenedTab(index)
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = tabItem.title,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 16.sp
                                )
                            },
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState, modifier = Modifier.fillMaxWidth()
                ) { index ->
                    when (index) {
                        0 -> {
                            FeedPosts(
                                posts = state.posts.filter { it.type == FeedTab.GENERAL.title },
                                onPostEvent = onPostEvent,
                                currentUserID = currentUserID
                            )
                        }

                        1 -> {
                            FeedPosts(
                                posts = state.posts.filter { it.type == FeedTab.SPOTLIGHT.title },
                                onPostEvent = onPostEvent,
                                currentUserID = currentUserID
                            )
                        }
                    }
                }
                if (isFileBottomSheetOpen) {
                    FilesBottomSheet(
                        attachments = currentAttachments.filter {
                            MimeType.getMimeTypeFromFileName(
                                it.name
                            ) !is MimeType.Image
                        },
                        onDismiss = onDismissBottomSheet,
                        onPostEvent = onPostEvent,
                        state = bottomSheetState,
                    )
                }
            }
            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                if (!pagerState.isScrollInProgress) {
                    selectedTabIndex = pagerState.currentPage
                }
            }
        }
    }



    @Composable
    fun FeedPosts(
        posts: List<Post>, onPostEvent: (PostEvent) -> Unit, currentUserID: String
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                items(posts) { post ->
                    if(!post.moderationStatus.isUnsafe()){
                        FeedPostItem(
                            post = post, onPostEvent = onPostEvent, currentUserID = currentUserID
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                    }
                }
            }
        }
    }


data class TabItem(val title: String, val imageVector: ImageVector)
