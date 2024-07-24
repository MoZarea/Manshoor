package com.example.gemipost.app.di

import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.example.gemipost.ui.auth.forgotpassword.ForgotPasswordViewModel
import com.example.gemipost.ui.auth.login.LoginViewModel
import com.example.gemipost.ui.auth.signup.SignUpViewModel
import com.example.gemipost.ui.post.create.CreatePostViewModel
import com.example.gemipost.ui.post.edit.EditPostScreenModel
import com.example.gemipost.ui.post.feed.FeedScreenModel
import com.example.gemipost.ui.post.postDetails.PostDetailsViewModel
import com.example.gemipost.ui.post.search.SearchViewModel
import com.example.gemipost.ui.post.searchResult.SearchResultViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import org.koin.androidx.compose.get
import org.koin.androidx.compose.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val screenModelModuleK = module{
    viewModel { CreatePostViewModel(get(),get()) }
    viewModel{ FeedScreenModel(get(),get()) }
    viewModel { EditPostScreenModel(get())}
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
    viewModel{ SignUpViewModel(get()) }
    viewModel{ ForgotPasswordViewModel(get()) }

}

