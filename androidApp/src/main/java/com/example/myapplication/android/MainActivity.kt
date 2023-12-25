package com.example.myapplication.android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.android.ui.GreetingView
import com.example.myapplication.android.ui.GreetingViewModel
import net.openid.appauth.TokenRequest

class MainActivity : ComponentActivity() {
    private val greetingViewModel: GreetingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        greetingViewModel.initAuthService(applicationContext)

        intent?.let { handleIntent(it) }
        Log.d("MainActivity", "ready to setContent")
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView(greetingViewModel)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val action: String? = intent.action
        val data: Uri? = intent.data
        intent.data = null
        if (Intent.ACTION_VIEW == action && data != null && data.scheme == "myapplication" && data.host == "auth") {
            // Handle the OAuth data here (e.g., extract the authorization code)
            Log.d("MainActivity", data.toString())
            val authorizationCode = data.getQueryParameter("code")
            val stateParameter = data.getQueryParameter("state") //

            greetingViewModel.getTokens(authorizationCode, stateParameter)
        }
    }
}
