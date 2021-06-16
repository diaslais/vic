package com.laisd.mycontacts

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.laisd.mycontacts.MainActivity.Companion.myContacts

class AddContactActivity : AppCompatActivity() {

    lateinit var inputLayoutName: TextInputLayout
    lateinit var inputEdtName: TextInputEditText
    lateinit var inputLayoutCell: TextInputLayout
    lateinit var inputEdtCell: TextInputEditText
    lateinit var inputLayoutComplement: TextInputLayout
    lateinit var inputEdtComplement: TextInputEditText
    lateinit var radioPersonal: RadioButton
    lateinit var radioWork: RadioButton
    lateinit var btnSave: Button
    lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        bindViews()

        inputEdtComplement.hint = getString(R.string.reference)

        btnSave.setOnClickListener {
            saveAndGoToMainActivity()
        }
    }

    private fun saveAndGoToMainActivity() {
        val checkedRadioButtonId = radioGroup.checkedRadioButtonId

        if (validateField(inputEdtName, inputLayoutName) &&
            validateField(inputEdtCell, inputLayoutCell) &&
            validateField(inputEdtComplement, inputLayoutComplement)) {
            if (checkedRadioButtonId.equals(radioPersonal.id)) {
                val personalContact = Personal (
                    inputEdtName.text.toString(),
                    inputEdtCell.text.toString(),
                    inputEdtComplement.text.toString()
                )
                myContacts.saveContact(personalContact)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else if (checkedRadioButtonId.equals(radioWork.id)) {
                val workContact = Work(
                    inputEdtName.text.toString(),
                    inputEdtCell.text.toString(),
                    inputEdtComplement.text.toString()
                )
                myContacts.saveContact(workContact)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun bindViews() {
        inputLayoutName = findViewById(R.id.inputLayoutName)
        inputEdtName = findViewById(R.id.inputEdtName)
        inputLayoutCell = findViewById(R.id.inputLayoutCell)
        inputEdtCell = findViewById(R.id.inputEdtCell)
        inputLayoutComplement = findViewById(R.id.inputLayoutComplement)
        inputEdtComplement = findViewById(R.id.inputEdtComplement)
        radioPersonal = findViewById(R.id.radioPersonal)
        radioWork = findViewById(R.id.radioWork)
        btnSave = findViewById(R.id.btnSave)
        radioGroup = findViewById(R.id.radioGroup)
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
}