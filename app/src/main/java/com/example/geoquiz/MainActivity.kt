package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var buttonTrue: Button
    private lateinit var buttonFalse: Button
    private lateinit var buttonNext: Button
    private lateinit var label: TextView
    private lateinit var counter: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var questionNumber = 0
    private var isAnswered = false
    private var rightAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        setContentView(R.layout.activity_main)

        buttonTrue = findViewById<Button>(R.id.button_true)
        buttonFalse = findViewById<Button>(R.id.button_false)
        buttonNext = findViewById<Button>(R.id.button_next)
        updateButtons()

        label = findViewById<TextView>(R.id.label)
        counter = findViewById<TextView>(R.id.counter)
        updateQuestion()

        buttonTrue.setOnClickListener {
            isAnswered = true
            checkAnswer(true)
            updateButtons()
        }
        buttonFalse.setOnClickListener {
            isAnswered = true
            checkAnswer(false)
            updateButtons()
        }
        buttonNext.setOnClickListener {
            if (questionNumber + 1 == questionBank.size) {
                Toast.makeText(this, "верно $rightAnswers из ${questionBank.size}", Toast.LENGTH_SHORT).show()
            } else {
                nextQuestion()
            }
        }

        label.setOnClickListener {
            if (isAnswered){
                nextQuestion()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("questionNumber", questionNumber)
        outState.putInt("rightAnswers", rightAnswers)
        outState.putBoolean("isAnswered", isAnswered)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        questionNumber = savedInstanceState.getInt("questionNumber")
        Log.d(TAG, "onRestoreInstanceState $questionNumber")
        updateQuestion()

        rightAnswers = savedInstanceState.getInt("rightAnswers")

        isAnswered = savedInstanceState.getBoolean("isAnswered")
        updateButtons()
    }

    private fun nextQuestion() {
        questionNumber = (questionNumber + 1) % questionBank.size
        updateQuestion()
        isAnswered = false
        updateButtons()

    }

    private fun updateQuestion(){
        counter.setText("${questionNumber + 1} / ${questionBank.size}")
        val questionTextResId = questionBank[questionNumber].textResId
        label.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val messageResId: Int
        if (userAnswer == questionBank[questionNumber].answer){
            messageResId = R.string.correct_toast
            rightAnswers ++
        } else{
            messageResId = R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun setButtonVisibility(button: Button, visibility: Boolean){
        button.visibility = if (visibility){
            (com.google.android.material.R.integer.mtrl_view_visible)
        } else {
            (com.google.android.material.R.integer.mtrl_view_invisible)
        }
    }

    private fun updateButtons(){
        setButtonVisibility(buttonTrue, !isAnswered)
        setButtonVisibility(buttonFalse, !isAnswered)
        setButtonVisibility(buttonNext, isAnswered)
        if ( questionNumber + 1 == questionBank.size){
            buttonNext.setText(R.string.button_finish)
        }
    }
}