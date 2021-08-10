package com.gentlekboy.mycontactapp.firstImplementation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.gentlekboy.mycontactapp.R
import com.gentlekboy.mycontactapp.databinding.FragmentAddContactBinding
import com.gentlekboy.mycontactapp.databinding.FragmentUpdateContactBinding
import com.gentlekboy.mycontactapp.firstImplementation.data.ContactsData

class UpdateContactFragment(private val contactsData: ContactsData) : DialogFragment() {
    private var _binding: FragmentUpdateContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateContactBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        return binding.root
    }

    //Observe live data
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.firstName.setText(contactsData.firstName)
        binding.lastName.setText(contactsData.lastName)
        binding.phoneNumber.setText(contactsData.phoneNumber)
        binding.emailAddress.setText(contactsData.email)

        binding.updateButton.setOnClickListener {
            val contactFirstName = binding.firstName.text.toString().trim()
            val contactLastName = binding.lastName.text.toString().trim()
            val contactPhoneNumber = binding.phoneNumber.text.toString().trim()
            val contactEmail = binding.emailAddress.text.toString().trim()

            if (contactFirstName.isEmpty()){
                binding.firstName.error = R.string.first_name_required.toString()
                return@setOnClickListener
            }

            if (contactLastName.isEmpty()){
                binding.lastName.error = R.string.last_name_required.toString()
                return@setOnClickListener
            }

            if (contactPhoneNumber.isEmpty()){
                binding.phoneNumber.error = R.string.phone_number_name_required.toString()
                return@setOnClickListener
            }

            contactsData.firstName = contactFirstName
            contactsData.lastName = contactLastName
            contactsData.phoneNumber = contactPhoneNumber
            contactsData.email = contactEmail

            viewModel.updateContact(contactsData)
            dismiss()

            Toast.makeText(context, "Contact has been update", Toast.LENGTH_SHORT).show()
        }
    }
}