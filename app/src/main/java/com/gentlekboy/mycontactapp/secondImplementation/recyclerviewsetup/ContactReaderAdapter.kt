package com.gentlekboy.mycontactapp.secondImplementation.recyclerviewsetup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gentlekboy.mycontactapp.R

class ContactReaderAdapter (private val listOfReadContacts: ArrayList<ContactReaderData>): RecyclerView.Adapter<ContactReaderAdapter.ContactReaderViewHolder>() {
    inner class ContactReaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameAbbreviation: TextView = itemView.findViewById(R.id.name_abbreviation_field)
        val fullName: TextView = itemView.findViewById(R.id.first_name)
        val phoneNumber: TextView = itemView.findViewById(R.id.phone_number_field)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactReaderViewHolder {
        val views = LayoutInflater.from(parent.context).inflate(R.layout.contact_reader_view_holder, parent, false)
        return ContactReaderViewHolder(views)
    }

    override fun onBindViewHolder(holder: ContactReaderViewHolder, position: Int) {
        val positionInList = listOfReadContacts[position]
        holder.nameAbbreviation.text = positionInList.nameAbbreviation
        holder.fullName.text = positionInList.fullName
        holder.phoneNumber.text = positionInList.phoneNumber
    }

    override fun getItemCount(): Int {
        return listOfReadContacts.size
    }
}