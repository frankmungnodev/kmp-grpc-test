package com.jetbrains.kmpapp.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.jetbrains.kmpapp.data.MuseumObject
import com.jetbrains.kmpapp.data.MuseumRepository
import io.github.timortel.kmpgrpc.core.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.proto.chat.ChatMessage
import org.example.proto.chat.ChatServiceStub

class ListViewModel(museumRepository: MuseumRepository) : ViewModel() {
    val objects: StateFlow<List<MuseumObject>> = museumRepository
            .getObjects()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    init {
        val channel = Channel.Builder
            .forAddress("127.0.0.1", 50051)
            .usePlaintext()
            .build()

        val stub = ChatServiceStub(channel)

        viewModelScope.launch {
            Logger.i("viewModelScope.launch")

            stub.ChatStream(flow {
                Logger.i("emit chat message")

                emit(ChatMessage("ios", content = "Hello", timestamp = ""))
            }).collectLatest { it ->
                Logger.i("Message $it")
            }
        }
    }
}
