package com.jetbrains.kmpapp.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(
    navigateToList: () -> Unit
) {
    val viewModel = koinViewModel<RegisterViewModel>()
    val state by viewModel.state.collectAsState()

    var error by remember { mutableStateOf<String?>(null) }

    RegisterScreenContent(
        state = state,
        onNameChanged = viewModel::onNameChanged,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onRegisterClicked = {
            viewModel.register(
                onSuccess = { navigateToList() },
                onError = { err -> error = err }
            )
        },
        error = error
    )
}

@Composable
fun RegisterScreenContent(
    state: RegisterUiState,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRegisterClicked: () -> Unit,
    error: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = state.name,
            onValueChange = onNameChanged,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state.email,
            onValueChange = onEmailChanged,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state.password,
            onValueChange = onPasswordChanged,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onRegisterClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        if (error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }
    }
}
