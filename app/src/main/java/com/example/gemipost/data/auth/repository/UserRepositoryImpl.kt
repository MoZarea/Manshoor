package com.example.gemipost.data.auth.repository

import com.example.gemipost.data.auth.source.local.AuthKeyValueStorage
import com.example.gemipost.data.auth.source.remote.UserRemoteDataSource
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.UserError

class UserRepositoryImpl(
    private val userRemoteSource: UserRemoteDataSource,
    private val localKeyValueStorage: AuthKeyValueStorage
) : UserRepository {
    override suspend fun updateUserInfo(user: User, pfpByteArray: ByteArray): Result<Unit,UserError> {
        return if (pfpByteArray.isNotEmpty()) {
            val result = userRemoteSource.uploadUserPfp(pfpByteArray, user.id)
            if (result is Result.Success) {
                userRemoteSource.updateUserInfo(user.copy(profilePictureURL = result.data))
            } else {
                error(UserError.SERVER_ERROR)
            }
        } else {
            userRemoteSource.updateUserInfo(user)
        }
    }

    override suspend fun updatePhoneNumber(userId: String, phoneNumber: String): Result<Unit, UserError> {
        return userRemoteSource.updatePhoneNumber(userId, phoneNumber)
    }

    override suspend fun updateName(userId: String, name: String): Result<Unit, UserError> {
        return userRemoteSource.updateName(userId, name)
    }

//    override suspend fun getUserSettings(): Result<UserSettings, UserError> {
//        return userRemoteSource.getUserSettings().let { result ->
//            if (result is Result.Success)
//                Result.Success(result.data.copy(theme = localKeyValueStorage.theme ?: "System Default"))
//            else
//                result
//        }
//    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit, UserError> {
        return userRemoteSource.changePassword(oldPassword, newPassword)
    }

    override suspend fun changeEmail(userId: String, email: String): Result<Unit, UserError> {
        return userRemoteSource.changeEmail(userId, email)
    }

    override suspend fun updateStringRemoteUserSetting(
        userId: String,
        tag: String,
        value: String
    ): Result<Unit, UserError> {
        return  userRemoteSource.updateStringRemoteUserSetting(userId, tag, value)
    }

    override suspend fun updateBooleanRemoteUserSetting(
        userId: String,
        tag: String,
        value: Boolean
    ): Result<Unit, UserError> {
        return userRemoteSource.updateBooleanRemoteUserSetting(userId, tag, value)
    }

    override suspend fun updateUserAvatar(avatarByteArray: ByteArray, userId: String): Result<Unit, UserError> {
        return userRemoteSource.updateUserAvatar(avatarByteArray, userId)
    }


    override fun fetchUsers() = userRemoteSource.fetchUsers()
    override fun getUsersByIds(Ids: List<String>) =
        userRemoteSource.getUsersByIds(GetUsersByIdsRequest(Ids))

    override suspend fun createRemoteUser(user: User): Result<Unit, UserError> =
        userRemoteSource.createRemoteUser(user)

    override suspend fun getTheme(): Result<String, UserError> {
        return try{
            Result.Success(localKeyValueStorage.theme ?: "System Default")
        } catch (e: Exception){
            error(UserError.SERVER_ERROR)
        }
    }
    override suspend fun setTheme(theme: String) {
        localKeyValueStorage.theme = theme
    }

}