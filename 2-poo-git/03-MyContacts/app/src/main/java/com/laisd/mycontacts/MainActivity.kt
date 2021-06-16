package com.laisd.mycontacts

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    lateinit var contactsListRecyclerview: RecyclerView
    lateinit var searchView: SearchView
    lateinit var btnAddContact: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()

        makeRecyclerView(myContacts.getContacts())

        searchView.setOnQueryTextListener(this)

        btnAddContact.setOnClickListener{
            goToAddActivity()
        }
    }

    private fun goToAddActivity() {
        val intent = Intent(this, AddContactActivity::class.java)
        startActivity(intent)
    }

    private fun makeRecyclerView(list: MutableList<Contact>) {
        contactsListRecyclerview.layoutManager = LinearLayoutManager(this)
        val contactsListAdapter = ContactsListAdapter(list)
        contactsListRecyclerview.adapter = contactsListAdapter
    }

    private fun bindViews() {
        contactsListRecyclerview = findViewById(R.id.recyclerViewContacts)
        searchView = findViewById(R.id.searchView)
        btnAddContact = findViewById(R.id.fabAddContact)
    }

    private fun doMySearch(query: String) {
        val filteredList = mutableListOf<Contact>()
        val queryUpperCase = query.toUpperCase()
        for (contact in myContacts.getContacts()){
            if (contact.name.toUpperCase().startsWith(queryUpperCase)) {
                filteredList.add(contact)
            }
        }
        makeRecyclerView(filteredList)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            doMySearch(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            doMySearch(query)
        }
        return true
    }

    companion object {
        val myContacts: ContactsList = ContactsList()
    }
}