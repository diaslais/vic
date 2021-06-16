package com.laisd.mycontacts

class ContactsList(private val contacts: MutableList<Contact> = mutableListOf<Contact>(),
                   private val emptyList: MutableList<Contact> = mutableListOf(
                       Contact(
                           "Empty list",
                           "0 Contacts"
                       )
                   )
){

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