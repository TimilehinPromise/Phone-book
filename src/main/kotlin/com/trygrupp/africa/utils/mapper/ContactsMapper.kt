package com.trygrupp.africa.utils.mapper

import com.trygrupp.africa.dto.ContactsDTO
import com.trygrupp.africa.dto.ContactsResponse
import com.trygrupp.africa.entity.Contacts
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ContactsMapper : Mapper<ContactsDTO,Contacts>{
    override fun fromEntity(entity: Contacts):
            ContactsDTO = ContactsDTO(
            entity.id,
            entity.name,
            entity.phoneNumber,
            entity.address

    )

    override fun toEntity(domain: ContactsDTO): Contacts = Contacts(
            null,
            domain.name,
            domain.phoneNumber,
            domain.address,
            LocalDateTime.now(),
            LocalDateTime.now()

    )


    override fun toResponse(entity: Contacts):
            ContactsResponse = ContactsResponse(
            entity.id,
            entity.name,
            entity.phoneNumber,
            entity.address,
            entity.createdAt,
            entity.lastUpdatedAt

    )

}
