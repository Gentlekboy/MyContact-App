package com.gentlekboy.mycontactapp.firstImplementation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gentlekboy.mycontactapp.R
import com.gentlekboy.mycontactapp.databinding.FragmentEditContactBinding
import com.gentlekboy.mycontactapp.firstImplementation.data.ContactsData
import com.gentlekboy.mycontactapp.firstImplementation.data.Validate

class EditContactFragment : Fragment() {
    private var _binding: FragmentEditContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ContactsViewModel
    lateinit var contactsData: ContactsData
    private val args : EditContactFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contactsData = args.contactData
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditContactBinding.inflate(inflater, container, false)
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

            Toast.makeText(context, getString(R.string.Contact_updated_successfully), Toast.LENGTH_SHORT).show()

            //Navigate to contact fragment
            val action = EditContactFragmentDirections.actionEditContactFragmentToContactsFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}