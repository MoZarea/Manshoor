package com.example.gemipost.data.auth.source.remote

import android.net.Uri
import com.example.gemipost.data.auth.source.remote.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.gp.socialapp.util.AuthError
import com.gp.socialapp.util.ErrorMessage
import com.gp.socialapp.util.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class AuthenticationRemoteDataSourceImpl(
    private val auth: FirebaseAuth,
) : AuthenticationRemoteDataSource {
    override fun sendPasswordResetEmail(email: String): Flow<Result<Unit, AuthError>> {
        TODO("Not yet implemented")
    }

    override fun signInWithEmail(
        email: String,
        password: String
    ): Flow<Result<User, ErrorMessage>> =
        callbackFlow {
            auth.signInWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    trySend(
                        Result.Success(
                            User(
                                id = user?.uid ?: "",
                                name = user?.displayName ?: "",
                                profilePictureURL = user?.photoUrl.toString(),
                                email = user?.email ?: "",
                                phoneNumber = user?.phoneNumber ?: "",
                                createdAt = user?.metadata?.creationTimestamp ?: 0L,
                                lastLoginAt = user?.metadata?.lastSignInTimestamp ?: 0L,
                            )
                        )
                    )
                } else {
                    trySend(Result.Error(ErrorMessage(task.exception?.localizedMessage ?: "error")))
                }
            }
            awaitClose()
        }

    override fun signUpWithEmail(
        name: String,
        avatarByteArray: Uri,
        email: String,
        password: String
    ): Flow<Result<User, AuthError>> = callbackFlow {
        var uri = Uri.EMPTY
        if (avatarByteArray != Uri.EMPTY) {
            uri = Firebase.storage.reference.child("avatars").putFile(avatarByteArray)
                .await().storage.downloadUrl.await()
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser?.updateProfile(
                    com.google.firebase.auth.UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .setPhotoUri(uri)
                        .build()
                )
                val user = auth.currentUser
                trySend(
                    Result.Success(
                        User(
                            id = user?.uid ?: "",
                            name = name,
                            profilePictureURL = uri.toString(),
                            email = user?.email ?: "",
                            phoneNumber = user?.phoneNumber ?: "",
                            createdAt = user?.metadata?.creationTimestamp ?: 0L,
                            lastLoginAt = user?.metadata?.lastSignInTimestamp ?: 0L,
                        )
                    )
                )
            } else {
                trySend(Result.Error(AuthError.SERVER_ERROR))
            }
        }
        awaitClose()
    }

    override suspend fun getSignedInUser(): Result<User, AuthError> {
        if (auth.currentUser == null) {
            return Result.Error(AuthError.SERVER_ERROR)
        } else {
            val user = auth.currentUser
            return Result.Success(
                User(
                    id = user?.uid ?: "",
                    name = user?.displayName ?: "",
                    profilePictureURL = user?.photoUrl.toString(),
                    email = user?.email ?: "",
                    phoneNumber = user?.phoneNumber ?: "",
                    createdAt = user?.metadata?.creationTimestamp ?: 0L,
                    lastLoginAt = user?.metadata?.lastSignInTimestamp ?: 0L,
                )
            )
        }
    }

    override suspend fun logout(): Result<Unit, AuthError> {
        auth.signOut()
        return Result.Success(Unit)
    }

    override suspend fun deleteAccount(userId: String): Result<Unit, AuthError> {
        auth.currentUser?.delete()
        return Result.Success(Unit)
    }


}