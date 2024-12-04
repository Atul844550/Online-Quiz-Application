package com.example.quizzapplication.objects

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.quizzapplication.R

object Base {

    fun showProgressBar(context:Context): Dialog
    {
        val dialogue = Dialog(context)
        dialogue.setContentView(R.layout.loadingprogressbar)
        dialogue.show()

        return dialogue
    }

    fun showToast(context: Context, msg:String)
    {
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
    }
}


