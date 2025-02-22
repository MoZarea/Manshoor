package com.example.gemipost.data.auth.source.remote

import android.net.Uri
import android.util.Log
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.auth.source.remote.model.UserToken
import com.example.gemipost.utils.AppConstants
import com.example.gemipost.utils.AuthResults
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.ktx.storage
import com.gp.socialapp.util.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class AuthenticationRemoteDataSourceImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthenticationRemoteDataSource {
    override fun sendPasswordResetEmail(email: String): Flow<Result<AuthResults, AuthResults>> =
        callbackFlow {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(Result.Success(AuthResults.RESET_EMAIL_SENT))
                    } else {
                        trySend(Result.Error(AuthResults.NETWORK_ERROR))
                    }
                }
            awaitClose()
        }

    override fun signInWithEmail(
        email: String,
        password: String
    ): Flow<Result<User, AuthResults>> =
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
                    trySend(Result.Error(AuthResults.LOGIN_FAILED))
                }
            }
            awaitClose()
        }

    override fun signUpWithEmail(
        name: String,
        avatarByteArray: Uri,
        email: String,
        password: String
    ): Flow<Result<User, AuthResults>> = callbackFlow {
        Result.Loading
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
                trySend(Result.Error(AuthResults.SERVER_ERROR))
            }
        }
        awaitClose()
    }

    override suspend fun getSignedInUser(): Result<User, AuthResults> {
        if (Firebase.auth.currentUser == null) {
            return Result.Error(AuthResults.LOGIN_FAILED)
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

    override suspend fun logout(): Result<AuthResults, AuthResults> {
        auth.signOut()
        return Result.Success(AuthResults.LOGOUT_SUCCESS)
    }

    override suspend fun deleteAccount(userId: String): Result<Unit, AuthResults> {
        auth.currentUser?.delete()
        return Result.Success(Unit)
    }

    override suspend fun registerUserToken(userToken: UserToken): Flow<Result<Unit, AuthResults>> =
        callbackFlow {
            trySend(Result.Loading)
            try {
                val userId = auth.currentUser?.uid?:""
                if(userId.isBlank()){
                    trySend(Result.Error(AuthResults.LOGIN_FAILED))
                }
                val collectionRef =
                    firestore.collection(AppConstants.DB_Constants.USER_TOKENS.name)
                collectionRef.document(userId).set(userToken).addOnSuccessListener {
                    Log.d("UserToken", "Token registered successfully")
                    trySend(Result.Success(Unit))
                }.addOnFailureListener {
                    Log.d("UserToken", "Token registration failed")
                    it.printStackTrace()
                    trySend(Result.Error(AuthResults.SERVER_ERROR))
                }
            } catch (e: Exception) {
                Log.d("UserToken", "Token registration failed")
                e.printStackTrace()
                Result.Error(AuthResults.SERVER_ERROR)
            }
            awaitClose()
        }

    override suspend fun getUserToken(): Result<String, AuthResults> {
        return try{
            val result = FirebaseMessaging.getInstance().token.await()
            Result.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(AuthResults.SERVER_ERROR)
        }
    }
}