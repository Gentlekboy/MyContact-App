package com.gentlekboy.mycontactapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.gentlekboy.mycontactapp.firstImplementation.ContactList
import com.gentlekboy.mycontactapp.secondImplementation.ContactReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Hooks for views
        val firstImplementationButton = findViewById<Button>(R.id.first_implementation_button)
        val secondImplementationButton = findViewById<Button>(R.id.second_implementation_button)

        //Navigate to first implementation
        firstImplementationButton.setOnClickListener {
            val intent = Intent(this, ContactList::class.java)
            startActivity(intent)
        }

        //Navigate to second implementation
        secondImplementationButton.setOnClickListener {
            val intent = Intent(this, ContactReader::class.java)
            startActivity(intent)
        }
    }
}