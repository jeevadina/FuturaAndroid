package com.futuraeducation.adapter.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import com.futuraeducation.adapter.TestClickListener
import com.futuraeducation.model.onBoarding.AttemptedTest
import com.futuraeducation.utils.Utils
import kotlinx.android.synthetic.main.row_attempted_test.view.*

class AttemptedTestAdapter(
    val context: Context,
    private val scheduledTestItems: List<AttemptedTest>,
    private var testClickListener: TestClickListener
) : RecyclerView.Adapter<AttemptedTestAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.row_attempted_test, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val scheduledTest = scheduledTestItems[position]
        holder.itemView.testName.text = scheduledTest.name
        holder.itemView.date.text = Utils.getDateValue(scheduledTest.publishDate)
        holder.itemView.backgroundColor.setBackgroundColor(context.getColor(R.color.carolina_blue))
        if (scheduledTest.completeStatus == "completed") {
            holder.itemView.review.visibility = View.GONE
            holder.itemView.result.visibility = View.GONE
            holder.itemView.submit.visibility = View.VISIBLE
        } else {
            holder.itemView.review.visibility = View.VISIBLE
            holder.itemView.result.visibility = View.VISIBLE
            holder.itemView.submit.visibility = View.GONE
        }
        holder.itemView.result.setOnClickListener {
            testClickListener.onResultClicked(
                scheduledTest.totalAttempts,
                scheduledTest.studentId,
                scheduledTest.testPaperId
            )
        }
        holder.itemView.review.setOnClickListener {
            testClickListener.onReviewClicked(
                scheduledTest
            )
        }
        holder.itemView.submit.setOnClickListener {
            testClickListener.onResultClicked(scheduledTest.testPaperId)
        }
    }

    override fun getItemCount(): Int {
        return scheduledTestItems.size
    }
}