package com.laisd.mycontacts

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    lateinit var contactsListRecyclerview: RecyclerView
    lateinit var inputLayoutName: TextInputLayout
    lateinit var inputEdtName: TextInputEditText
    lateinit var inputLayoutCell: TextInputLayout
    lateinit var inputEdtCell: TextInputEditText
    lateinit var inputLayoutComplement: TextInputLayout
    lateinit var inputEdtComplement: TextInputEditText
    lateinit var radioPersonal: RadioButton
    lateinit var radioWork: RadioButton
    lateinit var btnSave: Button
    lateinit var searchView: SearchView
    lateinit var radioGroup: RadioGroup
    private val myContacts = ContactsList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        inputEdtComplement.hint = getString(R.string.reference)

        val checkedRadioButtonId = radioGroup.checkedRadioButtonId

        makeRecyclerView(myContacts.getContacts())

        btnSave.setOnClickListener {
            if (validateField(inputEdtName, inputLayoutName) &&
                    validateField(inputEdtCell, inputLayoutCell) &&
                    validateField(inputEdtComplement, inputLayoutComplement)) {
                if (checkedRadioButtonId.equals(radioPersonal.id)) {
                    myContacts.saveContact(
                            Personal(
                                    inputEdtName.text.toString(),
                                    inputEdtCell.text.toString(),
                                    inputEdtComplement.text.toString()
                            )
                    )
                    makeRecyclerView(myContacts.getContacts())
                } else if (checkedRadioButtonId.equals(radioWork.id)) {
                    myContacts.saveContact(
                            Work(
                                    inputEdtName.text.toString(),
                                    inputEdtCell.text.toString(),
                                    inputEdtComplement.text.toString()
                            )
                    )
                    makeRecyclerView(myContacts.getContacts())
                }
                inputEdtName.text = null
                inputEdtCell.text = null
                inputEdtComplement.text = null
            }
        }
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.radioPersonal ->
                    if (checked) {
                        inputEdtComplement.hint = getString(R.string.reference)
                        inputEdtComplement.inputType = InputType.TYPE_CLASS_TEXT
                    }
                R.id.radioWork ->
                    if (checked) {
                        inputEdtComplement.hint = getString(R.string.email)
                        inputEdtComplement.inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
                    }
            }
        }
    }

    private fun makeRecyclerView(list: MutableList<Contact>) {
        contactsListRecyclerview.layoutManager = LinearLayoutManager(this)
        val contactsListAdapter = ContactsListAdapter(list)
        contactsListRecyclerview.adapter = contactsListAdapter
    }

    private fun bindViews() {
        contactsListRecyclerview = findViewById(R.id.recyclerViewContacts)
        inputLayoutName = findViewById(R.id.inputLayoutName)
        inputEdtName = findViewById(R.id.inputEdtName)
        inputLayoutCell = findViewById(R.id.inputLayoutCell)
        inputEdtCell = findViewById(R.id.inputEdtCell)
        inputLayoutComplement = findViewById(R.id.inputLayoutComplement)
        inputEdtComplement = findViewById(R.id.inputEdtComplement)
        radioPersonal = findViewById(R.id.radioPersonal)
        radioWork = findViewById(R.id.radioWork)
        btnSave = findViewById(R.id.btnSave)
        searchView = findViewById(R.id.searchView)
        radioGroup = findViewById(R.id.radioGroup)
    }

    private fun validateField(field: TextInputEditText, layout: TextInputLayout): Boolean {
        var notEmpty = true
        if (field.text.toString().isEmpty()) {
            notEmpty = false
            layout.error = getString(R.string.empty_field)
        }
        for (contact in myContacts.getContacts()) {
            if (contact.phoneNumber.equals(field.text.toString())) {
                val toast = Toast.makeText(this, getString(R.string.already_exists), Toast.LENGTH_SHORT)
                toast.show()
                notEmpty = false
            }
        }
        field.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                layout.error = null
            }
        })
        return notEmpty
    }

    private fun doMySearch(query: String) {
        val filteredList = mutableListOf<Contact>()
        for (contact in myContacts.getContacts()){
            if (contact.name.startsWith(query)) {
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
}