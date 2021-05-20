package com.laisd.fibonacci

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    val sequence = mutableListOf(1, 1)
    lateinit var userInputLayout: TextInputLayout
    lateinit var userInputEdt: TextInputEditText
    lateinit var btnSend: Button
    lateinit var answer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userInputLayout = findViewById(R.id.layoutTxtPosition)
        userInputEdt = findViewById(R.id.edTxtPosition)
        btnSend = findViewById(R.id.btnSend)
        answer = findViewById(R.id.txtAnswer)

        btnSend.setOnClickListener {
            if (validadeField()) {
                val position = userInputEdt.text.toString().toInt() - 1
                getSequence(position)
                showAnswer()
            }
        }
    }

    fun getSequence(position: Int): Int {
        if (position == 0 || position == 1) return 1
        val positionValue = getSequence(position - 1) + getSequence(position - 2)
        if (!sequence.contains(positionValue)) sequence.add(positionValue)
        return positionValue
    }

    fun showAnswer() {
        var textAnswer = ""
        for (number in sequence) textAnswer += number.toString()
        answer.text = textAnswer
    }

    fun validadeField(): Boolean {
        var notEmpty = true
        if (userInputEdt.text.toString().isEmpty()) {
            userInputLayout.error = "Preencha o campo"
            notEmpty = false
        }
        userInputEdt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                userInputLayout.error = null
            }
        })
        return notEmpty
    }
}