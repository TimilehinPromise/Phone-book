package com.trygrupp.africa.service





import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

import com.trygrupp.africa.dto.ContactsDTO
import com.trygrupp.africa.dto.ContactsResponse
import com.trygrupp.africa.entity.Contacts
import com.trygrupp.africa.repo.ContactsRepository
import com.trygrupp.africa.service.implementation.ContactsServiceImpl
import com.trygrupp.africa.utils.exception.ContactNotFoundException
import com.trygrupp.africa.utils.exception.ContactsException
import com.trygrupp.africa.utils.exception.PhoneNumberValidationException
import com.trygrupp.africa.utils.mapper.ContactsMapper
import com.trygrupp.africa.utils.validation.AppValidation
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.data.jpa.domain.Specification
import java.util.*


@SpringBootTest
class ContactsServiceImplTest {

    private lateinit var contactsService: ContactsServiceImpl

    @Mock
    private lateinit var contactsRepository: ContactsRepository

    @Mock
     private lateinit var appValidation: AppValidation

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val contactsMapper = ContactsMapper()
        contactsService = ContactsServiceImpl(contactsRepository, contactsMapper,appValidation)

    }

    @Test
    fun testCreateContact() {
        val  createdAt =LocalDateTime.now()
        val  updatedAt =LocalDateTime.now()
        val contactsDTO = ContactsDTO(34, "John", "08064708466", "123 Main St.")
        val contact = Contacts(1, "John", "08064708466", "123 Main St.", createdAt , updatedAt)

        Mockito.`when`(contactsRepository.save(Mockito.any(Contacts::class.java))).thenReturn(contact)
        Mockito.`when`(appValidation.validatePhoneNumber(contactsDTO.phoneNumber)).thenReturn(true)


        val expected = ContactsResponse(1, "John", "08064708466", "123 Main St.",createdAt,updatedAt)
        val actual = contactsService.createContact(contactsDTO)

        assertEquals(expected.name, actual.name)
        assertEquals(expected.phoneNumber,actual.phoneNumber)
        assertEquals(expected.address,actual.address)
        Mockito.verify(appValidation).validatePhoneNumber(contactsDTO.phoneNumber)
        assertNotNull(actual)
    }

    @Test
    fun testGetContacts() {
        val contact1 = Contacts(1, "John", "1234567890", "123 Main St.", null, null)
        val contact2 = Contacts(2, "Jane", "0987654321", "456 Broadway", null, null)
        val contactsList = listOf(contact1, contact2)
        Mockito.`when`(contactsRepository.getAllContacts()).thenReturn(contactsList)
        val expected = listOf(
                ContactsResponse(1, "John", "1234567890", "123 Main St.",null,null),
                ContactsResponse(2, "Jane", "0987654321", "456 Broadway",null,null)
        )
        val actual = contactsService.getContacts()
        assertEquals(expected, actual)
    }

    @Test
    fun testGetAContact() {
        // Create a sample contact
        val  createdAt =LocalDateTime.now()
        val  updatedAt =LocalDateTime.now()
        val contact = Contacts(1, "John", "1234567890", "123 Main St.", createdAt , updatedAt)
        val contactsDTO = ContactsDTO(name = "John", phoneNumber = "1234567890", address = "123 Main St.", id = 1)
        Mockito.`when`(contactsRepository.save(Mockito.any(Contacts::class.java))).thenReturn(contact)
        Mockito.`when`(contactsRepository.findAll(Mockito.any(Specification::class.java) as Specification<Contacts>?)).thenReturn(listOf(contact))
        Mockito.`when`(appValidation.validatePhoneNumber(contactsDTO.phoneNumber)).thenReturn(true)

        val createdContact = contactsService.createContact(contactsDTO)

        // Test getting a contact by name
        val keyword = "john"
        val contactsResponseList = contactsService.getAContact(keyword)
        println(contactsResponseList)
        assertEquals(1, contactsResponseList.size)
        assertEquals(createdContact.name, contactsResponseList[0].name)

        // Test getting a contact by phone number
        val keyword2 = "123"
        val contactsResponseList2 = contactsService.getAContact(keyword2)
        assertEquals(1, contactsResponseList2.size)
        assertEquals(createdContact.phoneNumber, contactsResponseList2[0].phoneNumber)

        // Test getting a contact by address
        val keyword3 = "main"
        val contactsResponseList3 = contactsService.getAContact(keyword3)
        assertEquals(1, contactsResponseList3.size)
        assertEquals(createdContact.address, contactsResponseList3[0].address)
    }

    @Test
    fun testDeleteContact() {
        // Create a sample contact
        val createdAt = LocalDateTime.now()
        val updatedAt = LocalDateTime.now()
        val contact = Contacts(1, "John", "1234567890", "123 Main St.", createdAt , updatedAt)

        Mockito.`when`(contactsRepository.findById(1)).thenReturn(Optional.of(contact))

        // Test deleting a contact
        assertDoesNotThrow { contactsService.deleteContact(1) }

        // Verify that the delete method was called with the correct contact
        Mockito.verify(contactsRepository, Mockito.times(1)).delete(contact)

        // Test deleting a non-existing contact
        Mockito.`when`(contactsRepository.findById(2)).thenReturn(Optional.empty())

        val exception = assertThrows<ContactNotFoundException> { contactsService.deleteContact(2) }
        assertEquals("Contact with ID 2 Not Found", exception.message)
    }

    @Test
    fun `createContact should throw PhoneNumberValidationException when phone number is invalid`() {
        val invalidPhoneNumber = "0801234567" // invalid phone number format
        val contactsDTO = ContactsDTO(
                id =1,
                name = "John",
                address = "Lekki",
                phoneNumber = invalidPhoneNumber
        )

        assertThrows<PhoneNumberValidationException> {
            contactsService.createContact(contactsDTO)
        }
    }


}