package com.example.gemipost.data.auth.source.remote

import com.example.gemipost.data.auth.source.remote.UserData
import com.example.gemipost.data.auth.source.remote.UserRemoteDataSource
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.auth.source.remote.model.UserSettings
import com.example.gemipost.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.UserError
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

class UserRemoteDataSourceImpl(
    private val httpClient: HttpClient,
//    private val supabaseClient: SupabaseClient
) : UserRemoteDataSource {
    override suspend fun updateUserInfo(user: User): Result<Unit, UserError> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePhoneNumber(
        userId: String,
        phoneNumber: String
    ): Result<Unit, UserError> {
        TODO("Not yet implemented")
    }

    override suspend fun updateName(userId: String, name: String): Result<Unit, UserError> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserSettings(): Result<UserSettings, UserError> {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Result<Unit, UserError> {
        TODO("Not yet implemented")
    }

    override suspend fun changeEmail(userId: String, email: String): Result<Unit, UserError> {
        TODO("Not yet implemented")
    }

    override suspend fun updateStringRemoteUserSetting(
        userId: String,
        tag: String,
        value: String
    ): Result<Unit, UserError> {
        TODO("Not yet implemented")
    }

    override suspend fun updateBooleanRemoteUserSetting(
        userId: String,
        tag: String,
        value: Boolean
    ): Result<Unit, UserError> {
        TODO("Not yet implemented")
    }

    override fun fetchUsers(): Flow<Result<List<User>, UserError>> {
        TODO("Not yet implemented")
    }

    override fun getUsersByIds(request: GetUsersByIdsRequest): Flow<Result<List<User>, UserError>> {
        TODO("Not yet implemented")
    }

    override suspend fun uploadUserPfp(
        pfpByteArray: ByteArray,
        userId: String
    ): Result<String, UserError> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserAvatar(
        avatarByteArray: ByteArray,
        userId: String
    ): Result<Unit, UserError> {
        TODO("Not yet implemented")
    }

    override suspend fun createRemoteUser(user: User): Result<Unit, UserError> {
        TODO("Not yet implemented")
    }


}