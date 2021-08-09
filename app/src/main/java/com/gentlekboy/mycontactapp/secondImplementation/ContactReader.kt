package com.gentlekboy.mycontactapp.secondImplementation

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gentlekboy.mycontactapp.R

class ContactReader : AppCompatActivity() {
    //Initialized required variables
    private val neededPermissions = arrayOf(Manifest.permission.READ_CONTACTS)
    private val requestCodeForReadContact = 419
    private val contentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
    private val contactDisplayName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
    private val contactNumber = ContactsContract.CommonDataKinds.Phone.NUMBER
    private val contactID = ContactsContract.CommonDataKinds.Phone._ID
    private val dataToReadFromContactList = arrayOf(contactDisplayName, contactNumber, contactID)
    private val listOfContactsRead: ArrayList<ContactReaderData> = ArrayList()
    private lateinit var contactReaderAdapter: ContactReaderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_reader)

        requestPermissionToReadContact()
        addReadContactToRecyclerView()
    }

    //This function asks for permission if permission hasn't been granted before
    private fun requestPermissionToReadContact(){
        //If permission to read contact list has not been granted before
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, neededPermissions, requestCodeForReadContact)
        }else{
            readContactList()
        }
    }

    //This function checks if request code matches and permission is granted
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if ((requestCode == requestCodeForReadContact) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            readContactList()
        }
    }

    //This function reads phone contact and stores them in an arraylist
    private fun readContactList() {
        val fetchContactData = contentResolver.query(contentUri, dataToReadFromContactList, null, null, contactDisplayName)

        if (fetchContactData != null) {
            while (fetchContactData.moveToNext()){
                val fullName = fetchContactData.getString(fetchContactData.getColumnIndex(contactDisplayName))
                val phoneNumber =fetchContactData.getString(fetchContactData.getColumnIndex(contactNumber))
                val abbreviation =fetchContactData.getString(fetchContactData.getColumnIndex(contactDisplayName))[0].toString()

                val contactReaderData = ContactReaderData(fullName, abbreviation, phoneNumber)

                listOfContactsRead.add(contactReaderData)
            }
        }
    }

    //This function connects recyclerview to contact reader layout
    private fun addReadContactToRecyclerView(){
        val contactReaderRecyclerview = findViewById<RecyclerView>(R.id.contact_reader_recyclerview)
        contactReaderAdapter = ContactReaderAdapter(listOfContactsRead)
        contactReaderRecyclerview.adapter = contactReaderAdapter
        contactReaderRecyclerview.layoutManager = LinearLayoutManager(this)
        contactReaderAdapter.notifyDataSetChanged()
    }
}