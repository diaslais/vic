package com.laisd.mycontacts

class ContactsList() {
    private val contacts = mutableListOf<Contact>()
    private val emptyList = mutableListOf(Contact("Empty list", "0 Contacts"))

    fun saveContact(contact: Contact){
        contacts.add(contact)
    }

    fun getContacts(): MutableList<Contact> {
        if (contacts.isEmpty()) {
            return emptyList
        } else {
            return contacts
        }
    }
}