package com.example.musicstreaming.network.exception

import java.lang.RuntimeException

data class NetworkException(var code: Int, override var message: String?) :
    RuntimeException(message)