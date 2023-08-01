package com.valijonboy.contact.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.valijonboy.contact.ContactListState
import com.valijonboy.contact.ui.ContactListEvent
import com.valijonboy.contact.ui.addcontact.ContactValidator
import com.valijonboy.data.datasource.entity.Contact
import com.valijonboy.domain.usecase.ContactUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel (
    private val useCase: ContactUseCase,
    private val contactsFromPhone: List<Contact>
) : ViewModel() {

    private val _state = MutableStateFlow(ContactListState())
    val state = combine(
        _state,
        useCase.getAllContacts()
    ) { state, contacts ->
        state.copy(
            contacts = contacts,
            contactsFromPhone = contactsFromPhone
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ContactListState())

    var newContact: Contact? by mutableStateOf(null)
        private set


    fun onEvent(event: ContactListEvent) {
        when (event) {
            ContactListEvent.DeleteContact -> {
                viewModelScope.launch {
                    _state.value.selectedContact?.id?.let { id ->
                        _state.update {
                            it.copy(
                                isSelectedContactSheetOpen = false
                            )
                        }
                        useCase.deleteContact(_state.value.selectedContact ?: Contact(null, ""))
                        delay(300L) // Animation delay
                        _state.update {
                            it.copy(
                                selectedContact = null
                            )
                        }
                    }
                }
            }

            ContactListEvent.DismissContact -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isSelectedContactSheetOpen = false,
                            isAddContactSheetOpen = false,
                            firstNameError = null,
                            lastNameError = null,
                            phoneNumberError = null
                        )
                    }
                    delay(300L) // Animation delay
                    newContact = null
                    _state.update {
                        it.copy(
                            selectedContact = null
                        )
                    }
                }
            }

            is ContactListEvent.EditContact -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            selectedContact = null,
                            isAddContactSheetOpen = true,
                            isSelectedContactSheetOpen = false
                        )
                    }
                    newContact = event.contact
                    useCase.updateContact(newContact ?: Contact(null, ""))
                    delay(300)
                }
            }

            ContactListEvent.OnAddNewContactClick -> {
                _state.update {
                    it.copy(
                        isAddContactSheetOpen = true
                    )
                }
                newContact = Contact(
                    id = null,
                    name = "",
                    lastName = "",
                    phone = ""
                )
            }

            is ContactListEvent.OnFirstNameChanged -> {
                newContact = newContact?.copy(
                    name = event.value
                )
            }

            is ContactListEvent.OnLastNameChanged -> {
                newContact = newContact?.copy(
                    lastName = event.value
                )
            }

            is ContactListEvent.OnPhoneNumberChanged -> {
                newContact = newContact?.copy(
                    phone = event.value
                )
            }

            ContactListEvent.SaveContact -> {
                newContact?.let { contact ->
                    val result = ContactValidator.validateContact(contact)
                    val errors = listOfNotNull(
                        result.firstNameError,
                        result.lastNameError,
                        result.emailError,
                        result.phoneNumberError
                    )

                    if (errors.isEmpty()) {
                        _state.update {
                            it.copy(
                                isAddContactSheetOpen = false,
                                firstNameError = null,
                                lastNameError = null,
                                phoneNumberError = null
                            )
                        }
                        viewModelScope.launch {
                            useCase.insertContact(contact)
                            delay(300L) // Animation delay
                            newContact = null
                        }
                    } else {
                        _state.update {
                            it.copy(
                                firstNameError = result.firstNameError,
                                lastNameError = result.lastNameError,
                                phoneNumberError = result.phoneNumberError
                            )
                        }
                    }
                }
            }

            is ContactListEvent.SelectContact -> {
                _state.update {
                    it.copy(
                        selectedContact = event.contact,
                        isSelectedContactSheetOpen = true
                    )
                }
            }
        }
    }
}