package com.gentlekboy.mycontactapp.firstImplementation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gentlekboy.mycontactapp.databinding.RecyclerviewContactsBinding
import com.gentlekboy.mycontactapp.firstImplementation.data.ContactClickListener
import com.gentlekboy.mycontactapp.firstImplementation.data.ContactsData

class ContactAdapter(val contactClickListener: ContactClickListener): RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    var listOfContactsToBeShown = arrayListOf<ContactsData>()

    //Viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerviewContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    //Bind data to views on view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.nameAbbreviation.text = listOfContactsToBeShown[position].abbreviation
        holder.binding.firstName.text = listOfContactsToBeShown[position].firstName
        holder.binding.lastName.text = listOfContactsToBeShown[position].lastName
        holder.binding.phoneNumber.text = listOfContactsToBeShown[position].phoneNumber

        //Get individual items on recyclerview
        holder.binding.recyclerviewCard.setOnClickListener {
            contactClickListener.onRecyclerViewItemClicked(listOfContactsToBeShown[position])
        }
    }

    //Get size of arraylist
    override fun getItemCount(): Int {
        return listOfContactsToBeShown.size
    }

    fun addContacts(contactsData: ContactsData){
        if (!listOfContactsToBeShown.contains(contactsData)){
            //Add contact to array list
            listOfContactsToBeShown.add(contactsData)

        }else{
            val index = listOfContactsToBeShown.indexOf(contactsData)

            if (contactsData.isDeleted){
                //Remove contact from array list
                listOfContactsToBeShown.removeAt(index)

            }else{
                //Update contact in the array list
                listOfContactsToBeShown[index] = contactsData
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RecyclerviewContactsBinding): RecyclerView.ViewHolder(binding.root)
}