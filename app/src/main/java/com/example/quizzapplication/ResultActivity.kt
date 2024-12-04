package com.example.quizzapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Fetch data passed from quiz activity
        val totalQuestions = intent.getIntExtra("totalQuestions", 0)
        val correctAnswers = intent.getIntExtra("correctAnswerCount", 0)


        val incorrectAnswers = totalQuestions - correctAnswers

        // Set data to UI components
        val tvScore: TextView = findViewById(R.id.tv_score)
        val tvCorrectAnswers: TextView = findViewById(R.id.tv_correct_answers)
        val tvIncorrectAnswers: TextView = findViewById(R.id.tv_incorrect_answers)

        tvScore.text = "Score: $correctAnswers/$totalQuestions"
        tvCorrectAnswers.text = "Correct Answers: $correctAnswers"
        tvIncorrectAnswers.text = "Incorrect Answers: $incorrectAnswers"


        val btnRetakeQuiz: Button = findViewById(R.id.btn_retake_quiz)
        val btnExit: Button = findViewById(R.id.btn_exit)

        btnRetakeQuiz.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnExit.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes") { _, _ ->
                    finishAffinity() // it Closes all activities and exits the app
                }
                .setNegativeButton("No", null) // Dismiss the dialog
                .show()
        }
    }
}
