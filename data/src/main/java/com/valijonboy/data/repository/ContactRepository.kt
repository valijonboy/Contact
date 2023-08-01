package com.valijonboy.data.repository

import com.valijonboy.data.datasource.entity.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {

  suspend fun insertContact(contact: Contact)

  suspend fun deleteContact(contact: Contact)

  suspend fun updateContact(contact: Contact)

   fun getContact(id: Int) : Flow<Contact?>

   fun getAllContacts() : Flow<List<Contact>>
}