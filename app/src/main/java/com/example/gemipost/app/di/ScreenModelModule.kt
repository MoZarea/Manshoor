package com.example.gemipost.app.di

import com.example.gemipost.ui.auth.login.LoginViewModel
import com.example.gemipost.ui.post.create.CreatePostViewModel
import com.example.gemipost.ui.post.edit.EditPostViewModel
import com.example.gemipost.ui.post.feed.FeedScreenModel
import com.example.gemipost.ui.post.postDetails.PostDetailsViewModel
import com.example.gemipost.ui.post.search.SearchViewModel
import com.example.gemipost.ui.post.searchResult.SearchResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val screenModelModuleK = module{
    viewModel { CreatePostViewModel(get(),get()) }
    viewModel{ FeedScreenModel(get(),get()) }
    viewModel { EditPostViewModel(get())}
    viewModel {
        PostDetailsViewModel(
            get(),
            get(),
            get()
        )
    }
    viewModel { SearchResultViewModel(get()) }
    viewModel{ SearchViewModel(get()) }
    viewModel{ LoginViewModel(get(), get()) }

}

