package com.gentlekboy.mycontactapp.firstImplementation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gentlekboy.mycontactapp.firstImplementation.data.ContactsData
import com.gentlekboy.mycontactapp.firstImplementation.data.NODE_CONTACTS
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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

    private val _contacts = MutableLiveData<ContactsData?>()
    val contacts: LiveData<ContactsData?> get() = _contacts

    fun addContact(contactsData: ContactsData){
        contactsData.id = dbcontacts.push().key.toString()
        contactsData.abbreviation = contactsData.firstName?.get(0)?.uppercaseChar().toString() +
                contactsData.lastName?.get(0)?.uppercaseChar().toString()

        //Check if data were sent to db successfully
        dbcontacts.child(contactsData.id!!).setValue(contactsData).addOnCompleteListener {
            if (it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }

    fun updateContact(contactsData: ContactsData){
        dbcontacts.child(contactsData.id!!).setValue(contactsData).addOnCompleteListener {
            if (it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }

    fun deleteContact(contactsData: ContactsData){
        dbcontacts.child(contactsData.id!!).setValue(null).addOnCompleteListener {
            if (it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }

    private val childEventListener = object: ChildEventListener{
        //When a new contact has been added
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val contact = snapshot.getValue(ContactsData::class.java)
            contact?.id = snapshot.key
            _contacts.value = contact
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val contact = snapshot.getValue(ContactsData::class.java)
            contact?.id = snapshot.key
            _contacts.value = contact
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val contact = snapshot.getValue(ContactsData::class.java)
            contact?.id = snapshot.key
            contact?.isDeleted = true
            _contacts.value = contact
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}
    }

    fun getRealTimeUpdate(){
        dbcontacts.addChildEventListener(childEventListener)
    }

    override fun onCleared() {
        super.onCleared()
        dbcontacts.removeEventListener(childEventListener)
    }
}