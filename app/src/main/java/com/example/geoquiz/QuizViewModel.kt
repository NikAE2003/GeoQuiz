package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel: ViewModel() {

    val questionNumber = MutableLiveData<Int>(0)
    val isAnswered = MutableLiveData<Int>(0)
    val rightAnswers = MutableLiveData<Int>(0)
    init {
        Log.d(TAG, "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}