package com.example.gemipost.app

import EditPostScreen
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDeepLink
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.gemipost.R
import com.example.gemipost.navigation.CreatePost
import com.example.gemipost.navigation.EditPost
import com.example.gemipost.navigation.Feed
import com.example.gemipost.navigation.ForgotPassword
import com.example.gemipost.navigation.Login
import com.example.gemipost.navigation.PostDetails
import com.example.gemipost.navigation.Search
import com.example.gemipost.navigation.SearchResult
import com.example.gemipost.navigation.SignUp
import com.example.gemipost.navigation.Splash
import com.example.gemipost.navigation.TestDest
import com.example.gemipost.ui.auth.forgotpassword.ForgotPasswordScreen
import com.example.gemipost.ui.auth.login.LoginScreen
import com.example.gemipost.ui.auth.signup.SignUpScreen
import com.example.gemipost.ui.post.create.CreatePostScreen
import com.example.gemipost.ui.post.feed.FeedScreen
import com.example.gemipost.ui.post.postDetails.PostDetailsScreen
import com.example.gemipost.ui.post.search.SearchScreen
import com.example.gemipost.ui.post.searchResult.SearchResultScreen
import com.example.gemipost.ui.splash.SplashScreen
import com.example.gemipost.ui.theme.GemiPostTheme
import com.example.gemipost.utils.AppConstants
import com.example.gemipost.utils.AppConstants.APP_URI

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

    @SuppressLint("RestrictedApi")
    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        NavHost(navController, startDestination = Splash) {
            composable<Splash> {
                Log.d("seerde", "Splash screen")
                SplashScreen(
                    onNavigateToFeed = {
                        navController.navigate(Feed) {
                            popUpTo(Splash) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate(Login) {
                            popUpTo(Splash) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<Login> {
                Log.d("seerde", "login screen")
                LoginScreen(
                    onNavigateToFeed = {
                        navController.navigate(Feed) {
                            popUpTo(Login) {
                                inclusive = true
                            }
                        }
                    }, onNavigateToSignUp = {
                        navController.navigate(SignUp)
                    }, onNavigateToForgotPassword = {
                        navController.navigate(ForgotPassword)
                    })
            }
            composable<SignUp> {
                Log.d("seerde", "sign up screen")
                SignUpScreen(
                    onNavigateToFeed = {
                        navController.navigate(Feed) {
                            popUpTo(SignUp) {
                                inclusive = true
                            }
                        }
                    },
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }
            composable<ForgotPassword> {
                Log.d("seerde", "forgot password screen")
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
                Log.d("seerde", "feed screen")
                FeedScreen(
                    navigateToCreatePost = {
                        navController.navigate(CreatePost)
                    },
                    navigateToEditPost = {
                        navController.navigate(EditPost(it))
                    },
                    navigateToPostDetails = {
                        navController.navigate(PostDetails(it))
                    },
                    navigateToSearch = {
                        navController.navigate(Search)
                    },
                    navigateToLogin = {
                        navController.navigate(Login) {
                            popUpTo(Feed) {
                                inclusive = true
                            }
                        }
                    },
                    onSharePost = { postId ->
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "$APP_URI/p/$postId")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                    }
                )
            }
            composable<CreatePost> {
                Log.d("seerde", "create post screen")
                CreatePostScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable<EditPost> {
                Log.d("seerde", "edit post screen")
                val postId = it.toRoute<EditPost>().postId
                EditPostScreen(
                    postId = postId,
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable<PostDetails> (
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern = "${AppConstants.APP_URI}/p/{postId}"
                        action = Intent.ACTION_VIEW
                    }
                )
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId")?:backStackEntry.toRoute<PostDetails>().postId
                Log.d("seerde", "post details screen: $postId")
                PostDetailsScreen(
                    postId = postId,
                    onBackPressed = {
                        navController.navigateUp()
                    }, onTagClicked = { tag ->
                        navController.navigate(
                            SearchResult(
                                label = tag.label,
                                isTag = true,
                                tagIntColor = tag.intColor
                            )
                        )
                    },
                    navigateToEditPost = {
                        navController.navigate(EditPost(it))
                    },
                    onSharePost = { sharedPostId ->
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "$APP_URI/p/$sharedPostId")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                    }
                )
            }
            composable<Search> {
                Log.d("seerde", "search screen")
                SearchScreen(onNavigateToSearchResult = {
                    navController.navigate(SearchResult(label = it, isTag = false))
                }, onBackPressed = {
                    navController.popBackStack()
                })
            }
            composable<SearchResult> { backStackEntry ->
                Log.d("seerde", "Search Result screen")
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
}
