package com.example.gemipost.data.auth.source.remote

import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.auth.source.remote.model.UserSettings
import com.example.gemipost.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.UserError
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    suspend fun updateUserInfo(user: User): Result<Unit, UserError>
    suspend fun updatePhoneNumber(userId: String, phoneNumber: String): Result<Unit, UserError>
    suspend fun updateName(userId: String, name: String): Result<Unit, UserError>
    suspend fun getUserSettings(): Result<UserSettings, UserError>
    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit, UserError>
    suspend fun changeEmail(userId: String, email: String): Result<Unit, UserError>
    suspend fun updateStringRemoteUserSetting(userId: String, tag: String, value: String): Result<Unit, UserError>
    suspend fun updateBooleanRemoteUserSetting(userId: String, tag: String, value: Boolean): Result<Unit, UserError>
    fun fetchUsers(): Flow<Result<List<User>, UserError>>
    fun getUsersByIds(request: GetUsersByIdsRequest): Flow<Result<List<User>, UserError>>
    suspend fun uploadUserPfp(pfpByteArray: ByteArray, userId: String): Result<String, UserError>
    suspend fun updateUserAvatar(avatarByteArray: ByteArray, userId: String): Result<Unit, UserError>
    suspend fun createRemoteUser(user: User): Result<Unit, UserError>
}