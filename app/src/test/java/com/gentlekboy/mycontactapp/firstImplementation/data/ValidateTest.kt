package com.gentlekboy.mycontactapp.firstImplementation.data

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class ValidateTest {
    private lateinit var validation: Validate

    @Before
    fun setUp(){
        validation = Validate()
    }

    @Test
    fun validateFirstName_isEmpty_returnBoolean() {
        val answer = validation.validateFirstName_isEmpty_returnBoolean("")
        assertEquals(true, answer)
    }

    @Test
    fun validateFirstName_isNotEmpty_returnBoolean() {
        val answer = validation.validateFirstName_isEmpty_returnBoolean("Kufre")
        assertEquals(false, answer)
    }

    @Test
    fun validateLastName_isEmpty_returnBoolean() {
        val answer = validation.validateLastName_isEmpty_returnBoolean("")
        assertEquals(true, answer)
    }

    @Test
    fun validateLastName_isNotEmpty_returnBoolean() {
        val answer = validation.validateLastName_isEmpty_returnBoolean("Udoh")
        assertEquals(false, answer)
    }

    @Test
    fun validateEmail_isEmpty_returnBoolean() {
        val answer = validation.validateEmail_isEmpty_returnBoolean("")
        assertEquals(true, answer)
    }

    @Test
    fun validateEmail_isNotEmpty_returnBoolean() {
        val answer = validation.validateEmail_isEmpty_returnBoolean("kufre.udoh@decagon.dev")
        assertEquals(false, answer)
    }

    @Test
    fun validatePhoneNumber_isEmpty_returnBoolean() {
        val answer = validation.validatePhoneNumber_isEmpty_returnBoolean("")
        assertEquals(true, answer)
    }

    @Test
    fun validatePhoneNumber_isNotEmpty_returnBoolean() {
        val answer = validation.validatePhoneNumber_isEmpty_returnBoolean("07067991832")
        assertEquals(false, answer)
    }

    @Test
    fun validateEmail_matchesEmailPattern_returnBoolean() {
        val answer = validation.validateEmail_matchesEmailPattern_returnBoolean("kufre.udoh@decagon.dev")
        assertEquals(true, answer)
    }

    @Test
    fun validateEmail_withoutAtSymbol_returnBoolean() {
        val answer = validation.validateEmail_matchesEmailPattern_returnBoolean("kufre.udohdecagon.dev")
        assertEquals(false, answer)
    }

    @Test
    fun validateEmail_withDotsAfterAtSymbol_returnBoolean() {
        val answer = validation.validateEmail_matchesEmailPattern_returnBoolean("kufre.udoh@decagon..dev")
        assertEquals(false, answer)
    }

    @Test
    fun validateEmail_withoutAlphabetsAfterAtSymbol_returnBoolean() {
        val answer = validation.validateEmail_matchesEmailPattern_returnBoolean("kufre.udoh@decagon.")
        assertEquals(false, answer)
    }
}