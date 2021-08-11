package com.gentlekboy.mycontactapp.firstImplementation.data

import com.google.firebase.database.Exclude

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
    ){
    override fun equals(other: Any?): Boolean {
        return if (other is ContactsData){
            other.id == id
        }else{
            false
        }
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (phoneNumber?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (abbreviation?.hashCode() ?: 0)
        result = 31 * result + isDeleted.hashCode()
        return result
    }
}