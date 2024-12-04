package com.example.quizzapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizzapplication.FireBase.SignInActivity
import com.example.quizzapplication.Quiz.QuizClass
import com.example.quizzapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private var selectedImageUri: Uri? =null
    private val PICK_IMAGE=1

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    private var binding: ActivityMainBinding? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val rvCategoryList = binding?.recyclerviewlayout
        rvCategoryList?.layoutManager = GridLayoutManager(this,2)
        val quizClass = QuizClass(this)
        quizClass.setRecyclerView(rvCategoryList)

        binding?.userChoice?.setOnClickListener {

            startActivity(Intent(this,UserDefinedQuizActivity::class.java))

        }

        binding?.randomQuiz?.setOnClickListener {
            quizClass.getQuizList(10,null,null,null)

        }

        binding?.profileimage?.setOnClickListener{

            val intent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
        }

        // Set up Navigation Drawer
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        // ActionBarDrawerToggle to link DrawerLayout with ActionBar (Toolbar)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Enable Home button in ActionBar and link it to the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set navigation item selection listener
        navigationView.setNavigationItemSelectedListener(this)

        var studentName = intent.getStringExtra("Names")
        var studentprofileName= "Hello, ${studentName}"

        binding?.Studentprofilename?.text= studentprofileName

        val log:Button= findViewById(R.id.logout)
        val email: TextView = navigationView.getHeaderView(0).findViewById(R.id.nav_header_email)

        log.setOnClickListener{

            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "logout Succesfully", Toast.LENGTH_SHORT).show()
        }

        val fetchingEmail = intent.getStringExtra("userEmail") ?: "Welcome"  // Elvis operator
        email.text= fetchingEmail

    }

    // Handling item clicks in the navigation drawer
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Home -> {

                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Forwarding to Home Page", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_about -> {

                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                finish()

                Toast.makeText(this, "forwarding to About Page", Toast.LENGTH_SHORT).show()
            }

            R.id.ResultView -> {

                val intent = Intent(this, ResultActivity::class.java)
                startActivity(intent)
                finish()

                Toast.makeText(this, "forwarding to Result Page", Toast.LENGTH_SHORT).show()
            }

            R.id.help -> {

                val intent = Intent(this, HelpSupportActivity::class.java)
                startActivity(intent)
                finish()

                Toast.makeText(this, "forwarding to Help Page", Toast.LENGTH_SHORT).show()
            }

            R.id.logout -> {

                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()

                Toast.makeText(this, "logout Succesfully", Toast.LENGTH_SHORT).show()


            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }


    // Handle the ActionBar's home button click for toggling the navigation drawer
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Handle back press to close the navigation drawer if it's open
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode== PICK_IMAGE && resultCode== Activity.RESULT_OK){

            selectedImageUri=data?.data
            selectedImageUri?.let {

                val bitmap:Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, it)

                binding?.profileimage?.setImageBitmap(bitmap)
            }
        }
    }

}