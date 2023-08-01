package com.valijonboy.contact.ui.addcontact

import com.valijonboy.data.datasource.entity.Contact

object ContactValidator {

    fun validateContact(contact: Contact): ValidationResult {
        var result = ValidationResult()

        if(contact.name.isBlank()) {
            result = result.copy(firstNameError = "The first name can't be empty.")
        }

        if(contact.lastName?.isBlank() == true) {
            result = result.copy(lastNameError = "The last name can't be empty.")
        }

        if(contact.phone?.isBlank() == true) {
            result = result.copy(phoneNumberError = "The phone number can't be empty.")
        }

        return result
    }

    data class ValidationResult(
        val firstNameError: String? = null,
        val lastNameError: String? = null,
        val emailError: String? = null,
        val phoneNumberError: String? = null,
    )
}