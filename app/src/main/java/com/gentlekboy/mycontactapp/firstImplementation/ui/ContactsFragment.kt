package com.gentlekboy.mycontactapp.firstImplementation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.absoluteAdapterPosition
            val currentContact = adapter.listOfContactsToBeShown[position]

            when(direction){
                ItemTouchHelper.RIGHT -> {
                    UpdateContactFragment(currentContact).show(childFragmentManager, "")
                }
                ItemTouchHelper.LEFT -> {
                    AlertDialog.Builder(requireContext()).also {
                        it.setTitle("Are you sure you want to delete this contact?")
                        it.setPositiveButton("Yes"){dialogue, which ->
                            viewModel.deleteContact(currentContact)
                            binding.recyclerview.adapter?.notifyItemRemoved(position)
                            Toast.makeText(context, "Contact has been deleted successfully", Toast.LENGTH_SHORT).show()
                        }
                    }.create().show()
                }
            }

            binding.recyclerview.adapter?.notifyDataSetChanged()
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}