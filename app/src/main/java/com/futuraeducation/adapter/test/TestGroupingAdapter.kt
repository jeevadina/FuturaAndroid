package com.futuraeducation.adapter.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import kotlinx.android.synthetic.main.activity_test_group.view.*

class TestGroupingAdapter(
    val context: Context,
    val subjects: ArrayList<String>,
) :
    RecyclerView.Adapter<TestGroupingAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_test_group, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = subjects[position]
        holder.itemView.subjectTxt.text = items
    }

    override fun getItemCount(): Int {
        return subjects.size
    }
}