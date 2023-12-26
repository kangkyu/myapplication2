package com.example.myapplication.android

import androidx.compose.runtime.mutableStateOf

class UiHelper {

    val routeName = mutableStateOf("login")

    fun goTo(routeName: String) {
        this.routeName.value = routeName
    }
}
