package com.example.myapplication.android.ui

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse


@Composable
fun GreetingView(viewModel: GreetingViewModel) {
    val context = LocalContext.current
    val authIntent = viewModel.getAuthRequestIntent(context)

    val authLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { authResult ->
        Log.d("GreetingView", "hello2")
        if (authResult.resultCode == Activity.RESULT_OK) {
            // Handle authorization response
            val response = AuthorizationResponse.fromIntent(authResult.data!!)
            val exception = AuthorizationException.fromIntent(authResult.data!!)
            // ... Token exchange logic here
            if (response != null) {

                val authorizationCode = response.authorizationCode
                val stateParameter = response.getState()

                viewModel.getTokens(authorizationCode, stateParameter)
            }

            Log.d("GreetingView", "hello")
            println("************************* ${response?.accessToken}")
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    try {
                        authLauncher.launch(authIntent)
                    } catch (e: Exception) {
                        Log.e("GreetingView", "Error launching auth intent", e)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,   //pre-created colour
                    containerColor = Color(0xff1e90ff),
                )
            ) {
                Text("Login with OAuth")
            }
        }
    }
}
