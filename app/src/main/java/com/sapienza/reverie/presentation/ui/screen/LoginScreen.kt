package com.sapienza.reverie.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background // 1. Import 'background'
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box // 2. Import 'Box'
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush // 3. Import 'Brush'
import androidx.compose.ui.graphics.Color // 4. Import 'Color'
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sapienza.reverie.R
import com.sapienza.reverie.presentation.theme.ReverieFontFamily
import com.sapienza.reverie.ui.theme.GradientButton
import com.sapienza.reverie.ui.theme.NavBarColor
import com.sapienza.reverie.ui.theme.ReverieTheme

@Composable
fun LoginScreen(
    // These will be used for navigation later
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    // States for email, password, and password visibility
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    // 5. Define the gradient colors
    val gradientColors = listOf(
        Color(0xFFDEF1ED),  // Light Blue

        Color.White,
        Color(0xFFEBE6FF), // Light Purple

    )

    // 6. Wrap your content in a Box with a gradient background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColors,
                    // You can adjust startY and endY for where the gradient starts and ends
                )
            ),
        contentAlignment = Alignment.Center // Center the Column within the Box
    ) {
        Column(
            modifier = Modifier
                // The column no longer needs fillMaxSize, padding can be adjusted
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Title
            Image(painter = painterResource(id = R.drawable.reverie_logo), contentDescription = null)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Welcome back",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = ReverieFontFamily("Bold"), // Removed the incorrect parameter
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(48.dp)) // Space between title and text fields

            // Email Text Field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Text Field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (isPasswordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onLoginClick
            ) {
                Text("LOG IN")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign Up Navigation Option
            TextButton(onClick = onSignUpClick) {
                Text("Don't have an account? Sign Up")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ReverieTheme { // It's good practice to wrap previews in your theme
        LoginScreen()
    }
}
