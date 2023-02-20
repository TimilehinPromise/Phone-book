package com.trygrupp.africa.utils.exception

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.IllegalArgumentException

@RestControllerAdvice
class ExceptionHandler {



    @ExceptionHandler(MissingKotlinParameterException::class)
    fun contactExceptionHandler(exception :Exception) :ResponseEntity<ApiError>{
        val error = ApiError("Variables cannot be null")
        return ResponseEntity(error,error.status)
    }


    @ExceptionHandler(ContactNotFoundException::class)
    fun contactNotFoundExceptionHandler(exception :Exception) :ResponseEntity<ApiError>{
        val error = ApiError(exception.message)
        return ResponseEntity(error,error.status)
    }


    @ExceptionHandler(PhoneNumberValidationException::class)
    fun invalidPhoneNumberExceptionHandler(exception :Exception) :ResponseEntity<ApiError>{
        val error = ApiError(exception.message)
        return ResponseEntity(error,error.status)
    }



}