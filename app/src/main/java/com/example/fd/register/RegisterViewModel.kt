package com.example.fd.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fd.data.AuthenticationRepository
import com.example.fd.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    fun registerUser(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            authenticationRepository.firebaseSignUp(User(firstName = firstName, lastName = lastName, email = email, password = password))
        }
    }
}