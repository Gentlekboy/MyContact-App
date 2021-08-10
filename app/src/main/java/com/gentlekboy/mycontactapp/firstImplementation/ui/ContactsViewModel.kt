package com.gentlekboy.mycontactapp.firstImplementation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gentlekboy.mycontactapp.firstImplementation.data.ContactsData
import com.gentlekboy.mycontactapp.firstImplementation.data.NODE_CONTACTS
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlin.Exception

class ContactsViewModel: ViewModel() {
    //Reference to the db
    private val dbcontacts = FirebaseDatabase.getInstance().getReference(NODE_CONTACTS)

    //If MutableLiveData is null, data has been successfully sent to db
    //If not, it will be stored in LiveData (There was an error with sending data to db)
    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?> get() = _result

    fun addContact(contactsData: ContactsData){
        contactsData.id = dbcontacts.push().key.toString()
        contactsData.abbreviation = contactsData.firstName?.get(0)?.toUpperCase().toString() + contactsData.lastName?.get(0)
            ?.toUpperCase()
            .toString()

        //Check if data were sent to db successfully
        dbcontacts.child(contactsData.id!!).setValue(contactsData).addOnCompleteListener {
            if (it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }
}