package com.example.quizzapplication

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class QuizzActivity : AppCompatActivity() {

    private lateinit var questionList: ArrayList<QuizResult>
    private var position = 0
    private var allowPlaying = true
    private var timer: CountDownTimer? = null
    private var correctAnswerCount = 0

    private lateinit var tvTimer: TextView
    private lateinit var tvQuestionCount: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var option1: Button
    private lateinit var option2: Button
    private lateinit var option3: Button
    private lateinit var option4: Button
    private lateinit var btnNext: Button

    private var totalQuestions = 0

    private lateinit var correctAnswer: String
    private lateinit var optionList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz)

        // Initialize views
        tvTimer = findViewById(R.id.tv_timer)
        tvQuestionCount = findViewById(R.id.tv_question_count)
        tvQuestion = findViewById(R.id.tv_question)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)
        btnNext = findViewById(R.id.btn_next)

        // Initialize question list
        questionList = intent.getSerializableExtra("questionList") as ArrayList<QuizResult>
        totalQuestions = questionList.size
        updateQuestionCount()
        setQuestion()
        setOptions()
        startTimer()

        btnNext.setOnClickListener {
            onNextQuestion()
        }

        // Setup option click listeners
        val optionClickListener = View.OnClickListener { view ->
            if (allowPlaying) {
                timer?.cancel()
                val selectedButton = view as Button
                handleOptionSelection(selectedButton)
                allowPlaying = false
            }
        }

        option1.setOnClickListener(optionClickListener)
        option2.setOnClickListener(optionClickListener)
        option3.setOnClickListener(optionClickListener)
        option4.setOnClickListener(optionClickListener)
    }

    private fun updateQuestionCount() {
        tvQuestionCount.text = "Question ${position + 1}/${questionList.size}"
    }

    private fun setQuestion() {
        val currentQuestion = questionList[position]
        tvQuestion.text = Constants.decodeHtmlString(currentQuestion.question)
    }

    private fun setOptions() {
        val currentQuestion = questionList[position]
        val (correct, options) = Constants.getRandomOptions(
            currentQuestion.correct_answer,
            currentQuestion.incorrect_answers
        )
        correctAnswer = correct
        optionList = options

        // Safely assign options to buttons
        option1.text = optionList.getOrNull(0) ?: ""
        option2.text = optionList.getOrNull(1) ?: ""
        option3.text = optionList.getOrNull(2) ?: ""
        option4.text = optionList.getOrNull(3) ?: ""

        // Show or hide options based on the question type
        if (currentQuestion.type == "multiple") {
            option3.visibility = View.VISIBLE
            option4.visibility = View.VISIBLE
        } else {
            option3.visibility = View.GONE
            option4.visibility = View.GONE
        }
    }


    private fun handleOptionSelection(selectedButton: Button) {
        val greenBg = ContextCompat.getDrawable(this, R.drawable.green_button_bg)
        val redBg = ContextCompat.getDrawable(this, R.drawable.red_button_bg)

        if (selectedButton.text == correctAnswer) {
            selectedButton.background = greenBg

            // counting the correct answer score

            correctAnswerCount++
        } else {
            selectedButton.background = redBg
            highlightCorrectAnswer()
        }
    }

    private fun highlightCorrectAnswer() {
        val greenBg = ContextCompat.getDrawable(this, R.drawable.green_button_bg)
        when (correctAnswer) {
            option1.text -> option1.background = greenBg
            option2.text -> option2.background = greenBg
            option3.text -> option3.background = greenBg
            option4.text -> option4.background = greenBg
        }
    }

    private fun onNextQuestion() {
        if (position < questionList.size - 1) {
            timer?.cancel()
            position++
            resetOptionBackgrounds()
            allowPlaying = true
            updateQuestionCount()
            setQuestion()
            setOptions()
            startTimer()
        } else {
            navigateToResultActivity()
        }
    }

    private fun resetOptionBackgrounds() {
        val defaultBg = ContextCompat.getDrawable(this, R.drawable.gray_button_bg)
        option1.background = defaultBg
        option2.background = defaultBg
        option3.background = defaultBg
        option4.background = defaultBg
    }

    private fun startTimer() {
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(remaining: Long) {
                tvTimer.text = "Timer: ${remaining / 1000}s"
            }

            override fun onFinish() {
                highlightCorrectAnswer()
                allowPlaying = false
            }
        }.start()
    }

    private fun navigateToResultActivity() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("correctAnswerCount", correctAnswerCount)
        intent.putExtra("totalQuestions", totalQuestions)
        startActivity(intent)
        finish()
    }
}


