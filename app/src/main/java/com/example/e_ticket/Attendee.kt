package com.example.e_ticket

data class Attendee(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val college: String = "",
    val passtype: String = "",
    val scanned: Boolean = false
)
