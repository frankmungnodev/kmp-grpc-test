package com.jetbrains.kmpapp.screens.chatlist

import AuthServiceClient
import GetMeRequest
import User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ChatListUiState(
    val user: User? = null
)

class ChatListViewModel (private val authServiceClient: AuthServiceClient) : ViewModel() {

    private val _state = MutableStateFlow(ChatListUiState())
    val state: StateFlow<ChatListUiState> = _state

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            try {
                val response = authServiceClient.GetMe().execute(GetMeRequest())
                println("getme response => $response")
            } catch (e: Exception) {
                println("getme error => $e")
            }
        }
    }
}