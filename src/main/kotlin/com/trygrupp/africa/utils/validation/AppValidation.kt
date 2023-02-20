package com.trygrupp.africa.utils.validation

import org.springframework.stereotype.Component

@Component
class AppValidation {

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        val pattern = "^(\\+?234|0)[789][01]\\d{8}\$".toRegex()
        return pattern.matches(phoneNumber)
    }

}