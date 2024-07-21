package com.gp.socialapp.presentation.post.feed

import EditPostScreen
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
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.NotificationImportant
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.community.source.remote.model.UserId
import com.gp.socialapp.data.community.source.remote.model.isAdmin
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.ui.auth.login.LoginScreen
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.presentation.post.create.CreatePostScreen
import com.gp.socialapp.presentation.post.feed.components.FeedPostItem
import com.gp.socialapp.presentation.post.feed.components.FilesBottomSheet
import com.gp.socialapp.presentation.post.feed.components.isUnsafe
import com.example.gemipost.ui.post.postDetails.PostDetailsScreen
import com.example.gemipost.ui.post.search.SearchScreen
import com.example.gemipost.ui.post.searchResult.SearchResultScreen
import com.gp.socialapp.presentation.userprofile.UserProfileScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.general
import socialmultiplatform.composeapp.generated.resources.spotlight

data class FeedScreen(val communityId: String) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<FeedScreenModel>()
        LaunchedEffect(true) { screenModel.initScreen(communityId) }

        var currentAttachments by remember { mutableStateOf(emptyList<PostAttachment>()) }
        val scope = rememberCoroutineScope()
        val state by screenModel.state.collectAsState()
        var isFileBottomSheetOpen by remember { mutableStateOf(false) }
        val bottomSheetState = rememberModalBottomSheetState()
        if (state.isLoggedOut) {
            navigator.replaceAll(LoginScreen)
            screenModel.resetState()
        }
        val tabItems = listOf(
            TabItem(stringResource(resource = Res.string.general), Icons.Filled.AllInclusive),
            TabItem(
                stringResource(resource = Res.string.spotlight), Icons.Filled.NotificationImportant
            ),
        )
        FeedContent(
            state = state,
            currentUserID = state.currentUserID,
            onPostEvent = { action ->
                when (action) {
                    is PostEvent.OnAddPost -> {
                        navigator.push(CreatePostScreen(state.openedTabItem, communityId))
                    }

                    is PostEvent.OnPostClicked -> {
                        navigator.push(PostDetailsScreen(action.post))
                    }

                    is PostEvent.OnPostEdited -> {
                        navigator.push(EditPostScreen(action.post))
                    }

                    is PostEvent.OnTagClicked -> {
                        navigator.push(SearchResultScreen(searchTag = action.tag, isTag = true))
                    }

                    is PostEvent.OnAttachmentClicked -> {
                        screenModel.openAttachment(action.attachment)
                    }

                    is PostEvent.OnPostUpVoted -> {
                        screenModel.upVote(action.post)
                    }

                    is PostEvent.OnPostDownVoted -> {
                        screenModel.downVote(action.post)
                    }

                    is PostEvent.OnPostDeleted -> {
                        screenModel.deletePost(action.post)
                    }

                    is PostEvent.OnViewFilesAttachmentClicked -> {
                        currentAttachments = action.files
                        scope.launch {
                            isFileBottomSheetOpen = true
                        }
                    }

                    is PostEvent.OnPostReported -> {
                        screenModel.reportPost(action.post)
                    }

                    is PostEvent.OnPostShareClicked -> {
                        screenModel.logout()
                    }

                    is PostEvent.OnPostAuthorClicked -> {
                        navigator.push(UserProfileScreen(action.userId))
                    }
                    is PostEvent.OnCommentClicked -> {
                        navigator.push(PostDetailsScreen(action.post))
                    }
                    else -> {}
                }
            },
            onNavigationAction = { action ->
                when (action) {
                    is NavigationAction.NavigateToSearch -> {
                        navigator.push(SearchScreen)
                    }

                    is NavigationAction.NavigateToPostDetails -> {
                        navigator.push(PostDetailsScreen(action.post))
                    }

                    else -> {}
                }
            },
            scope = scope,
            currentAttachments = currentAttachments,
            isFileBottomSheetOpen = isFileBottomSheetOpen,
            tabItems = tabItems,
            onDismissBottomSheet = {
                isFileBottomSheetOpen = false
            },
            onShowBottomSheet = {
                isFileBottomSheetOpen = true
            },
            bottomSheetState = bottomSheetState,
            onResetError = screenModel::resetError,
            onChangeOpenedTab = screenModel::changeOpenedTab
        )
    }

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
                if (currentUserIsAdmin(
                        state.currentUser.id, state.currentCommunity.members
                    ) || selectedTabIndex != 1
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
//                    .padding(paddingValues)
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

    private fun currentUserIsAdmin(id: String, members: Map<UserId, isAdmin>): Boolean {
        return members[id] == true

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
}

data class TabItem(val title: String, val imageVector: ImageVector)
