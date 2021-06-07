package com.laisd.mycontacts

data class Work(
    override val name: String,
    override val phoneNumber: String,
    val email: String
): Contact(name = name, phoneNumber = phoneNumber, complement = email)