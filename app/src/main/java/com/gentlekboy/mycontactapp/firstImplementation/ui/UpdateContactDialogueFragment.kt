package com.gentlekboy.mycontactapp.firstImplementation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.gentlekboy.mycontactapp.R
import com.gentlekboy.mycontactapp.databinding.FragmentUpdateContactDialogueBinding
import com.gentlekboy.mycontactapp.firstImplementation.data.ContactsData
import com.gentlekboy.mycontactapp.firstImplementation.data.Validate

class UpdateContactDialogueFragment(private val contactsData: ContactsData) : DialogFragment() {
    private var _binding: FragmentUpdateContactDialogueBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set style for dialogue fragment
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateContactDialogueBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.firstName.setText(contactsData.firstName)
        binding.lastName.setText(contactsData.lastName)
        binding.phoneNumber.setText(contactsData.phoneNumber)
        binding.emailAddress.setText(contactsData.email)

        //Update contact detail on click of the update button
        binding.updateButton.setOnClickListener {
            val contactFirstName = binding.firstName.text.toString().trim()
            val contactLastName = binding.lastName.text.toString().trim()
            val contactPhoneNumber = binding.phoneNumber.text.toString().trim()
            val contactEmail = binding.emailAddress.text.toString().trim()

            val validate = Validate()

            if (validate.validateFirstName_isEmpty_returnBoolean(contactFirstName)){
                binding.firstName.error = getString(R.string.first_name_required)
                return@setOnClickListener
            }

            if (validate.validateLastName_isEmpty_returnBoolean(contactLastName)){
                binding.lastName.error = getString(R.string.last_name_required)
                return@setOnClickListener
            }

            if (validate.validatePhoneNumber_isEmpty_returnBoolean(contactPhoneNumber)){
                binding.phoneNumber.error = getString(R.string.phone_number_is_required)
                return@setOnClickListener
            }

            if (validate.validateEmail_isEmpty_returnBoolean(contactEmail)){
                binding.emailAddress.error = getString(R.string.email_is_required)
                return@setOnClickListener
            }

            if (!validate.validateEmail_matchesEmailPattern_returnBoolean(contactEmail)){
                binding.emailAddress.error = getString(R.string.enter_valid_email)
                return@setOnClickListener
            }

            contactsData.firstName = contactFirstName
            contactsData.lastName = contactLastName
            contactsData.phoneNumber = contactPhoneNumber
            contactsData.email = contactEmail
            contactsData.abbreviation = "${contactFirstName[0].uppercaseChar()}${contactLastName[0].uppercase()}"

            viewModel.updateContact(contactsData)
            dismiss()

            Toast.makeText(context, getString(R.string.Contact_updated_successfully), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}