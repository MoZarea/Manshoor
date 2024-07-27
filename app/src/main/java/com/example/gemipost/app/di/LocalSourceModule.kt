package com.example.gemipost.app.di

import com.example.gemipost.app.MainApplication
import com.example.gemipost.data.auth.source.remote.AuthenticationRemoteDataSource
import com.example.gemipost.data.post.source.local.PostLocalDataSource
import com.example.gemipost.data.post.source.local.PostLocalDataSourceImpl
import com.example.gemipost.data.post.source.remote.ModerationRemoteDataSource
import com.example.gemipost.data.post.source.remote.PostRemoteDataSource
import com.example.gemipost.data.post.source.remote.PostRemoteDataSourceImpl
import com.example.gemipost.data.post.source.remote.ReplyRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore
import io.realm.kotlin.Realm
import org.koin.dsl.module

val localDataSourceModule = module {
    single<PostLocalDataSource> { PostLocalDataSourceImpl(get()) }
    single<Realm> { MainApplication.realm}
}
