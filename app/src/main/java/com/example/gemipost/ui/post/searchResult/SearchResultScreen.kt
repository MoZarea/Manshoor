package com.gp.socialapp.presentation.post.searchResult

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Tag
import com.gp.socialapp.presentation.post.postDetails.PostDetailsScreen
import com.gp.socialapp.presentation.post.searchResult.components.SearchResultHeader
import com.gp.socialapp.presentation.post.searchResult.components.SearchResultList
import com.gp.socialapp.presentation.userprofile.UserProfileScreen

data class SearchResultScreen(
    val searchTerm: String = "",
    val searchTag: Tag = Tag(),
    val isTag: Boolean = false
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<SearchResultScreenModel>()
        val state by screenModel.uiState.collectAsState()
        val isScreenModelInitialized by remember { mutableStateOf(false) }
        if (!isScreenModelInitialized) {
            screenModel.initScreenModel(searchTerm, searchTag, isTag)
        }
        SearchResultContent(
            posts = state.posts,
            onPostClicked = { navigator.push(PostDetailsScreen(it)) },
            onBackPressed = { navigator.pop() },
            onPostAuthorClicked = { navigator.push(UserProfileScreen(it)) }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchResultContent(
        modifier: Modifier = Modifier,
        posts: List<Post>,
        onPostClicked: (Post) -> Unit,
        onBackPressed: () -> Unit,
        onPostAuthorClicked: (String) -> Unit,
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            SearchResultHeader(
                                searchTerm = searchTerm,
                                searchTag = searchTag,
                                isTag = isTag
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )

            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(it)
            ) {
                SearchResultList(
                    posts = posts,
                    onPostClicked = onPostClicked,
                    onPostAuthorClicked = onPostAuthorClicked
                )
            }
        }
    }
}
