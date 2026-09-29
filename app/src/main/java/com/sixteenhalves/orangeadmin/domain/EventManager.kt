package com.sixteenhalves.orangeadmin.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

object EventManager {
    private val channel = Channel<AppEvent>(Channel.BUFFERED)
    val channelFlow = channel.receiveAsFlow()

    fun triggerEvent(event: AppEvent) {
        CoroutineScope(Dispatchers.Default).launch {
            channel.send(event)
        }
    }

    sealed class AppEvent {
        data class ShowEvent(
            val message: String,
        ) : AppEvent()
    }
}
