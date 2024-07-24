package com.example.gemipost.data.auth.source.remote

import android.net.Uri
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.util.AuthError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthenticationRemoteDataSource {
    fun sendPasswordResetEmail(email: String): Flow<Result<Unit,AuthError>>
    fun signInWithEmail(email: String, password: String): Flow<Result<User,AuthError>>
    fun signUpWithEmail(name: String, avatarByteArray: Uri, email: String, password: String): Flow<Result<User,AuthError>>
    suspend fun getSignedInUser(): Result<User,AuthError>
    suspend fun logout(): Result<Unit, AuthError>
    suspend fun deleteAccount(userId: String): Result<Unit, AuthError>

}