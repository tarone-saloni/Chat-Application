package com.example.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
fun SignInScreen(
    modifier: Modifier = Modifier,
    onNavigateToSignUp: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    viewModel: AuthViewMode = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthViewMode.AuthState.Success -> {
                onLoginSuccess()
                viewModel.resetSate()
            }
            else -> Unit
            }
    }

    val signUpText = buildAnnotatedString {
        append("Don't have an account? ")
        pushStringAnnotation(tag = "SIGN_UP", annotation = "sign_up")
        withStyle(
            style = SpanStyle(
                color = Color(0xFFE75480),
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append("Sign Up")
        }
        pop()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(80.dp))


        Text(
            text = "Welcome Back!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F1F1F)
        )

        Text(
            text = "Sign in to continue",
            fontSize = 16.sp,
            color = Color(0xFF757575),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(125.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFE75480),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon",
                    tint = Color(0xFF757575)
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFE75480),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon",
                    tint = Color(0xFF757575)
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = "Toggle password visibility",
                        tint = Color(0xFF757575)
                    )
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.End
        ) {
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFE75480),
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append("Forgot Password?")
                    }
                },
                onClick = {ofsset ->
                    println("Forgot Password clicked!, Offset: $ofsset")
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        if(authState is AuthViewMode.AuthState.Error){
            Text(
                text = (authState as AuthViewMode.AuthState.Error).message,
                color=Color.Red,
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
                onClick = { viewModel.signIn(email, password) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.height(55.dp).padding(horizontal = 24.dp),
                enabled = authState !is AuthViewMode.AuthState.Loading

            ) {
                if (authState is AuthViewMode.AuthState.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }else{
                    Text(
                        text = "Log In",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        OrDivider()

        Spacer(modifier = Modifier.height(24.dp))

        SocialLoginRow()

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


            Spacer(modifier = Modifier.height(60.dp))

            ClickableText(
                text = signUpText,
                onClick = { offset ->
                    signUpText.getStringAnnotations(tag = "SIGN_UP", start = offset, end = offset)
                        .firstOrNull()?.let {
                            onNavigateToSignUp()
                        }
                },
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.sp,
                    color = Color(0xFF757575)
                ),
                modifier = Modifier.padding(bottom = 0.dp)
            )
        }
    }
}

@Composable
fun OrDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = Color(0xFFE0E0E0)
        )
        Text(
            text = "OR",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF9E9E9E),
            letterSpacing = 1.sp
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = Color(0xFFE0E0E0)
        )
    }
}

@Composable
fun SocialLoginRow(
    modifier: Modifier = Modifier,
    onGoogleClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
    onAppleClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SocialLoginIcon(
            iconRes = R.drawable.google,
            contentDesc = "Google",
            onClick = onGoogleClick
        )
        SocialLoginIcon(
            iconRes = R.drawable.facebook,
            contentDesc = "Facebook",
            onClick = onFacebookClick
        )
        SocialLoginIcon(
            iconRes = R.drawable.mac,
            contentDesc = "Apple",
            onClick = onAppleClick
        )
    }
}

@Composable
fun SocialLoginIcon(
    iconRes: Int,
    contentDesc: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(60.dp)
            .clickable(onClick = onClick),
        shape = CircleShape,
        border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = contentDesc
            )
        }
    }
}