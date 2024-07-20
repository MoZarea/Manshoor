package com.example.gemipost.data.auth.source.remote

import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.util.AuthError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthenticationRemoteDataSource {
    suspend fun deleteAccount(userId: String): Result<Unit,AuthError>
    fun sendPasswordResetEmail(email: String): Flow<Result<Unit,AuthError>>
//    fun signInWithOAuth(provider: OAuthProvider): Flow<Result<User,AuthError>>
    fun signInWithEmail(email: String, password: String): Flow<Result<User,AuthError>>
    fun signUpWithEmail(email: String, password: String): Flow<Result<User,AuthError>>
    suspend fun getSignedInUser(): Result<User,AuthError>
    suspend fun getUserSettings(): Result<UserSettings,AuthError>
    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit,AuthError>
    suspend fun changeEmail(email: String): Result<Unit,AuthError>
    suspend fun updateStringRemoteUserSetting(tag: String, value: String): Result<Unit,AuthError>
    suspend fun updateBooleanRemoteUserSetting(tag: String, value: Boolean): Result<Unit,AuthError>
    suspend fun logout(): Result<Unit,AuthError>
}