package com.laisd.mycontacts

data class Personal(
    override val name: String,
    override val phoneNumber: String,
    val reference: String
): Contact(name = name, phoneNumber = phoneNumber, complement = reference)