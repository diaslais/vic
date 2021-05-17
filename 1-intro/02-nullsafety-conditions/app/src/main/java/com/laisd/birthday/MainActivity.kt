package com.laisd.birthday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var date: EditText
    lateinit var gift: EditText
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.edtName)
        date = findViewById(R.id.edtDate)
        gift = findViewById(R.id.edtGift)
        button = findViewById(R.id.btnSend)

        val numberOfDays = countDays()

        button.setOnClickListener {
            makeToast(numberOfDays)
        }
    }

    fun countDays(): Int {
        val days = 0
        //calcular
        return days
    }

    fun makeToast(numberOfDays: Int){
        val text = "Olá ${name.text}, faltam $numberOfDays dias para o seu aniversário! Espero que você ganhe um(a) ${gift.text}."
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_LONG)
        toast.show()
    }
}