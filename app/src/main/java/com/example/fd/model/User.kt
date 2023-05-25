package com.example.fd.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    val email: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("password")
    val password: String
)

data class AuthenticationUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)