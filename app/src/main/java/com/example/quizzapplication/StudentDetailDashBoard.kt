package com.example.quizzapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzapplication.DataBase.StudentDatabaseHelper


class StudentDetailDashBoard : AppCompatActivity() {
    private lateinit var dbHelper: StudentDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail_dash_board)


        dbHelper = StudentDatabaseHelper(this)

        // Get references to views
        val nameInput: EditText = findViewById(R.id.name)
        val classInput: EditText = findViewById(R.id.Class)
        val schoolInput: EditText = findViewById(R.id.school)
        val cityInput: EditText = findViewById(R.id.city)
        val contactInput: EditText = findViewById(R.id.contact)
        val playButton: Button = findViewById(R.id.play)


        playButton.setOnClickListener {

            val nameinput = nameInput.text.toString().trim()
            val classinput = classInput.text.toString().trim()
            val schoolinput = schoolInput.text.toString().trim()
            val cityinput = cityInput.text.toString().trim()
            val contactinput = contactInput.text.toString().trim()


            if (nameinput.isEmpty()) {
                nameInput.error = "Name is required"
                nameInput.requestFocus()
                return@setOnClickListener
            }
            if (classinput.isEmpty()) {
                classInput.error = "Class is required"
                classInput.requestFocus()
                return@setOnClickListener
            }
            if (schoolinput.isEmpty()) {
                schoolInput.error = "School is required"
                schoolInput.requestFocus()
                return@setOnClickListener
            }
            if (cityinput.isEmpty()) {
                cityInput.error = "City is required"
                cityInput.requestFocus()
                return@setOnClickListener
            }
            if (contactinput.isEmpty() || contactinput.length < 10) {
                contactInput.error = "Mobile number must be 10 digits"
                contactInput.requestFocus()
                return@setOnClickListener
            }

            // Saving the data in the database
            val id = dbHelper.insertStudent(nameinput, classinput, schoolinput, cityinput, contactinput)
            if (id > 0) {
                Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show()


                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Names", nameinput)
                startActivity(intent)
                finish() // Close the current activity
            } else {
                Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

