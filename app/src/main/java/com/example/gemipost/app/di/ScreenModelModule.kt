package com.example.gemipost.app.di

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


val screenModelModuleK = DI.Module("screenModelModule") {
    bind<CreatePostViewModel>() with singleton { CreatePostViewModel(instance(), instance()) }
    bind<FeedScreenModel>() with singleton { FeedScreenModel(instance(), instance()) }
    bind<EditPostScreenModel>() with singleton { EditPostScreenModel(instance()) }
    bind<PostDetailsViewModel>() with singleton {
        PostDetailsViewModel(
            instance(),
            instance(),
            instance()
        )
    }
    bind<SearchResultViewModel>() with singleton { SearchResultViewModel(instance()) }
    bind<SearchViewModel>() with singleton { SearchViewModel(instance()) }

}

