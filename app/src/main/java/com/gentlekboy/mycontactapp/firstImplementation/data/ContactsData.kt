package com.gentlekboy.mycontactapp.firstImplementation.data

import com.google.firebase.database.Exclude

data class ContactsData(
    @get: Exclude
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var phoneNumber: String? = null,
    var email: String? = null,
    var abbreviation: String? = null
    )