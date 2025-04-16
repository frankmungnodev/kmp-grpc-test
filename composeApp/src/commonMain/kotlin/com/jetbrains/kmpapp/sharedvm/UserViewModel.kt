package com.jetbrains.kmpapp.sharedvm

import AuthServiceClient
import GetMeRequest
import User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liftric.kvault.KVault
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UserState(
    val user: User? = null,
    val isLoadingGetMe: Boolean = true,
    val errorGetMe: Exception? = null,
)

class UserViewModel(
    private val authServiceClient: AuthServiceClient,
    private val kVault: KVault
) : ViewModel() {
    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state

    init {
        getMe()
    }

    fun getMe() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(
                    isLoadingGetMe = true,
                    errorGetMe = null
                )

                val response = authServiceClient.GetMe().execute(GetMeRequest())

                _state.value = _state.value.copy(
                    user = response,
                    isLoadingGetMe = false,
                    errorGetMe = null,
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoadingGetMe = false,
                    errorGetMe = e
                )
            }
        }
    }

    fun logout() {
        val isTokenDeleted = kVault.deleteObject("token")
        if (isTokenDeleted) {
            _state.value = _state.value.copy(user = null)
        }
    }
}