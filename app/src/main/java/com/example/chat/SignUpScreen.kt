package com.example.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun CreateAccount(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    onNavigateToOtp: () -> Unit = {},
    onSignUpSuccess: () -> Unit = {},
    viewModel: AuthViewMode = viewModel()
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val authState by viewModel.authState.collectAsState()

LaunchedEffect(authState) {
    when (authState) {
        is AuthViewMode.AuthState.Success -> {
            onSignUpSuccess()
            viewModel.resetSate()
        }
        else -> Unit
    }
}

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.height(80.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {Text(
            text = "CONNECT",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE75480),
            modifier = Modifier.padding(top = 8.dp)
        )
            Text(
                text = "CHAT",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE75480),
            modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "BELONG",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE75480),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Let's get you started!",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))

        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFE75480),
            unfocusedBorderColor = Color.LightGray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
        val iconTint = Color(0xFF757575)

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = textFieldColors,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            leadingIcon = { Icon(Icons.Default.Person, "Full Name", tint = iconTint) }
        )
        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = textFieldColors,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            leadingIcon = { Icon(Icons.Default.Email, "Email", tint = iconTint) }
        )
        Spacer(modifier = Modifier.height(14.dp))


        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = textFieldColors,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            leadingIcon = { Icon(Icons.Default.Lock, "Password", tint = iconTint) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    Icon(icon, "Toggle password visibility", tint = iconTint)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = textFieldColors,
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            leadingIcon = {
                Icon(Icons.Default.Lock, "Confirm Password", tint = iconTint)
            },
            trailingIcon = {
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    val icon = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    Icon(icon, "Toggle confirm password visibility", tint = iconTint)
                }
            },
            isError = confirmPassword.isNotEmpty() && password != confirmPassword,
            supportingText = {
                if (confirmPassword.isNotEmpty() && password != confirmPassword) {
                    Text(
                        text = "Passwords do not match",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        if(authState is AuthViewMode.AuthState.Error){
            Text(
                text = ( authState as AuthViewMode.AuthState.Error).message,
                color = Color.Red,
                fontSize = 13.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFF6F91),
                            Color(0xFFE75480)
                        )
                    )
                )
        ) {

            Button(
                onClick = {
                    viewModel.signup( email, password, fullName, confirmPassword)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .height(55.dp)
                    .padding(horizontal = 24.dp),
                enabled = authState !is AuthViewMode.AuthState.Loading

            ) {
               if(authState is AuthViewMode.AuthState.Loading){
                   CircularProgressIndicator(
                       color = Color.White,
                       modifier = Modifier.size(24.dp),
                       strokeWidth = 2.dp

                   )
               } else {
                   Text(
                       text = "Create Account",
                       color = Color.White,
                       fontSize = 20.sp,
                       fontWeight = FontWeight.Bold
                   )
               }


            }
        }


        Spacer(modifier = Modifier.height(100.dp))

        val loginText = buildAnnotatedString {
            append("Already have an account? ")
            pushStringAnnotation("LOGIN", "login")
            withStyle(style = SpanStyle(color = Color(0xFFE75480), fontWeight = FontWeight.Bold)) {
                append("Log In")
            }
            pop()
        }
        ClickableText(
            text = loginText,
            onClick = { offset ->
                loginText.getStringAnnotations("LOGIN", offset, offset)
                    .firstOrNull()?.let {
                        onLoginClick()
                    }
            },
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}