package com.gentlekboy.mycontactapp.firstImplementation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gentlekboy.mycontactapp.databinding.RecyclerviewContactsBinding
import com.gentlekboy.mycontactapp.firstImplementation.data.ContactsData

class ContactAdapter: RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    var listOfContactsToBeShown = arrayListOf<ContactsData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerviewContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return listOfContactsToBeShown.size
    }

    inner class ViewHolder(val binding: RecyclerviewContactsBinding): RecyclerView.ViewHolder(binding.root)
}