package com.example.myapplication.android.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.CodeVerifierUtil
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest
import java.util.logging.LogRecord


private const val baseUrl = "https://kyohoe-authorization-server-1a58960ea78f.herokuapp.com"

class GreetingViewModel : ViewModel() {

    // Configuration details
    private val clientId = "zzgol39SXf_OB4OyeQ-0ng3AObUb7qX5Ft5DEyLqNTc"
    private val redirectUri = Uri.parse("myapplication://auth")
    private val authorizationEndpointUri = Uri.parse("${baseUrl}/oauth/authorize")
    private val tokenEndpointUri = Uri.parse("${baseUrl}/oauth/token")
    private val scope = "public" // Adjust the scope according to your needs

    private var authService: AuthorizationService? = null
    val codeVerifier = CodeVerifierUtil.generateRandomCodeVerifier()
//    val codeChallenge = CodeVerifierUtil.deriveCodeVerifierChallenge(codeVerifier)

    fun initAuthService(context: Context) {
        Log.d("ViewModel", "Initializing AuthService")

        try {
            authService = AuthorizationService(context)
            // Rest of the initialization code
        } catch (e: Exception) {
            Log.e("ViewModel", "Error initializing AuthService", e)
        }
    }

    override fun onCleared() {
        super.onCleared()
        authService?.dispose()
    }

    fun getAuthRequestIntent(context: Context): Intent? {
        Log.d("ViewModel", "Building Auth Request")
        val authRequest = AuthorizationRequest.Builder(
                AuthorizationServiceConfiguration(
                    authorizationEndpointUri,
                    tokenEndpointUri
                ),
                clientId,
                ResponseTypeValues.CODE,
                redirectUri
            )
            .setScope(scope)
//            .setState("abc123")
            .setCodeVerifier(codeVerifier)
            .build()

        Log.d("ViewModel", "Getting Auth Request Intent")
        return authService?.getAuthorizationRequestIntent(authRequest)
    }

    fun getTokens(authorizationCode: String?, stateParameter: String?) {

        if (!authorizationCode.isNullOrEmpty() && !stateParameter.isNullOrEmpty()) {
//            if (stateParameter != "abc123") ...

            // Perform the token request
            val tokenExchangeRequest = TokenRequest.Builder(
                    AuthorizationServiceConfiguration(
                        authorizationEndpointUri,
                        tokenEndpointUri
                    ),
                    clientId
                )
                .setAuthorizationCode(authorizationCode)
                .setRedirectUri(redirectUri)
                .setCodeVerifier(codeVerifier)
                .build()

            authService?.performTokenRequest(tokenExchangeRequest) { response, exception ->
                // Handle token response or exception
                if (response != null) {
                    // Token exchange succeeded
                    val accessToken = response.accessToken
                    // Use the access token (e.g., for API calls)
                    Log.d("ViewModel", "Access Token: $accessToken")
                } else if (exception != null) {
                    // Token exchange failed
                    Log.e("ViewModel", "Token Request Error: ${exception.localizedMessage}")
                    // Handle the error appropriately
                }
            }
        }
    }
}
