package com.amolina.taller.ui.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen() {

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var loginResult = rememberSaveable { mutableStateOf("") }
    var authenticated = rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Spacer(modifier = Modifier.padding(all = 16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("User Name") })
        Spacer(modifier = Modifier.padding(all = 16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Passwrod") })
        Spacer(modifier = Modifier.padding(horizontal = 16.dp, vertical = 50.dp))
        Button(
            onClick = {
                isLoading = true
                coroutineScope.launch {
                    delay(200) // Simulate loading
                    if (username.isBlank() || password.isBlank()) {
                        loginResult.value = "User Name or Password is empty!"
                    } else {
                        if (username == "User Name" && password == "Password") {
                            authenticated.value = true
                            loginResult.value = "Authenticated"
                        } else {
                            loginResult.value = "Invalid credentials, please retry."
                            username = ""
                            password = ""
                        }
                    }
                    isLoading = false
                }

            }) {
            Text("Login")

        }

        if (isLoading) {
            DisplayLoading(true)
        }

        if (authenticated.value) {
            Authenticated()
        } else {
            Retry(loginResult.value)
        }
    }


}

@Composable
fun DisplayLoading(display: Boolean) {
    if (display) {
        CircularProgressIndicator()
    }
}

@Composable
fun Authenticated() {
    Text(text = "Authenticated")
}

@Composable
fun Retry(value: String) {
    Text(text = value)
}

