package com.example.gemipost.app

import EditPostScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.gemipost.R
import com.example.gemipost.data.post.source.remote.model.Post
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
import com.example.gemipost.ui.auth.forgotpassword.ForgotPasswordScreen
import com.example.gemipost.ui.auth.login.LoginScreen
import com.example.gemipost.ui.auth.signup.SignUpScreen
import com.example.gemipost.ui.post.create.CreatePostScreen
import com.example.gemipost.ui.post.feed.FeedScreen
import com.example.gemipost.ui.post.postDetails.PostDetailsScreen
import com.example.gemipost.ui.post.search.SearchScreen
import com.example.gemipost.ui.post.searchResult.SearchResultScreen
import com.example.gemipost.ui.theme.GemiPostTheme
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
    NavHost(navController, startDestination = Login) {
        composable<Login> {
            LoginScreen(
                onNavigateToFeed = {
                navController.navigate(Feed)
            }, onNavigateToSignUp = {
                navController.navigate(SignUp)
            }, onNavigateToForgotPassword = {
                navController.navigate(ForgotPassword)
            })
        }
        composable<SignUp> {
            SignUpScreen(
                onNavigateToFeed = {
                    navController.navigate(Feed)
                    navController.clearBackStack<Feed>()
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
        composable<ForgotPassword> {
            ForgotPasswordScreen(
                onNavigateToLogin = {
                    navController.navigate(Login)
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
        composable<Feed> {
            println("$$$$$$$$$$$$$$$$$" + navController.currentBackStackEntry?.destination?.route)
            FeedScreen(
                navigateToCreatePost = {
                    navController.navigate(CreatePost)
                },
                navigateToEditPost = {
                    navController.navigate(EditPost(it))
                },
                navigateToPostDetails = {
                    navController.navigate(PostDetails(it))
                }
            )
        }
        composable<CreatePost> {
            println("$$$$$$$$$$$$$$$$$" + navController.currentBackStackEntry?.destination?.route)
            CreatePostScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<EditPost> {
            val postId = it.toRoute<EditPost>().postId
            EditPostScreen(
                postId = postId,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<PostDetails>{ backStackEntry ->
            val postId = backStackEntry.toRoute<PostDetails>().postId
            PostDetailsScreen(
                postId = postId,
                onBackPressed = {
                navController.navigateUp()
            }, onTagClicked = {tag->
                navController.navigate(
                    SearchResult(
                        label = tag.label,
                        isTag = true,
                        tagIntColor = tag.intColor
                    )
                )
            })
        }
        composable<Search> {
            SearchScreen(onNavigateToSearchResult = {
                navController.navigate(SearchResult(label = it, isTag = false))
            }, onBackPressed = {
                navController.popBackStack()
            })
        }
        composable<SearchResult> { backStackEntry ->
            val searchResult = backStackEntry.toRoute<SearchResult>()
            SearchResultScreen(
                searchLabel = searchResult.label,
                searchTagIntColor = searchResult.tagIntColor,
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