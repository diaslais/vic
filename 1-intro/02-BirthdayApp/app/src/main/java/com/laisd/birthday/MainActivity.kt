package com.laisd.birthday

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var date: EditText
    lateinit var gift: EditText
    lateinit var answer: TextView
    lateinit var button: Button
    val list = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.edtName)
        date = findViewById(R.id.edtDate)
        gift = findViewById(R.id.edtGift)
        answer = findViewById(R.id.txtAnswer)
        button = findViewById(R.id.btnSend)

        button.setOnClickListener {
            if (validateField(name) && validateField(date) && validateField(gift)) {
                val numberOfDays = countDays()
                addToList(numberOfDays)
                showAnswer()
            }
        }
    }

    fun countDays(): Long {
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
        val days = TimeUnit.DAYS.convert(nextBirthday.time - currentDate.time, TimeUnit.MILLISECONDS) + 1

        return days
    }

    fun showAnswer() {
        var textAnswer = ""
        for (text in list) {
            textAnswer += text
        }
        answer.text = textAnswer
    }

    fun addToList(numberOfDays: Long) {
        val text = "Faltam $numberOfDays dias para o aniversário de ${name.text} e o presente será um(a) ${gift.text}.\n\n"
        list.add(text)
    }

    fun validateField(field: EditText): Boolean {
        var notEmpty = true
        if (field.text.toString().isEmpty()) {
            field.error = "Preencha o campo"
            notEmpty = false
        }
        return notEmpty
    }
}