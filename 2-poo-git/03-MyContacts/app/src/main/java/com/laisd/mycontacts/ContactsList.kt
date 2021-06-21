package com.laisd.mycontacts

class ContactsList(val contacts: MutableList<Contact> = mutableListOf()){

    fun saveContact(contact: Contact){
        contacts.add(contact)
        filterDataSet()
    }

    fun filterDataSet() {
        contacts.sortBy { it.name.toUpperCase() }
    }
}