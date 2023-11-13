package com.example.geoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

private const val EXTRA_ANSWER = "com.example.geoquiz.answer"
private const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_is_shown"

class HelpActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var text: TextView
    private var answer: Boolean = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        answer = intent.getBooleanExtra(EXTRA_ANSWER, false)

        button = findViewById<Button>(R.id.button_help)
        text = findViewById<TextView>(R.id.textView)

        button.setOnClickListener {
            showAnswer()
            setAnswerShownResult(true)
        }
    }

    private fun showAnswer(){
        var answerTextId = if (answer){
            R.string.button_true
        }else{
            R.string.button_false
        }
        text.setText(answerTextId)
    }

    private fun setAnswerShownResult(answer_is_shown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, answer_is_shown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object{
        fun newIntent(pakageContext: Context, answer: Boolean): Intent{
            return Intent(pakageContext, HelpActivity::class.java).apply{
                putExtra(EXTRA_ANSWER, answer)
            }
        }
    }
}