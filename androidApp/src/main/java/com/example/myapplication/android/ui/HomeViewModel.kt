package com.example.myapplication.android.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.myapplication.android.modules.BaseViewModel
import com.example.myapplication.api.PostingApi
import com.example.myapplication.repository.Posting
import com.example.myapplication.repository.PostingRepository
import kotlinx.coroutines.launch

class HomeViewModel() : BaseViewModel() {
    private val api = PostingApi()
    private val postingRepository = PostingRepository(api)

    val visiblePosting = mutableStateOf<Posting?>(null)

    override fun viewDidLoad() {
        viewModelScope.launch {
            postingRepository.getPosting(1).onFailure { println(it) }

            postingRepository.posting.collect {
                getPosting(it)
            }
        }
    }

    private fun getPosting(posting: Posting) {
        visiblePosting.value = posting
    }
}
