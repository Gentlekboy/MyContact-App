package com.gentlekboy.mycontactapp.firstImplementation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gentlekboy.mycontactapp.R

class ContactList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)
    }
}