package com.futuraeducation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import com.futuraeducation.model.MOCKTEST
import com.futuraeducation.utils.Utils.getDateTime
import kotlinx.android.synthetic.main.row_practice_test.view.*


class ScheduledTestAdapter(
    val context: Context,
    private val scheduledItems: List<MOCKTEST>,
    private var testClickListener: TestClickListener
) : RecyclerView.Adapter<ScheduledTestAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.row_practice_test, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scheduledTest = scheduledItems[position]
        holder.itemView.topicPractice.text = scheduledTest.testPaperVo?.name
        holder.itemView.noOfQuesTxt.text = scheduledTest.testPaperVo?.questionCount.toString() + " Question"
        holder.itemView.durationTxtPrac.text = getDateTime(scheduledTest.publishDate!!)
        holder.itemView.takeTestPrac.setOnClickListener {
            testClickListener.onTestClicked(true, scheduledTest)
        }
    }

    override fun getItemCount(): Int {
        return scheduledItems.size
    }

}