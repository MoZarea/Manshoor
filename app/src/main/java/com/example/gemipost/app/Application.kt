package com.example.gemipost.app

import android.app.Application
import com.example.gemipost.app.di.appModule
import com.example.gemipost.data.post.source.local.PostEntity
import com.example.gemipost.data.post.source.local.RecentSearchEntity
import com.example.gemipost.data.post.source.local.TagEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    companion object {
        lateinit var realm: Realm
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    PostEntity::class,
                    TagEntity::class,
                    RecentSearchEntity::class,
                )
            )
        )

    }
}