package com.example.myapplication.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.repository.Posting


@Composable
fun HomeView(viewModel: HomeViewModel) {
    LaunchedEffect(Unit) {
        viewModel.onViewLoad()
    }

    val posting = viewModel.visiblePosting.value
    if (posting != null) {
        PostingView(posting)
    }
}

@Composable
private fun PostingView(posting: Posting) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = posting.title,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
    }
}
