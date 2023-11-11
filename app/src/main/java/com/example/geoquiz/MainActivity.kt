package com.example.geoquiz

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

class MainActivity : AppCompatActivity() {

    private lateinit var buttonTrue: Button
    private lateinit var buttonFalse: Button
    private lateinit var buttonNext: Button
    private lateinit var buttonBack: Button
    private lateinit var label: TextView
    private lateinit var counter: TextView
    private lateinit var viewModel: QuizViewModel

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var questionNumber = 0
    private var isAnswered = 0
    private var rightAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        viewModel.questionNumber.observe(this, Observer{
            questionNumber = it
        })
        viewModel.isAnswered.observe(this, Observer{
            isAnswered = it
        })
        viewModel.rightAnswers.observe(this, Observer{
            rightAnswers = it
        })
        questionNumber = viewModel.questionNumber.value!!
        isAnswered = viewModel.isAnswered.value!!
        rightAnswers = viewModel.rightAnswers.value!!

        buttonTrue = findViewById<Button>(R.id.button_true)
        buttonFalse = findViewById<Button>(R.id.button_false)
        buttonNext = findViewById<Button>(R.id.button_next)
        buttonBack = findViewById<Button>(R.id.button_back)
        updateButtons()

        label = findViewById<TextView>(R.id.label)
        counter = findViewById<TextView>(R.id.counter)
        updateQuestion()

        buttonTrue.setOnClickListener {
            viewModel.isAnswered.value = viewModel.isAnswered.value!! + 1
//            isAnswered ++
            checkAnswer(true)
            updateButtons()
        }
        buttonFalse.setOnClickListener {
            viewModel.isAnswered.value = viewModel.isAnswered.value!! + 1
//            isAnswered ++
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

        buttonBack.setOnClickListener {
                prevQuestion()
        }

        label.setOnClickListener {
            if (isAnswered > questionNumber){
                nextQuestion()
            }
        }
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putInt("questionNumber", questionNumber)
//        outState.putInt("rightAnswers", rightAnswers)
//        outState.putInt("isAnswered", isAnswered)
//    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        questionNumber = savedInstanceState.getInt("questionNumber")
//        Log.d(TAG, "onRestoreInstanceState $questionNumber")
//        updateQuestion()
//
//        rightAnswers = savedInstanceState.getInt("rightAnswers")
//
//        isAnswered = savedInstanceState.getInt("isAnswered")
//        updateButtons()
//    }

    private fun nextQuestion() {
        viewModel.questionNumber.value = (viewModel.questionNumber.value!! + 1) % questionBank.size
//        questionNumber = (questionNumber + 1) % questionBank.size
        updateQuestion()
//        isAnswered = false
        updateButtons()
    }

    private fun prevQuestion() {
        viewModel.questionNumber.value = max(0, (viewModel.questionNumber.value!! - 1)) % questionBank.size
//        questionNumber = max(0, (questionNumber - 1) % questionBank.size)
        updateQuestion()
//        isAnswered = true
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
            viewModel.rightAnswers.value = viewModel.rightAnswers.value!! + 1
//            rightAnswers ++
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
        setButtonVisibility(buttonTrue, isAnswered <= questionNumber)
        setButtonVisibility(buttonFalse, isAnswered  <= questionNumber)
        setButtonVisibility(buttonNext, isAnswered  > questionNumber)
        if ( questionNumber + 1 == questionBank.size){
            buttonNext.setText(R.string.button_finish)
        } else {
            buttonNext.setText(R.string.button_next)
        }
    }
}