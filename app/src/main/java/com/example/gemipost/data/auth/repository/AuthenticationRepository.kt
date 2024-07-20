package com.example.gemipost.data.auth.repository

import com.example.gemipost.data.auth.source.remote.model.User
import com.gp.socialapp.util.AuthError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun sendPasswordResetEmail(email: String): Flow<Result<Unit,AuthError>>
    fun clearStorage()
//    fun signInWithOAuth(provider: OAuthProvider): Flow<Result<User,AuthError>>
    fun signInWithEmail(email: String, password: String): Flow<Result<User,AuthError>>
    fun signUpWithEmail(email: String, password: String): Flow<Result<User,AuthError>>
    suspend fun getSignedInUser(): Result<User,AuthError>
    suspend fun logout(): Result<Unit, AuthError>
    suspend fun deleteAccount(userId: String): Result<Unit, AuthError>

}
