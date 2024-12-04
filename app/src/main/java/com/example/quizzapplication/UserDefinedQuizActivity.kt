package com.example.quizzapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.example.quizzapplication.Quiz.QuizClass


class UserDefinedQuizActivity : AppCompatActivity() {

    private var amount = 10
    private var category: Int? = null
    private var difficulty: String? = null
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_defined_quiz)
        handleSeekBar()

        val categoryList = Constants.getCategoryStringArray()
        val categorySpinner: Spinner = findViewById(R.id.categorySpinner)
        categorySpinner.adapter = getSpinnerAdapter(categoryList)

        val difficultySpinner: Spinner = findViewById(R.id.difficultySpinner)
        difficultySpinner.adapter = getSpinnerAdapter(Constants.difficultyList)

        val typeSpinner: Spinner = findViewById(R.id.typeSpinner)
        typeSpinner.adapter = getSpinnerAdapter(Constants.typeList)

        handleCategorySpinner()
        handleDifficultySpinner()
        handleTypeSpinner()

        val quizClass = QuizClass(this)
        val startCustomQuizButton: Button = findViewById(R.id.startquiz)
        startCustomQuizButton.setOnClickListener {
            quizClass.getQuizList(amount, category, difficulty, type)
        }
    }

    private fun handleSeekBar() {
        val seekBarAmount: SeekBar = findViewById(R.id.seekBarAmount)
        val tvAmount: TextView = findViewById(R.id.tvAmount)

        seekBarAmount.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                amount = progress
                val text = "Amount: $progress"
                tvAmount.text = text
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Nothing to do here
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Nothing to do here
            }
        })
    }

    private fun handleCategorySpinner() {
        val categorySpinner: Spinner = findViewById(R.id.categorySpinner)
        categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    category = if (position == 0)
                        null
                    else
                        position + 8
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Nothing to do here
                }
            }
    }

    private fun handleDifficultySpinner() {
        val difficultySpinner: Spinner = findViewById(R.id.difficultySpinner)
        difficultySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                difficulty = when (position) {
                    0 -> null
                    1 -> "easy"
                    2 -> "medium"
                    else -> "hard"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Nothing to do here
            }
        }
    }

    private fun handleTypeSpinner() {
        val typeSpinner: Spinner = findViewById(R.id.typeSpinner)
        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                type = when (position) {
                    0 -> null
                    1 -> "multiple"
                    else -> "boolean"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Nothing to do here
            }
        }
    }

    private fun getSpinnerAdapter(list: List<String>): SpinnerAdapter {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }
}