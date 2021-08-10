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
        holder.binding.nameAbbreviation.text = listOfContactsToBeShown[position].abbreviation
        holder.binding.firstName.text = listOfContactsToBeShown[position].firstName
        holder.binding.lastName.text = listOfContactsToBeShown[position].lastName
        holder.binding.phoneNumber.text = listOfContactsToBeShown[position].phoneNumber
    }

    override fun getItemCount(): Int {
        return listOfContactsToBeShown.size
    }

    fun addContacts(contactsData: ContactsData){
        if (!listOfContactsToBeShown.contains(contactsData)){
            listOfContactsToBeShown.add(contactsData)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RecyclerviewContactsBinding): RecyclerView.ViewHolder(binding.root)
}