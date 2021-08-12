package com.gentlekboy.mycontactapp.firstImplementation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gentlekboy.mycontactapp.R
import com.gentlekboy.mycontactapp.databinding.FragmentContactsBinding
import com.gentlekboy.mycontactapp.firstImplementation.data.ContactClickListener
import com.gentlekboy.mycontactapp.firstImplementation.data.ContactsData

class ContactsFragment : Fragment(), ContactClickListener {
    private lateinit var viewModel: ContactsViewModel
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val adapter = ContactAdapter(this)

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

        //Navigate to add contact page when floating action button is clicked
        binding.addButton.setOnClickListener {
            val action = ContactsFragmentDirections.actionContactsFragmentToAddFragment()
            findNavController().navigate(action)
        }

        //Observe if contact is added
        viewModel.contacts.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.addContacts(it)
            }
        })

        viewModel.getRealTimeUpdate()

        //Update recyclerview when an update is made
        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)
    }

    private var simpleCallBack = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        //Swipe right to update contact and left to delete contact
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.absoluteAdapterPosition
            val currentContact = adapter.listOfContactsToBeShown[position]

            when(direction){
                ItemTouchHelper.RIGHT -> {
                    UpdateContactDialogueFragment(currentContact).show(childFragmentManager, "")
                }
                ItemTouchHelper.LEFT -> {
                    AlertDialog.Builder(requireContext()).also {
                        it.setTitle(getString(R.string.delete_prompt))

                        it.setPositiveButton(getString(R.string.yes)){dialogue, which ->
                            viewModel.deleteContact(currentContact)
                            binding.recyclerview.adapter?.notifyItemRemoved(position)

                            Toast.makeText(context, getString(R.string.Contact_deleted_successfully), Toast.LENGTH_SHORT).show()
                        }
                    }.create().show()
                }
            }

            binding.recyclerview.adapter?.notifyDataSetChanged()
        }
    }

    //Navigate to contact detail when a recyclerview item is clicked
    override fun onRecyclerViewItemClicked(contact: ContactsData) {
        val action = ContactsFragmentDirections.actionContactsFragmentToContactDetailFragment(contact)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}