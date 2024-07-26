package com.example.gemipost.app.di

import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.auth.repository.AuthenticationRepositoryImpl
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.repository.PostRepositoryImpl
import com.example.gemipost.data.post.repository.ReplyRepository
import com.example.gemipost.data.post.repository.ReplyRepositoryImpl
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



