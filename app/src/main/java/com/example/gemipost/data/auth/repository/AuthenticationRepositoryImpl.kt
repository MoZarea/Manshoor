package com.example.gemipost.data.auth.repository

import android.net.Uri
import com.example.gemipost.data.auth.source.remote.AuthenticationRemoteDataSource
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.utils.AuthResults
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
) : AuthenticationRepository {
    override fun sendPasswordResetEmail(email: String) =
        remoteDataSource.sendPasswordResetEmail(email)

    override fun signInWithEmail(email: String, password: String): Flow<Result<User, AuthResults>> =
        remoteDataSource.signInWithEmail(email, password)
    override fun signUpWithEmail(name: String, avatarByteArray: Uri, email: String, password: String): Flow<Result<User, AuthResults>> =
        remoteDataSource.signUpWithEmail(name,avatarByteArray,email, password)
    override suspend fun getSignedInUser(): Result<User, AuthResults> =
        remoteDataSource.getSignedInUser()

    override suspend fun logout(): Result<AuthResults, AuthResults> {
        return remoteDataSource.logout()
    }

    override suspend fun deleteAccount(userId: String): Result<Unit, AuthResults> {
        return remoteDataSource.deleteAccount(userId).also{
            if (it is Result.Success) {
                logout()
            }
        }
    }


}
