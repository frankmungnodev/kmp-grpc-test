package com.jetbrains.kmpapp.screens.register

import AuthServiceClient
import RegisterRequest
import RegisterResponse
import User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authServiceClient: AuthServiceClient
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterUiState>(RegisterUiState())
    val state: StateFlow<RegisterUiState> = _state

    fun onNameChanged(newName: String) {
        _state.value = _state.value.copy(name = newName)
    }

    fun onEmailChanged(newEmail: String) {
        _state.value = _state.value.copy(email = newEmail)
    }

    fun onPasswordChanged(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword)
    }

    fun register(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val request = RegisterRequest(
                    name = _state.value.name,
                    email = _state.value.email,
                    password = _state.value.password,
                )
                val response: RegisterResponse = authServiceClient.Register()

                    .execute(request)
                _state.value = _state.value.copy(token = response.token, user = response.user)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Registration failed")
            }
        }
    }
}

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val token: String? = null,
    val user: User? = null,
)
