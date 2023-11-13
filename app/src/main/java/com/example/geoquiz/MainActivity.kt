package com.example.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.lang.Integer.max

private const val TAG = "MainActivity"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var buttonTrue: Button
    private lateinit var buttonFalse: Button
    private lateinit var buttonNext: Button
    private lateinit var buttonBack: Button
    private lateinit var buttonHelp: Button
    private lateinit var label: TextView
    private lateinit var counter: TextView
    private lateinit var viewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]

        findViews()
        setListeners()
    }

    private fun findViews(){
        buttonTrue = findViewById<Button>(R.id.button_true)
        buttonFalse = findViewById<Button>(R.id.button_false)
        buttonNext = findViewById<Button>(R.id.button_next)
        buttonBack = findViewById<Button>(R.id.button_back)
        buttonHelp = findViewById<Button>(R.id.button_help)
        updateButtons()

        label = findViewById<TextView>(R.id.label)
        counter = findViewById<TextView>(R.id.counter)
        updateQuestion()
    }

    private fun setListeners(){
        buttonTrue.setOnClickListener {
            checkAnswer(true)
            updateButtons()
        }
        buttonFalse.setOnClickListener {
            checkAnswer(false)
            updateButtons()
        }
        buttonNext.setOnClickListener {
            if (viewModel.questionNumber + 1 == viewModel.questionCount) {
                Toast.makeText(this, "верно ${viewModel.rightAnswers} из ${viewModel.questionCount}", Toast.LENGTH_SHORT).show()
            } else {
                nextQuestion()
            }
        }

        buttonBack.setOnClickListener {
            prevQuestion()
        }

        buttonHelp.setOnClickListener {
            val intent = HelpActivity.newIntent(this, viewModel.currentAnswer)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

        label.setOnClickListener {
            if (viewModel.isAnswered > viewModel.questionNumber){
                nextQuestion()
            }
        }
    }

    private fun nextQuestion() {
        viewModel.nextQuestion()
        updateQuestion()
        updateButtons()
    }

    private fun prevQuestion() {
        viewModel.prevQuestion()
        updateQuestion()
        updateButtons()
    }

    private fun updateQuestion(){
        counter.setText("${viewModel.questionNumber + 1} / ${viewModel.questionCount}")
        val questionTextResId = viewModel.currentQuestion
        label.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val messageResId = viewModel.checkAnswer(userAnswer)
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
        setButtonVisibility(buttonTrue, viewModel.isAnswered <= viewModel.questionNumber)
        setButtonVisibility(buttonFalse, viewModel.isAnswered <= viewModel.questionNumber)
        setButtonVisibility(buttonHelp, viewModel.isAnswered <= viewModel.questionNumber)
        setButtonVisibility(buttonNext, viewModel.isAnswered > viewModel.questionNumber)
        if ( viewModel.questionNumber + 1 == viewModel.questionCount){
            buttonNext.setText(R.string.button_finish)
        } else {
            buttonNext.setText(R.string.button_next)
        }
    }
}