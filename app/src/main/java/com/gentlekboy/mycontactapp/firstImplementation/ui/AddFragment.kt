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

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        return binding.root
    }

    //Observe live data
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
}