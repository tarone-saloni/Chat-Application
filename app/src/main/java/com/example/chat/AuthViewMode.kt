package com.example.chat

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewMode : ViewModel(){

private val auth: FirebaseAuth = FirebaseAuth.getInstance()

 private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
 val authState: StateFlow<AuthState> = _authState

sealed class AuthState {
 object Idle : AuthState()
 object Loading : AuthState()
 object Success : AuthState()
 data class Error(val message: String) : AuthState()
}

 fun signIn(email: String, password: String){
  if (email.isBlank() || password.isBlank()){
   _authState.value = AuthState.Error("Please fill in all fields")
  }
  _authState.value = AuthState.Loading
  auth.signInWithEmailAndPassword(email, password)
   .addOnFailureListener { exception ->
    _authState.value = AuthState.Error(
     exception.message ?: "Sign in failed"
    )
   }
 }
 fun resetSate(){
 _authState.value = AuthState.Idle}

 fun signup(email: String, password: String,name:String,confirmPassword:String){
  if(email.isBlank() || name.isBlank() || password.isBlank()){
   _authState.value = AuthState.Error("Please fill in all fields")
   return
  }

  if(password != confirmPassword){
   _authState.value = AuthState.Error("Passwords do not match")
   return
  }

  if(password.length < 6){
   _authState.value = AuthState.Error("Password must be at least 6 characters long")
   return}

  _authState.value = AuthState.Loading

  auth.createUserWithEmailAndPassword(email,password)
   .addOnSuccessListener { result->
    val profileUpdate = UserProfileChangeRequest.Builder()
     .setDisplayName(name)
     .build()
    result.user?.updateProfile(profileUpdate)
    _authState.value = AuthState.Success

   }
   .addOnFailureListener{ exception ->
    _authState.value = AuthState.Error(
     exception.message ?: "sign up failed"
    )

   }
  }
}