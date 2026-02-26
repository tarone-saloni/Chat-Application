package com.example.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.chat.ui.theme.ChatTheme
import androidx.navigation.compose.composable
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            SplashScreen()
            ChatTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNevigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNevigation(
    modifier: Modifier = Modifier
){

val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "starting"
    ){
        composable("starting"){
            SplashScreen(
                onGetStarted = {
                    navController.navigate("sign_in")
                }
            )
        }
        composable("sign_in"){
            SignInScreen(
                onNavigateToSignUp = {
                    navController.navigate("sign_up")
                }
            )
        }

        composable("sign_up"){
            CreateAccount(
                onLoginClick = {
                    navController.popBackStack()
                }
            )

        }
    }


}