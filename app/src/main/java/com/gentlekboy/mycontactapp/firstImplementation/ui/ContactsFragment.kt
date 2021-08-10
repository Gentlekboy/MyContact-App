package com.gentlekboy.mycontactapp.firstImplementation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.gentlekboy.mycontactapp.R
import com.gentlekboy.mycontactapp.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {
    private lateinit var viewModel: ContactsViewModel
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val adapter = ContactAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Connect ContactAdapter to recyclerview on contacts fragment
        binding.recyclerview.adapter = adapter

        binding.addButton.setOnClickListener {
            AddContactFragment().show(childFragmentManager, "")
        }

        viewModel.contacts.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.addContacts(it)
            }
        })

        viewModel.getRealTimeUpdate()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}