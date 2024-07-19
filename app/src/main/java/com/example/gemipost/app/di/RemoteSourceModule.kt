package com.example.gemipost.app.di

import com.example.gemipost.data.post.source.remote.PostRemoteDataSource
import com.example.gemipost.data.post.source.remote.PostRemoteDataSourceImpl
import com.example.gemipost.data.post.source.remote.ReplyRemoteDataSource
import com.example.gemipost.data.post.source.remote.ReplyRemoteDataSourceImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


val remoteDataSourceModuleK = DI.Module("remoteDataSourceModule") {
    bind<PostRemoteDataSource>() with singleton { PostRemoteDataSourceImpl(instance()) }
    bind<ReplyRemoteDataSource>() with singleton { ReplyRemoteDataSourceImpl(instance()) }
    bind<HttpClient>() with singleton {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    isLenient = true
                    encodeDefaults = true
                })
            }
        }
    }

}

