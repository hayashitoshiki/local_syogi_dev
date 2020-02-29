package com.example.socketiosample.scarlet

typealias TicketId = Long

data class Ticket(
    val id: TicketId,
    val description: String
)