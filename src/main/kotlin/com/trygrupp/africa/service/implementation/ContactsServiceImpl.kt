package com.trygrupp.africa.service.implementation

import com.trygrupp.africa.dto.ContactsDTO
import com.trygrupp.africa.dto.ContactsResponse
import com.trygrupp.africa.entity.Contacts
import com.trygrupp.africa.repo.ContactsRepository
import com.trygrupp.africa.service.abstracts.ContactsService
import com.trygrupp.africa.utils.exception.ContactNotFoundException
import com.trygrupp.africa.utils.exception.ContactsException
import com.trygrupp.africa.utils.exception.PhoneNumberValidationException
import com.trygrupp.africa.utils.mapper.ContactsMapper
import com.trygrupp.africa.utils.validation.AppValidation
import org.springframework.stereotype.Service

@Service
class ContactsServiceImpl(
        private val contactsRepository: ContactsRepository,
        private val contactsMapper: ContactsMapper,
        private  val appValidation: AppValidation
) :ContactsService {
    override fun createContact(contactsDTO: ContactsDTO): ContactsResponse {
        if (!appValidation.validatePhoneNumber(contactsDTO.phoneNumber)) {
            throw PhoneNumberValidationException("Invalid phone number format")
        }

        val contact = contactsMapper.toEntity(contactsDTO)
         contactsRepository.save(contact)
        return contactsMapper.toResponse(contact)
    }

    override fun getContacts(): List<ContactsResponse> {
        val contacts = contactsRepository.getAllContacts()

        return contacts.map { contactsMapper.toResponse(it) }
    }

   override fun getAContact(keyword: String): List<ContactsResponse> {
        val keywordPattern = "%$keyword%"




       val contacts = contactsRepository.findAll { root, query, builder ->
           builder.or(
                   builder.like(root.get("name"), keywordPattern),
                   builder.like(root.get("phoneNumber"), keywordPattern),
                   builder.like(root.get("address"), keywordPattern)
           )
       }

       return contacts.map { contactsMapper.toResponse(it) }
    }

    override fun deleteContact(id: Long) {
        try{
        val optionalContact = contactsRepository.findById(id)
            val contact = optionalContact.get();
        contactsRepository.delete(contact)}
        catch (ex:Exception){

            throw ContactNotFoundException("Contact with ID $id Not Found")
        }
    }

}