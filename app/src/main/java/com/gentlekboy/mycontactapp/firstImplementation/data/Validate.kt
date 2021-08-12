package com.gentlekboy.mycontactapp.firstImplementation.data

class Validate {
    fun validateFirstName_isEmpty_returnBoolean(firstName: String): Boolean{
        if (firstName.isEmpty()){
            return true
        }
        return false
    }

    fun validateLastName_isEmpty_returnBoolean(lastName: String): Boolean{
        if (lastName.isEmpty()){
            return true
        }
        return false
    }

    fun validateEmail_isEmpty_returnBoolean(email: String): Boolean{
        if (email.isEmpty()){
            return true
        }
        return false
    }

    fun validatePhoneNumber_isEmpty_returnBoolean(phoneNumber: String): Boolean{
        if (phoneNumber.isEmpty()){
            return true
        }
        return false
    }

    fun validateEmail_matchesEmailPattern_returnBoolean(email: String): Boolean{
        val emailPattern = "[a-zA-Z0-9._-]+@([a-z])+\\.[a-z]+".toRegex()

        if (email.matches(emailPattern)) {
            return true
        }
        return false
    }
}