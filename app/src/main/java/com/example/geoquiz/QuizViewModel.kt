package com.example.geoquiz

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Integer.max

private const val TAG = "QuizViewModel"

class QuizViewModel: ViewModel() {

    var questionNumber = 0
        private set
    var isAnswered = 0
        private set
    var rightAnswers = 0
        private set

    var helpCount = 0

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    val currentQuestion: Int
        get() = questionBank[questionNumber].textResId

    val currentAnswer: Boolean
        get() = questionBank[questionNumber].answer

    val questionCount: Int
        get() = questionBank.size

    fun checkAnswer(userAnswer: Boolean): Int {
        isAnswered ++

        val messageResId: Int
        if (userAnswer == questionBank[questionNumber].answer){
            messageResId = R.string.correct_toast
            rightAnswers ++
        } else{
            messageResId = R.string.incorrect_toast
        }

        return messageResId
    }

    fun nextQuestion(){
        questionNumber = (questionNumber + 1) % questionBank.size
    }

    fun prevQuestion(){
        questionNumber = max(0, (questionNumber - 1)) % questionBank.size
    }

}