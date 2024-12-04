package com.example.quizzapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzapplication.CategoryStats
import com.example.quizzapplication.R
import com.example.quizzapplication.model.CategoryModel


class GridAdapter(private val items: List<CategoryModel>,
                  private val categoryStat:Map<String, CategoryStats>
) :
    RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.image.setImageResource(item.image)
        holder.tvCategoryName.text = item.name
        val count = categoryStat[item.id]?.total_num_of_questions
        holder.tvQuestionCount.text = count.toString() + " questions"

        holder.itemView.setOnClickListener {
            if (onClickListener!= null)
            {
                onClickListener!!.onClick(item.id.toInt())
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image:ImageView = itemView.findViewById(R.id.image)
        val tvCategoryName:TextView = itemView.findViewById(R.id.headingItems)
        val tvQuestionCount:TextView = itemView.findViewById(R.id.question)
    }

    fun setOnClickListener(onClickListener: OnClickListener)
    {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(id: Int)
    }
}