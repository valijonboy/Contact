package com.valijonboy.contact

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.valijonboy.contact.ui.home.HomeScreen
import com.valijonboy.contact.ui.home.HomeViewModel
import com.valijonboy.contact.ui.theme.ContactTheme
import com.valijonboy.data.datasource.entity.Contact
import com.valijonboy.domain.usecase.ContactUseCase
import dagger.hilt.android.AndroidEntryPoint
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
     lateinit var useCase: ContactUseCase
     private val contactsFromPhone = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactsListScreen(contacts = contactsFromPhone)
                    ContactsApp(useCase, contactsFromPhone)
                }
            }
        }
    }
}

@Composable
fun ContactsListScreen(contacts: MutableList<Contact>) {
    val context = LocalContext.current
    val hasPermission = ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.READ_CONTACTS
    ) == PackageManager.PERMISSION_GRANTED

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            GlobalScope.launch(Dispatchers.IO) {

//                LoadContacts(context, contacts)
            }
        } else {
            Toast.makeText(context, "Permission not denied", Toast.LENGTH_SHORT).show()
        }
    }


    LaunchedEffect(hasPermission) {
        if (!hasPermission) {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
        } else {

            GlobalScope.launch(Dispatchers.IO) {

                LoadContacts(context, contacts)
            }
        }
    }
}


fun LoadContacts(context: Context, contacts: MutableList<Contact>) {

    val contentResolver = context.contentResolver
    val cursor = contentResolver.query(
        ContactsContract.Contacts.CONTENT_URI,
        null,
        null,
        null,
        null
    )

    cursor?.use {
        if (it.count > 0) {
            while (it.moveToNext()) {
                val contactId = it.getLong(it.getColumnIndex(ContactsContract.Contacts._ID)) ?: 0
                val displayName =
                    it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) ?: ""
                val phoneNumber =
                    it.getString(it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        .toInt()



                if (phoneNumber > 0) {
                    val cursorPhone = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        arrayOf(contactId.toString()),
                        null
                    )

                    if (cursorPhone?.count!! > 0) {
                        while (cursorPhone.moveToNext()) {
                            val phoneNumValue = cursorPhone.getString(
                                cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )

                            contacts.add(Contact(contactId.toInt(), displayName, "", phoneNumValue))
                        }
                    }
                    cursorPhone.close()
                }
            }
        }
    }

    cursor?.close()
}


@Composable
fun  ContactsApp(useCase: ContactUseCase, contacts: MutableList<Contact>) {

    val viewModel = getViewModel(
        key = "home_screen",
        factory = viewModelFactory {
            HomeViewModel(useCase, contacts)
        }
    )
    val state by viewModel.state.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        HomeScreen(
            state = state,
            newContact = viewModel.newContact,
            onEvent = viewModel::onEvent
        )
    }
}