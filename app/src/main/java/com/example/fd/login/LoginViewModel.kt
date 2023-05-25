package com.example.fd.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fd.data.AuthenticationRepository
import com.example.fd.data.model.Result
import com.example.fd.model.AuthenticationUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) : ViewModel() {

    fun login(email: String, password: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val response = authenticationRepository.firebaseLogIn(AuthenticationUser(email, password))
            if (response is Result.Success) {
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }
}