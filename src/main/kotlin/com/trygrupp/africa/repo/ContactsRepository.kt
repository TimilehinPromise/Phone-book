package com.trygrupp.africa.repo

import com.trygrupp.africa.entity.Contacts
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository


interface ContactsRepository :CrudRepository<Contacts,Long>, JpaSpecificationExecutor<Contacts> {

    @Query("SELECT c FROM Contacts as c")
    fun getAllContacts(): List<Contacts>

}