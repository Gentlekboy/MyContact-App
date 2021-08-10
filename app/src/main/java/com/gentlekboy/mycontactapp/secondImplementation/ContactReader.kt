package com.gentlekboy.mycontactapp.secondImplementation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gentlekboy.mycontactapp.R
import com.gentlekboy.mycontactapp.secondImplementation.recyclerviewsetup.ContactReaderAdapter
import com.gentlekboy.mycontactapp.secondImplementation.recyclerviewsetup.ContactReaderData

class ContactReader : AppCompatActivity() {
    //Initialized required variables
    private lateinit var contactReaderAdapter: ContactReaderAdapter
    private lateinit var makeRequestButton: Button
    private lateinit var info: TextView
    private val listOfContactsRead: ArrayList<ContactReaderData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_reader)

        //Hook declared variables
        info = findViewById(R.id.info)
        makeRequestButton = findViewById(R.id.make_request_button)

        //Request permission to read contact
        requestPermissionToReadContact()

        //Add read contact to recyclerview
        addReadContactToRecyclerView()
    }

    //This function asks for permission if permission hasn't been granted before, else, it reads the contacts
    private fun requestPermissionToReadContact(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 419)
        }else{
            readContactList()
        }
    }

    //This function checks if request code matches and permission is granted
    override fun onRequestPermissionsResult (requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if ((requestCode == 419) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            readContactList()

        }else{
            makeRequestButton.visibility = View.VISIBLE
            info.visibility = View.VISIBLE

            makeRequestButton.setOnClickListener {
                makeRequestButton.visibility = View.GONE
                info.visibility = View.GONE

                requestPermissionToReadContact()
            }
        }
    }

    //This function reads phone contact, stores them in an arraylist and connects recyclerview to contact reader layout
    private fun readContactList() {
        val fetchContactData = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER),
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )

        if (fetchContactData != null) {
            while (fetchContactData.moveToNext()){
                val fullName = fetchContactData.getString(fetchContactData.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber =fetchContactData.getString(fetchContactData.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val abbreviation =fetchContactData.getString(fetchContactData.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))[0].toString()
                val contactReaderData = ContactReaderData(fullName, abbreviation, phoneNumber)

                listOfContactsRead.add(contactReaderData)
                addReadContactToRecyclerView()
            }
        }
    }

    //This function connects recyclerview to contact reader layout
    private fun addReadContactToRecyclerView(){
        val contactReaderRecyclerview = findViewById<RecyclerView>(R.id.contact_reader_recyclerview)
        contactReaderAdapter = ContactReaderAdapter(listOfContactsRead)
        contactReaderRecyclerview.adapter = contactReaderAdapter
        contactReaderRecyclerview.layoutManager = LinearLayoutManager(this)
    }
}