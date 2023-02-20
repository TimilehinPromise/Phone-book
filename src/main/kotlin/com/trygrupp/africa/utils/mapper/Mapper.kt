package com.trygrupp.africa.utils.mapper

import com.trygrupp.africa.dto.ContactsResponse
import com.trygrupp.africa.entity.Contacts

interface Mapper<D,E> {

    fun fromEntity(entity:E): D
    fun toEntity(domain:D): E
    fun toResponse(entity: Contacts): ContactsResponse
}