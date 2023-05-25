package com.example.fd.data

import com.example.fd.model.User
import com.google.firebase.auth.FirebaseUser
import  com.example.fd.data.model.Result
import com.example.fd.model.AuthenticationUser
import kotlinx.coroutines.flow.StateFlow

interface AuthenticationRepository {
    val currentUser: FirebaseUser?

    suspend fun firebaseSignUp(user: User): Result<Boolean>

    suspend fun firebaseLogIn(user: AuthenticationUser): Result<Boolean>

    suspend fun reloadFirebaseUser(): Result<Boolean>

    fun signOut()

    fun getAuthState(): StateFlow<Boolean>

}