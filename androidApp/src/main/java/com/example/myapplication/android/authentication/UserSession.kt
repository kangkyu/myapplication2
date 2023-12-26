package com.example.myapplication.android.authentication

import kotlinx.coroutines.flow.MutableStateFlow

class UserSession(var accessToken: String? = null) {
    val profile = MutableStateFlow<String>("")

    // TODO: get user profile from server
    fun setAccessToken() {
        profile.value = accessToken ?: ""
    }
}
