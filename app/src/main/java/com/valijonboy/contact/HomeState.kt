package com.valijonboy.contact

import com.valijonboy.data.datasource.entity.Contact

data class ContactListState(
    val contacts: List<Contact> = emptyList(),
    val contactsFromPhone: List<Contact> = emptyList(),
    val recentlyAddedContacts: List<Contact> = emptyList(),
    val selectedContact: Contact? = null,
    val isAddContactSheetOpen: Boolean = false,
    val isSelectedContactSheetOpen: Boolean = false,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val phoneNumberError: String? = null,
)
