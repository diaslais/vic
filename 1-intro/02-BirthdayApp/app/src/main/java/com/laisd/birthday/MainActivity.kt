package com.laisd.birthday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var date: EditText
    lateinit var gift: EditText
    lateinit var button: Button
    val list = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.edtName)
        date = findViewById(R.id.edtDate)
        gift = findViewById(R.id.edtGift)
        button = findViewById(R.id.btnSend)

        button.setOnClickListener {
            val numberOfDays = countDays()
            makeToast(numberOfDays)
        }
    }

    fun countDays(): Int {
        //calendar.time = data atual
        var calendar = Calendar.getInstance()
        val currentDate = calendar.time
        val currentYear = calendar.get(Calendar.YEAR)

        //pega a data de nascimento em string e formata em Date
        val format = SimpleDateFormat("dd/MM/yyyy")
        val birthDate: Date = format.parse(date.text.toString())

        calendar.time = birthDate
        val birthYear = calendar.get(Calendar.YEAR)

        val yearDiff = currentYear - birthYear
        calendar.add(Calendar.YEAR, yearDiff) //coloca em calendar a data de aniversário do ano atual

        val birthdayCurrentYear = calendar.time
        var nextBirthday: Date

        if (birthdayCurrentYear.before(currentDate)){ //se aniversário desse ano já foi
            calendar.add(Calendar.YEAR, 1)
            nextBirthday = calendar.time
        } else {
            nextBirthday = birthdayCurrentYear
        }

        //o ".time" do Calendar retorna um Date
        //o ".time" do Date retorna um Long (periodo em milisegundos)
        val days = TimeUnit.DAYS.convert(nextBirthday.time - currentDate.time, TimeUnit.MILLISECONDS).toInt()

        return days
    }

    fun makeToast(numberOfDays: Int) {
        val text = "Olá ${name.text}, faltam $numberOfDays dias para o seu aniversário! Espero que você ganhe um(a) ${gift.text}."
        addToList(text)
        val toast = Toast.makeText(applicationContext, list[0], Toast.LENGTH_LONG)
        toast.show()
    }

    fun addToList(text: String){
        list.add(text)
    }
}