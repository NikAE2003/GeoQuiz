package com.example.geoquiz

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HelpActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var text: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        button = findViewById<Button>(R.id.button_help)
        text = findViewById<TextView>(R.id.textView)



    }
}