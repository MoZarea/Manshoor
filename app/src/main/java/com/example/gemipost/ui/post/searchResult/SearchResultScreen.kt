package com.example.gemipost.ui.post.searchResult

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.ui.post.searchResult.components.SearchResultHeader
import com.example.gemipost.ui.post.searchResult.components.SearchResultList

@Composable
    fun SearchResultScreen(
    viewModel: SearchResultViewModel = viewModel(),
    searchTerm: String = "",
    searchTag: Tag = Tag(),
    isTag: Boolean = false,
    onPostClicked: (Post) -> Unit,
    onBackPressed: () -> Unit
    ) {
        val state by viewModel.uiState.collectAsState()
        val isScreenModelInitialized by remember { mutableStateOf(false) }
        if (!isScreenModelInitialized) {
            viewModel.init(searchTerm, searchTag, isTag)
        }
        SearchResultContent(
            posts = state.posts,
            onPostClicked = { onPostClicked(it) },
            onBackPressed = { onBackPressed() },
            onPostAuthorClicked = {},
            searchTerm = searchTerm,
            searchTag = searchTag,
            isTag = isTag
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
        searchTerm: String = "",
        searchTag: Tag = Tag(),
        isTag: Boolean = false,
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
