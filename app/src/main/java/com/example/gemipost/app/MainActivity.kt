package com.example.gemipost.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.gemipost.R
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.navigation.CreatePost
import com.example.gemipost.navigation.EditPost
import com.example.gemipost.navigation.Feed
import com.example.gemipost.navigation.ForgotPassword
import com.example.gemipost.navigation.Login
import com.example.gemipost.navigation.PostDetails
import com.example.gemipost.navigation.Search
import com.example.gemipost.navigation.SearchResult
import com.example.gemipost.navigation.SignUp
import com.example.gemipost.navigation.parcelableType
import com.example.gemipost.ui.auth.login.LoginScreen
import com.example.gemipost.ui.post.create.CreatePostContent
import com.example.gemipost.ui.post.create.CreatePostUIState
import com.example.gemipost.ui.post.postDetails.PostDetailsScreen
import com.example.gemipost.ui.post.search.SearchScreen
import com.example.gemipost.ui.post.searchResult.SearchResultScreen
import com.example.gemipost.ui.theme.GemiPostTheme
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(R.color.transparent))
        super.onCreate(savedInstanceState)
        setContent {
            GemiPostTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = CreatePost) {
        composable<Login> {
            LoginScreen(onNavigateToFeed = {
                navController.navigate(Feed)
            }, onNavigateToSignUp = {
                navController.navigate(SignUp)
            }, onNavigateToForgotPassword = {
                navController.navigate(ForgotPassword)
            })
        }
        composable<SignUp> {
            //TODO
        }
        composable<ForgotPassword> {
            //TODO
        }
        composable<Feed> {
            //TODO
        }
        composable<CreatePost> {
            CreatePostContent(
                state = CreatePostUIState(),
                channelTags = emptyList(),
                onBackClick = {},
                onPostClick = { _, _, _ -> },
                onAddTag = {},
                confirmNewTags = {},
                onAddFile = {},
                onRemoveFile = {}
            )
        }
//        composable<EditPost> {
//            //TODO
//        }
//        composable<PostDetails> { backStackEntry ->
//            val post: Post = backStackEntry.toRoute<PostDetails>().post
//            PostDetailsScreen(post = post, onBackPressed = {
//                navController.popBackStack()
//            }, onTagClicked = {
//                navController.navigate(SearchResult(query = "", isTag = true, tag = it))
//            })
//        }
//        composable<Search> {
//            SearchScreen(onNavigateToSearchResult = {
//                navController.navigate(SearchResult(query = it, isTag = false, tag = Tag()))
//            }, onBackPressed = {
//                navController.popBackStack()
//            })
//        }
        composable<SearchResult> (
                typeMap = mapOf(typeOf<Tag>() to parcelableType<Tag>())

        ){ backStackEntry ->
            val searchResult = backStackEntry.toRoute<SearchResult>()
            SearchResultScreen(
                searchTerm = searchResult.query,
                searchTag = searchResult.tag,
                isTag = searchResult.isTag,
                onPostClicked = {
                    navController.navigate(PostDetails(it))
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}