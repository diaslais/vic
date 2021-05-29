package com.laisd.elevator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var txtFloor: TextView
    private lateinit var txtNumberOfPeople: TextView
    private lateinit var layoutTxtInput: TextInputLayout
    private lateinit var edtTxtInput: TextInputEditText
    private lateinit var btnGoToFloor: Button
    private lateinit var btnEnter: Button
    private lateinit var btnLeave: Button
    private val elevator = Elevator(0, mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtFloor = findViewById(R.id.txtFloor)
        txtNumberOfPeople = findViewById(R.id.txtNumberOfPeople)
        layoutTxtInput = findViewById(R.id.layoutTxtInput)
        edtTxtInput = findViewById(R.id.edtTxtInput)
        btnGoToFloor = findViewById(R.id.btnGoToFloor)
        btnEnter = findViewById(R.id.btnEnter)
        btnLeave = findViewById(R.id.btnLeave)

        setOnClicks()

        txtNumberOfPeople.text = getString(R.string.number_people, elevator.getNumberOfPeople())
        setFloor(elevator)
    }

    private fun setFloor(elevator: Elevator) {
        if (elevator.currentFloor == 0) {
            txtFloor.text = getString(R.string.ground_floor)
        } else {
            txtFloor.text = getString(R.string.current_floor, elevator.currentFloor)
        }
    }

    private fun addPerson(elevator: Elevator, person: Person){
        if (person.enter(elevator)) {
            txtNumberOfPeople.text = getString(R.string.number_people, elevator.getNumberOfPeople())
        } else {
            val toast = Toast.makeText(applicationContext, getString(R.string.elevator_full), Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun removePerson(elevator: Elevator, person: Person){
        if (person.leave(elevator)) {
            txtNumberOfPeople.text = getString(R.string.number_people, elevator.getNumberOfPeople())
        } else {
            val toast = Toast.makeText(applicationContext, getString(R.string.elevator_empty), Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun setOnClicks() {
        btnGoToFloor.setOnClickListener(this)
        btnEnter.setOnClickListener(this)
        btnLeave.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnGoToFloor -> {
                if (validateField(edtTxtInput, layoutTxtInput)) {
                    val destinationFloor = edtTxtInput.text.toString().toInt()
                    elevator.goToFloor(destinationFloor)
                    setFloor(elevator)
                }
            }
            R.id.btnEnter -> {
                addPerson(elevator, Person())
            }
            R.id.btnLeave -> {
                removePerson(elevator, Person())
            }
        }
    }

    private fun validateField(field: TextInputEditText, layout: TextInputLayout): Boolean {
        var notEmpty = true
        if (field.text.toString().isEmpty()) {
            notEmpty = false
            layout.error = getString(R.string.insert_floor)
        } else if (field.text.toString().toInt() < 0) {
            notEmpty = false
            layout.error = getString(R.string.floor_negative)
        } else if (field.text.toString().toInt() > 15){
            notEmpty = false
            layout.error = getString(R.string.floor_max)
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