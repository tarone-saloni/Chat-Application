package com.example.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun OtpScreen(){
    Column(){
        Text(
            text = "Verify Your Account",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F1F1F)
        )

        Text(
            text = "Enter the OTP sent to your email",
            fontSize = 16.sp
        )

        
    }

}
