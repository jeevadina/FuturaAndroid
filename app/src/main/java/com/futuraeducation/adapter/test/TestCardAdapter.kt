package com.futuraeducation.adapter.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import kotlinx.android.synthetic.main.test_item_view.view.*

class TestCardAdapter(
    private val scheduledTestItems: Array<String>,
) : RecyclerView.Adapter<TestCardAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.test_item_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scheduledTest = scheduledTestItems[position]
        holder.itemView.subjectTxt.text = scheduledTest

        when(scheduledTest){
            "Chapter"-> holder.itemView.subjectimg.setImageResource(R.drawable.ic_chapter_v1)
            "Full Length"-> holder.itemView.subjectimg.setImageResource(R.drawable.ic_full_length)
            "Mock"-> holder.itemView.subjectimg.setImageResource(R.drawable.ic_mock_v1)
            "Section"-> holder.itemView.subjectimg.setImageResource(R.drawable.ic_section_v1)
            "Attempted"-> holder.itemView.subjectimg.setImageResource(R.drawable.ic_attempted_test)
            "Section"-> holder.itemView.subjectimg.setImageResource(R.drawable.ic_unattempted)
        }
    }

    override fun getItemCount(): Int {
        return scheduledTestItems.size
    }
}