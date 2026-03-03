
package com.example.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun CustomOtpScreen(
    verificationId: String = "",
    onVerifiedSuccess: () -> Unit,
    viewModel: AuthViewMode = viewModel()
) {
    viewModel.storeVerificationID = verificationId
    val authState by viewModel.authState.collectAsState()

    var otp by remember { mutableStateOf("") }
    val maxLength = 6

    LaunchedEffect(authState) {
        when (authState) {
            is AuthViewMode.AuthState.Success -> onVerifiedSuccess()
            else -> Unit
        }

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Enter OTP",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "We sent a verification link to your email . \nPlease enter the OTP below",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(maxLength) { index ->
                val char = when {
                    index < otp.length -> otp[index].toString()
                    else -> ""
                }
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(55.dp)
                        .border(
                            2.dp,
                            if (index == otp.length) MaterialTheme.colorScheme.primary
                            else Color.Gray,
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    Text(
                        text = char,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        if(authState is AuthViewMode.AuthState.Error){
            Text(
                text = (authState as AuthViewMode.AuthState.Error).message,
                color = Color.Red,
                fontSize = 14.sp
            )

        }
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            val numbers = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf("", "0", "delete")
            )

            numbers.forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(40.dp),
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    row.forEach { item ->

                        when (item) {

                            "delete" -> {
                                Icon(
                                    imageVector = Icons.Default.Backspace,
                                    contentDescription = "Delete",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clickable {
                                            if (otp.isNotEmpty()) {
                                                otp = otp.dropLast(1)
                                            }
                                        }
                                )
                            }

                            "" -> {
                                Spacer(modifier = Modifier.size(40.dp))
                            }

                            else -> {
                                Text(
                                    text = item,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clickable {
                                            if (otp.length < maxLength) {
                                                otp += item
                                            }
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
    Button(
        onClick = { viewModel.verifyOTP(otp) },
        enabled = otp.length == 6 && authState is AuthViewMode.AuthState.Loading,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (authState is AuthViewMode.AuthState.Loading) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
        } else {
            Text("Verify OTP", fontSize = 16.sp)
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
}
