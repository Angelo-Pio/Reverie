// Suggested Fix for: com.sapienza.reverie.domain.util.GoogleAuthUiClient.kt

package com.sapienza.reverie.domain.util

import android.content.Context

import android.os.Bundle
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.sapienza.reverie.presentation.viewmodel.SessionViewModel
import com.sapienza.reverie.properties.ApiProperties
import java.security.MessageDigest
import java.util.UUID

// Define a sealed class to represent the result of the sign-in attempt
sealed class SignInResult {
    data class Success(val idToken: String) : SignInResult()
    data class Error(val message: String) : SignInResult()
}

class GoogleAuthUiClient(private val context: Context, private val viewModel : SessionViewModel) {

    private val credentialManager = CredentialManager.create(context)

    // This is now a suspend function that returns a result
    suspend fun signIn(): SignInResult {
        // Nonce generation remains the same
        val nonce = UUID.randomUUID().toString()
        val bytes = nonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false) // Set to true if you only want to show existing accounts
            .setServerClientId(ApiProperties.CLIENT_ID)
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(request = request, context = context)
            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val token = googleIdTokenCredential.idToken
            viewModel.loginWithGoogle(token)



            SignInResult.Success(token)
        } catch (e: GetCredentialException) {
            SignInResult.Error("Failed to sign in: ${e.message}")
        } catch (e: Exception) { // Catch other potential exceptions
            SignInResult.Error("An unexpected error occurred: ${e.message}")
        }
    }
}
