package com.futuraeducation.adapter.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import com.futuraeducation.model.TestResultsData
import kotlinx.android.synthetic.main.all_results_item.view.*

class AllResultsAdapter(
    val context: Context,
    private val scheduledTestItems: ArrayList<TestResultsData>,
) : RecyclerView.Adapter<AllResultsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.all_results_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scheduledTest = scheduledTestItems[position]
        holder.itemView.testNameTxt.text = scheduledTest.testName
        holder.itemView.scoreAnstxt.text = scheduledTest.score.toString()
        holder.itemView.highscoretxt.text = "Highscore :  " + scheduledTest.highestScore.toString()
        holder.itemView.rankAnstxt.text = scheduledTest.rankInTest.toString()
        holder.itemView.attemptTxt.text = "Attempt :  " + scheduledTest.attempt
    }

    override fun getItemCount(): Int {
        return scheduledTestItems.size
    }
}