package com.example.gemipost.data.auth.repository

import com.example.gemipost.data.auth.source.local.AuthKeyValueStorage
import com.example.gemipost.data.auth.source.remote.AuthenticationRemoteDataSource
import com.example.gemipost.data.auth.source.remote.model.User
import com.gp.socialapp.util.AuthError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    private val localKeyValueStorage: AuthKeyValueStorage
) : AuthenticationRepository {
    override fun sendPasswordResetEmail(email: String) =
        remoteDataSource.sendPasswordResetEmail(email)
    override fun clearStorage() {
        localKeyValueStorage.cleanStorage()
    }
//    override fun signInWithOAuth(provider: OAuthProvider): Flow<Result<User,AuthError>> =
//        remoteDataSource.signInWithOAuth(provider)
    override fun signInWithEmail(email: String, password: String): Flow<Result<User,AuthError>> =
        remoteDataSource.signInWithEmail(email, password)
    override fun signUpWithEmail(email: String, password: String): Flow<Result<User,AuthError>> =
        remoteDataSource.signUpWithEmail(email, password)
    override suspend fun getSignedInUser(): Result<User,AuthError> =
        remoteDataSource.getSignedInUser()

    override suspend fun logout(): Result<Unit, AuthError> {
        clearStorage()
        return remoteDataSource.logout()
    }

    override suspend fun deleteAccount(userId: String): Result<Unit, AuthError> {
        return remoteDataSource.deleteAccount(userId).also{
            if (it is Result.Success) {
                logout()
            }
        }
    }
}
