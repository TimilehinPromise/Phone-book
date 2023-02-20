package com.trygrupp.africa.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "contacts")
data class Contacts(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  var id: Long? = null,
        @Column(name = "name") val name: String,
        @Column(name = "phone_number") val phoneNumber: String,
        @Column(name = "address") val address: String,
        @Column(name = "created_at", nullable = false, updatable = false) val createdAt: LocalDateTime?,
        @Column(name = "last_updated_at", nullable = false) val lastUpdatedAt: LocalDateTime?
)
