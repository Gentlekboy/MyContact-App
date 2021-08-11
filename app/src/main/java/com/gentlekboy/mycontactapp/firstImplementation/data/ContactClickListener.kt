package com.gentlekboy.mycontactapp.firstImplementation.data

//Interface controls clicking items on recyclerview
interface ContactClickListener {
    fun onRecyclerViewItemClicked(contact : ContactsData)
}