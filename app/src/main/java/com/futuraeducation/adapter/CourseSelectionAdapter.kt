package com.futuraeducation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import kotlinx.android.synthetic.main.list_courses.view.*

class CourseSelectionAdapter(
    val context: Context,
    private val scheduledTestItems: ArrayList<String>?,
) : RecyclerView.Adapter<CourseSelectionAdapter.ViewHolder>() {

    private var row_index= ArrayList<Int>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_courses, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scheduledTest = scheduledTestItems?.get(position)
        holder.itemView.txt_course.text = scheduledTest
        holder.itemView.txt_course.setOnClickListener {
            if (row_index.contains(position)){
                row_index.remove(position)
            }else {
                row_index.add(position);
            }
            notifyDataSetChanged();
        }

        if (row_index.contains(position)){
            holder.itemView.txt_course.setBackgroundResource(R.drawable.ic_rectangle_blue)
            holder.itemView.txt_course.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ticks_img , 0)
            holder.itemView.txt_course.setPaddingRelative(0, 0, 20 , 0)
        }else{
            holder.itemView.txt_course.setBackgroundResource(R.drawable.ic_rectangle_new)
            holder.itemView.txt_course.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0 , 0)
        }
    }

    override fun getItemCount(): Int {
        scheduledTestItems.let {
            return scheduledTestItems!!.size
        }
    }
}