package com.gentlekboy.mycontactapp.firstImplementation.data

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

//Data class for recyclerview
@Parcelize
data class ContactsData(
    @get: Exclude
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var phoneNumber: String? = null,
    var email: String? = null,
    var abbreviation: String? = null,
    @get: Exclude
    var isDeleted: Boolean = false
    ) : Parcelable {
    override fun equals(other: Any?): Boolean {
        return if (other is ContactsData){
            other.id == id
        }else{
            false
        }
    }
}