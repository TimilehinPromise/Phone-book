package com.trygrupp.africa.dto


import javax.validation.constraints.NotNull


data class ContactsDTO(
val id: Long?,
@field:NotNull(message = "Name cannot be null")
val name: String,


val phoneNumber: String,

@field:NotNull(message = "Address cannot be null")
val address: String
)


