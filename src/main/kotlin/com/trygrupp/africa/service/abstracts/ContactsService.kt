package com.trygrupp.africa.service.abstracts

import com.trygrupp.africa.dto.ContactsDTO
import com.trygrupp.africa.dto.ContactsResponse

interface ContactsService  {

    fun createContact(contactsDTO: ContactsDTO): ContactsResponse

    fun getContacts(): List<ContactsResponse>

    fun getAContact(keyword: String): List<ContactsResponse>

    fun deleteContact(id: Long)
}