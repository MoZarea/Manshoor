package com.example.gemipost.ui.post.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection.Companion.Down
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.ui.post.feed.components.TagItem

@Composable
fun SearchResultHeader(
    modifier: Modifier = Modifier,
    searchTerm: String,
    searchTagIntColor: Int,
    isTag: Boolean,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    isSearching: Boolean,
    onSearch: (String) -> Unit,
    onChangeSearchingStatus: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (isTag) {
            Text(
                text = "Search results for: ",
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
            )
            val tag = Tag(label = searchTerm, intColor = searchTagIntColor)
            TagItem(
                tag = tag, onTagClicked = {}, modifier = Modifier.padding(start = 8.dp)
            )
        } else {
            SearchTextField(
                onSearchQueryChanged = onSearchQueryChanged,
                onSearch = onSearch,
                onChangeSearchingStatus = onChangeSearchingStatus,
                isSearching = isSearching,
                searchQuery = searchQuery
            )
        }
    }
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    searchQuery: String,
    isSearching: Boolean,
    onChangeSearchingStatus: (Boolean) -> Unit
) {
    val focusManager = LocalFocusManager.current
    if(!isSearching){
       focusManager.clearFocus(true)
    }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp).height(54.dp).onFocusChanged { state ->
            if (state.isFocused) {
                onChangeSearchingStatus(true)
            }
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        shape = MaterialTheme.shapes.small,
        value = searchQuery,
        singleLine = true,
        onValueChange = {
            onSearchQueryChanged(it)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (searchQuery.isNotBlank()) {
                IconButton(onClick = {
                    onSearchQueryChanged("")
                    onChangeSearchingStatus(true)
                }) {
                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = "Clear"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            onSearch(searchQuery)
            onChangeSearchingStatus(false)
        }),

        )
}