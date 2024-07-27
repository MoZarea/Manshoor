package com.example.gemipost.data.post.source.local

import android.util.Log
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.utils.PostResults
import com.gp.socialapp.util.Result
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PostLocalDataSourceImpl(val realm: Realm) : PostLocalDataSource {

    @OptIn(DelicateCoroutinesApi::class)
    override fun updateDataBase(posts: List<Post>): Result<Unit, PostResults> {
        return try {
            println("updateDataBase out: ${posts.size}")
            GlobalScope.launch(Dispatchers.Default) {
                realm.write {
                    for (post in posts) {
                        Log.d("seerde", "updateDataBase: ${post.title}")
                        copyToRealm(
                            post.toPostEntity(),
                            updatePolicy = io.realm.kotlin.UpdatePolicy.ALL
                        )
                    }
                    realm.query<PostEntity>().find().forEach {
                        if (posts.find { post -> post.id == it.id } == null) {
                            findLatest(it)?.also { delete(it) }
                        }
                    }
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(PostResults.NETWORK_ERROR)
        }
    }

    override fun searchByTitle(title: String): Flow<List<Post>> {
        println("searchByTitle: $title")
        try {
            val posts = realm.query<PostEntity>("title CONTAINS[c] $0", title).asFlow()
                .map { it.list.toList().map { entity -> entity.toPost() } }
            return posts
        } catch (e: Exception) {
            e.printStackTrace()
            return flow { emit(emptyList<Post>()) }
        }
    }

    override fun searchByTag(tag: String): Flow<List<Post>> = try {
        println("searchByTag: $tag")
        val posts = realm.query<PostEntity>("tags.label CONTAINS[c] $0", tag).asFlow()
            .map { it.list.toList().map { entity -> entity.toPost() } }
        posts
    } catch (e: Exception) {
        e.printStackTrace()
        flow { emit(emptyList()) }
    }


    override suspend fun addRecentSearch(search: String) {
        realm.write {
            copyToRealm(RecentSearchEntity().apply {
                this.recentSearch = search
            })
        }
    }

    override suspend fun getRecentSearches(): List<String> {
        val searches = realm.query<RecentSearchEntity>().find()
        return searches.map { it.recentSearch }
    }

    override suspend fun deleteRecentSearch(search: String) {
        val searchEntity =
            realm.query<RecentSearchEntity>("recentSearch == $0", search).find().firstOrNull()
        realm.write {
            if (searchEntity != null) {
                findLatest(searchEntity)?.also { delete(it) }
            }
        }
    }
}