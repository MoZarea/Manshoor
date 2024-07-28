package com.example.gemipost.data.auth.source.remote

import android.net.Uri
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.auth.source.remote.model.UserToken
import com.example.gemipost.utils.AuthResults
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthenticationRemoteDataSource {
    fun sendPasswordResetEmail(email: String): Flow<Result<AuthResults, AuthResults>>
    fun signInWithEmail(email: String, password: String): Flow<Result<User, AuthResults>>
    fun signUpWithEmail(name: String, avatarByteArray: Uri, email: String, password: String): Flow<Result<User, AuthResults>>
    suspend fun getSignedInUser(): Result<User, AuthResults>
    suspend fun logout(): Result<AuthResults, AuthResults>
    suspend fun deleteAccount(userId: String): Result<Unit, AuthResults>
    suspend fun registerUserToken(userToken: UserToken): Flow<Result<Unit, AuthResults>>
    suspend fun getUserToken(): Result<String, AuthResults>

}