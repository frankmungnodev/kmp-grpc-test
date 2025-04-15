package com.jetbrains.kmpapp.screens.login

import AuthServiceClient
import LoginRequest
import RegisterResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liftric.kvault.KVault
import com.squareup.wire.GrpcException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authServiceClient: AuthServiceClient,
    private val kVault: KVault
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    fun onEmailChanged(newEmail: String) {
        _state.value = _state.value.copy(email = newEmail)
    }

    fun onPasswordChanged(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword)
    }

    fun login() {
        viewModelScope.launch {
            try {
                val request = LoginRequest(
                    email = _state.value.email,
                    password = _state.value.password,
                )
                val response: RegisterResponse = authServiceClient.Login().execute(request)
                kVault.set("token", response.token)

                _state.value = _state.value.copy(isSuccessful = true)
            } catch (e: Exception) {
                val message = if (e is GrpcException) {
                    if (e.grpcStatus.name == "ALREADY_EXISTS") {
                        "User already exists"
                    } else {
                        e.grpcMessage ?: "Registration failed"
                    }
                } else {
                    e.message ?: "Registration failed"
                }
                _state.value = _state.value.copy(isSuccessful = false, error = message)
            }
        }
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
)
