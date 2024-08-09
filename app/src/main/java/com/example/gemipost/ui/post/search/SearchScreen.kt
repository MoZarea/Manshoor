package com.example.gemipost.ui.post.search

import androidx.annotation.ColorInt
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.ui.post.search.components.SearchResultHeader
import com.example.gemipost.ui.post.search.components.SearchResultList
import com.example.gemipost.ui.post.search.components.SearchSuggestions
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    searchLabel: String,
    @ColorInt searchTagIntColor: Int = Color.Transparent.toArgb(),
    isTag: Boolean = false,
    onPostClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.init(isTag, searchLabel)
    }
    val state by viewModel.uiState.collectAsState()
    SearchResultContent(
        posts = state.searchResult,
        onPostClicked = { onPostClicked(it) },
        onBackPressed = { onBackPressed() },
        onPostAuthorClicked = {},
        searchTerm = searchLabel,
        searchTagIntColor = searchTagIntColor,
        isTag = isTag,
        recentSearches = state.recentSearches,
        onSearch = {
            viewModel.addRecentSearchItem(it)
            viewModel.onSearch(it)
        },
        onDeleteRecentItem = viewModel::deleteRecentSearchItem,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        suggestionItems = state.suggestionItems,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultContent(
    modifier: Modifier = Modifier,
    posts: List<Post>,
    onPostClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    onPostAuthorClicked: (String) -> Unit,
    searchTerm: String = "",
    searchTagIntColor: Int,
    isTag: Boolean = false,
    recentSearches: List<String>,
    onSearch: (String) -> Unit,
    onDeleteRecentItem: (String) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    suggestionItems: List<String>,
) {
    var isSearching by remember {
        mutableStateOf(!isTag)
    }
    var searchQuery by remember {
        mutableStateOf("")
    }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection).fillMaxSize(), topBar = {
        Surface(
            shadowElevation = 8.dp
        ) {
            TopAppBar(
                scrollBehavior = scrollBehavior, title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SearchResultHeader(
                        searchTerm = searchTerm,
                        searchTagIntColor = searchTagIntColor,
                        isTag = isTag,
                        onSearch = { query ->
                            onSearch(query)
                        },
                        onSearchQueryChanged = {
                            searchQuery = it
                            onSearchQueryChanged(it)
                        },
                        isSearching = isSearching,
                        onChangeSearchingStatus = { isSearching = it },
                        searchQuery = searchQuery
                    )
                }
            }, navigationIcon = {
                IconButton(onClick = {
                    onBackPressed()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            })
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            AnimatedContent(targetState = isSearching, label = "Search Animated Content") { state ->
                when {
                    state -> {
                        SearchSuggestions(
                            recentSearches = recentSearches,
                            onDeleteRecentItem = onDeleteRecentItem,
                            onSearch = { query ->
                                searchQuery = query
                                onSearch(query)
                                isSearching = false
                            },
                            suggestionItems = suggestionItems,
                            searchQuery = searchQuery
                        )
                    }

                    else -> {
                        SearchResultList(
                            posts = posts,
                            onPostClicked = onPostClicked,
                            onPostAuthorClicked = onPostAuthorClicked
                        )
                    }
                }
            }
        }
    }
}

