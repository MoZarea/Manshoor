package com.example.gemipost.app.di

import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.auth.repository.AuthenticationRepositoryImpl
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.repository.PostRepositoryImpl
import com.example.gemipost.data.post.repository.ReplyRepository
import com.example.gemipost.data.post.repository.ReplyRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import org.koin.dsl.module


val repositoryModuleK = module {

    single <PostRepository> {
        PostRepositoryImpl(
            get()
        )
    }
    single<ReplyRepository> { ReplyRepositoryImpl(get()) }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get()) }

}



