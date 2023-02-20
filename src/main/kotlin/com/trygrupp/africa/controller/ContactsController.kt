package com.trygrupp.africa.controller

import com.trygrupp.africa.dto.ContactsDTO
import com.trygrupp.africa.dto.ContactsResponse
import com.trygrupp.africa.service.abstracts.ContactsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/contacts")
class ContactsController(
        private val contactsService: ContactsService
) {

    @PostMapping
    fun createContact(@RequestBody @Valid contactsDTO: ContactsDTO): ResponseEntity<ContactsResponse> {
          return ResponseEntity(contactsService.createContact(contactsDTO), HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllContacts(): ResponseEntity<List<ContactsResponse>> =
            ResponseEntity.ok(contactsService.getContacts())

    @GetMapping("/search")
    fun getContact(@RequestParam("keyword") keyword: String):ResponseEntity<List<ContactsResponse>> =
            ResponseEntity.ok(contactsService.getAContact(keyword))

    @DeleteMapping("/del")
    fun deleteContact(@RequestParam("id") id: Long){
        ResponseEntity.ok(contactsService.deleteContact(id))
    }
}