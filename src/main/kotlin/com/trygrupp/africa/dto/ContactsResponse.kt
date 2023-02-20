package com.trygrupp.africa.dto

import java.time.LocalDateTime

data class ContactsResponse(
        val id: Long?,
        val name: String,
        val phoneNumber: String,
        val address: String,
        val createdAt: LocalDateTime?,
        val lastUpdatedAt: LocalDateTime?
)
