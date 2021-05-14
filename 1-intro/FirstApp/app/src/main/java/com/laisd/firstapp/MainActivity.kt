package com.laisd.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var welcomeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = "Laís"

        welcomeText = findViewById(R.id.txtWelcome)

        welcomeText.text = setText(name)
    }

    fun setText (name: String): String {
        return "Bem vinda, $name!\nEste é o meu app."
    }
}