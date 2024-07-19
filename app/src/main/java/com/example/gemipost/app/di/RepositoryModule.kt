package com.example.gemipost.app.di

import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.repository.PostRepositoryImpl
import com.example.gemipost.data.post.repository.ReplyRepository
import com.example.gemipost.data.post.repository.ReplyRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


val repositoryModuleK = DI.Module("repositoryModule") {

    bind<PostRepository>() with singleton {
        PostRepositoryImpl(
            instance(), instance()
        )
    }
    bind<ReplyRepository>() with singleton { ReplyRepositoryImpl(instance()) }

}



