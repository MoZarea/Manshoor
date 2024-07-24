package com.example.gemipost.app.di

import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.example.gemipost.data.auth.source.remote.AuthenticationRemoteDataSource
import com.example.gemipost.data.auth.source.remote.AuthenticationRemoteDataSourceImpl
import com.example.gemipost.data.auth.source.remote.UserRemoteDataSource
import com.example.gemipost.data.auth.source.remote.UserRemoteDataSourceImpl
import com.example.gemipost.data.post.source.remote.PostRemoteDataSource
import com.example.gemipost.data.post.source.remote.PostRemoteDataSourceImpl
import com.example.gemipost.data.post.source.remote.ReplyRemoteDataSource
import com.example.gemipost.data.post.source.remote.ReplyRemoteDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import org.koin.dsl.module


val remoteDataSourceModuleK = module {
    single<PostRemoteDataSource>{ PostRemoteDataSourceImpl(get(),get()) }
    single<ReplyRemoteDataSource> { ReplyRemoteDataSourceImpl(get()) }
    single <AuthenticationRemoteDataSource>{AuthenticationRemoteDataSourceImpl(get())  }
    single <UserRemoteDataSource>{UserRemoteDataSourceImpl(get())  }
    single<FirebaseFirestore> {
        Firebase.firestore
    }
    single<FirebaseStorage> {
        Firebase.storage
    }
    single<FirebaseAuth> {
        Firebase.auth
    }

}

