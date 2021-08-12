package com.gentlekboy.mycontactapp.firstImplementation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gentlekboy.mycontactapp.R
import com.gentlekboy.mycontactapp.databinding.FragmentAddBinding
import com.gentlekboy.mycontactapp.firstImplementation.data.ContactsData
import com.gentlekboy.mycontactapp.firstImplementation.data.Validate

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ContactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.result.observe(viewLifecycleOwner, {
            val message = if(it == null){
                getString(R.string.added_contact)
            }else{
                getString(R.string.error, it.message)
            }

            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        //Save contact on click of the save button
        binding.saveContactButton.setOnClickListener {
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

            //Save added contact to the contact data object
            val contactsData = ContactsData()
            contactsData.firstName = contactFirstName
            contactsData.lastName = contactLastName
            contactsData.phoneNumber = contactPhoneNumber
            contactsData.email = contactEmail

            viewModel.addContact(contactsData)

            //Navigate to Contact fragment to view added contact
            val action = AddFragmentDirections.actionAddFragmentToContactsFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}