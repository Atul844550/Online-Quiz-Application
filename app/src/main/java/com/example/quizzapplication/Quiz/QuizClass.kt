package com.example.quizzapplication.Quiz

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzapplication.Constants
import com.example.quizzapplication.QuestionStats
import com.example.quizzapplication.QuizResponse
import com.example.quizzapplication.QuizzActivity
import com.example.quizzapplication.RETROFIT.QuestionStatsService
import com.example.quizzapplication.RETROFIT.QuizService
import com.example.quizzapplication.adapter.GridAdapter
import com.example.quizzapplication.objects.Base
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class QuizClass(private val context: Context) {

    fun getQuizList(amount:Int, category:Int?, difficulty:String?,type:String? )
    {
        if (Constants.isNetworkAvailable(context))
        {
            val pbDialog = Base.showProgressBar(context)
            val retrofit : Retrofit = Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()

            val service: QuizService = retrofit.create(QuizService::class.java)
            val dataCall: Call<QuizResponse> = service.getQuiz(
                amount,
                category,
                difficulty,
                type
            )

            dataCall.enqueue(object :Callback<QuizResponse>{
                override fun onResponse(
                    call: Call<QuizResponse>,
                    response: Response<QuizResponse>
                ) {
                    pbDialog.cancel()
                    if (response.isSuccessful)
                    {
                        val responseData: QuizResponse = response.body()!!
                        val questionList = ArrayList(responseData.results)
                        if(questionList.isNotEmpty()) {
                            val intent = Intent(context, QuizzActivity::class.java)
                            intent.putExtra("questionList", questionList)
                            context.startActivity(intent)
                        }
                        else
                        {
                            Base.showToast(context,"sorry currently " +
                                    "we don't have $amount question for this particular selection")
                            Log.e("debug",responseData.toString())
                        }
                    }
                    else
                    {
                        Base.showToast(context, "Error Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<QuizResponse>, t: Throwable) {
                    pbDialog.cancel()
                    Base.showToast(context, "CallBack Failure")
                }

            })
        }
        else
        {
            Base.showToast(context,"Network is not Available")
        }
    }

    fun setRecyclerView(recycleView:RecyclerView?)
    {
        if (Constants.isNetworkAvailable(context))
        {
            val pbDialog = Base.showProgressBar(context)
            val retrofit : Retrofit = Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()

            val service:QuestionStatsService = retrofit.create(QuestionStatsService::class.java)
            val dataCall:Call<QuestionStats> = service.getData()
            dataCall.enqueue(object :Callback<QuestionStats>{
                override fun onResponse(
                    call: Call<QuestionStats>,
                    response: Response<QuestionStats>
                ) {
                    pbDialog.cancel()
                    if (response.isSuccessful)
                    {
                        val questionStats:QuestionStats = response.body()!!
                        val categoryMap = questionStats.categories
                        val adapter = GridAdapter(Constants.getCategoryItemList(),categoryMap)
                        recycleView?.adapter = adapter
                        adapter.setOnClickListener(object :GridAdapter.OnClickListener{
                            override fun onClick(id: Int) {
                                getQuizList(10,id,null,null)
                            }

                        })

                    }
                    else
                    {
                        Base.showToast(context, "Error Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<QuestionStats>, t: Throwable) {
                    pbDialog.cancel()
                    Base.showToast(context,"Network is not Available")
                }

            })
        }
    }
}