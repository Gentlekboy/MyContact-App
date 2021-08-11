package com.gentlekboy.mycontactapp.firstImplementation.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.gentlekboy.mycontactapp.R
import com.gentlekboy.mycontactapp.databinding.FragmentContactDetailBinding
import com.gentlekboy.mycontactapp.databinding.FragmentContactsBinding
import com.gentlekboy.mycontactapp.firstImplementation.data.ContactClickListener
import com.gentlekboy.mycontactapp.firstImplementation.data.ContactsData

class ContactDetailFragment : Fragment(), ContactClickListener {

    //Pass data from previous fragment using argument
    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!
    private val args : ContactDetailFragmentArgs by navArgs()
    lateinit var contactsData: ContactsData
    private val adapter = ContactAdapter(this)
    private var viewModel: ContactsViewModel = ContactsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contactsData = args.contactData

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Bind data from previous fragment to appropriate textviews
        binding.displayFirstName.text = contactsData.firstName
        binding.displayLastName.text = contactsData.lastName
        binding.displayPhoneNumber.text = contactsData.phoneNumber
        binding.email.text = contactsData.email

        //Click delete icon to delete contact and move to contact fragment
        binding.deleteIcon.setOnClickListener {
            viewModel.deleteContact(contactsData)
            Toast.makeText(context, "Contact deleted successfully", Toast.LENGTH_SHORT).show()

            //Navigate to contact fragment
            val action = ContactDetailFragmentDirections.actionContactDetailFragmentToContactsFragment()
            findNavController().navigate(action)

        }

        //Click edit icon to update contact details
        binding.editIcon.setOnClickListener {
        }

        //Click email icon to send mail to contact
        binding.emailArea.setOnClickListener {
            sendMail()
        }

        //Click call button to make a phone call to the contact
        binding.callButton.setOnClickListener {
            makePhoneCall()
        }

        //Click share icon to share contact details
        binding.shareIcon.setOnClickListener {
            shareContact()
        }
    }

    //Share contact as text to other apps
    private fun shareContact() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            putExtra(Intent.EXTRA_TEXT, contactsData.phoneNumber)
            putExtra(Intent.EXTRA_TEXT, contactsData.firstName + contactsData.lastName)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(intent,"Share contact via")
        startActivity(shareIntent)
    }

    //Send mail to the contact's email address
    private fun sendMail(){
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_EMAIL, arrayOf(contactsData.email))
            type = "message/rfc822"
        }

        val sendMailIntent = Intent.createChooser(intent, "Send mail via:")
        startActivity(sendMailIntent)
    }

    //Ask for permission to call selected contact
    private fun makePhoneCall() {
        val string = binding.displayPhoneNumber.text.toString().trim()
        if (string.isNotEmpty()){
            if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), 200)
            }else{
                val number = "tel:$string"
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse(number)
                startActivity(intent)
            }
        }
    }

    //Verify if request has been granted
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 200){
            if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhoneCall()
            }else{
                Toast.makeText(requireContext(), "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    override fun onRecyclerViewItemClicked(contact: ContactsData) {}
}