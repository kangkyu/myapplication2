package com.example.myapplication.android.modules

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private var viewDidLoad: Boolean = false

    fun onViewLoad() {
        if (viewDidLoad) {
            return
        }
        viewDidLoad()
        viewDidLoad = true
    }

    abstract fun viewDidLoad()
}
