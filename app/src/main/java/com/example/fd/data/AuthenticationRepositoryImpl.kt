package com.example.fd.data

import com.example.fd.data.model.Result
import com.example.fd.model.AuthenticationUser
import com.example.fd.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val scope: CoroutineScope
) : AuthenticationRepository {
    override val currentUser get() = auth.currentUser
    private val collection = FirebaseFirestore.getInstance().collection("users")

    override suspend fun firebaseSignUp(user: User): Result<Boolean> {
        return try {
            val authenticationResult = auth.createUserWithEmailAndPassword(user.email, user.password).await()
            authenticationResult.user?.let {
                collection.document(it.uid)
                    .set(
                        user
                    )
            }
            Result.Success(true)
        } catch (exception: Exception) {
            Result.Failure(exception)
        }
    }

    override suspend fun firebaseLogIn(user: AuthenticationUser): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(user.email, user.password).await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun reloadFirebaseUser(): Result<Boolean> {
        return try {
            auth.currentUser?.reload()?.await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override fun signOut() = auth.signOut()

    override fun getAuthState() = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(scope, SharingStarted.WhileSubscribed(), auth.currentUser == null)
}