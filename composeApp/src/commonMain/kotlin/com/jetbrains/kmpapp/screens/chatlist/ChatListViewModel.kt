package com.jetbrains.kmpapp.screens.chatlist

import AuthServiceClient
import User
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ChatListUiState(
    val user: User? = null
)

class ChatListViewModel (private val authServiceClient: AuthServiceClient) : ViewModel() {

    private val _state = MutableStateFlow(ChatListUiState())
    val state: StateFlow<ChatListUiState> = _state


}