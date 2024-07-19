package com.example.gemipost.app.di

import com.gp.socialapp.presentation.post.create.CreatePostScreenModel
import com.gp.socialapp.presentation.post.edit.EditPostScreenModel
import com.gp.socialapp.presentation.post.feed.FeedScreenModel
import com.gp.socialapp.presentation.post.postDetails.PostDetailsScreenModel
import com.gp.socialapp.presentation.post.search.SearchScreenModel
import com.gp.socialapp.presentation.post.searchResult.SearchResultScreenModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


val screenModelModuleK = DI.Module("screenModelModule") {
    bind<CreatePostScreenModel>() with singleton { CreatePostScreenModel(instance(), instance()) }
    bind<FeedScreenModel>() with singleton { FeedScreenModel(instance(), instance(), instance()) }
    bind<EditPostScreenModel>() with singleton { EditPostScreenModel(instance()) }
    bind<PostDetailsScreenModel>() with singleton {
        PostDetailsScreenModel(
            instance(),
            instance(),
            instance()
        )
    }
    bind<SearchResultScreenModel>() with singleton { SearchResultScreenModel(instance()) }
    bind<SearchScreenModel>() with singleton { SearchScreenModel(instance()) }

}

