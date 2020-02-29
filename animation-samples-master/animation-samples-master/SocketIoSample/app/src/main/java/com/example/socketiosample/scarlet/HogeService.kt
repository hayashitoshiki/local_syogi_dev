package com.example.socketiosample.scarlet

import com.example.socketiosample.scarlet.Command
import com.example.socketiosample.scarlet.Ticket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable
import io.socket.engineio.client.transports.WebSocket

interface HogeService {
//    @Receive
//    fun observeWebSocketEvent(): Flowable<WebSocket.Event>

    @Send
    fun sendCommand(command: Command)

    @Receive
    fun observeTicker(): Flowable<Ticket>
}