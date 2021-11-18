package com.futuraeducation.practiceTest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import com.futuraeducation.model.PracticeSubjectItem
import kotlinx.android.synthetic.main.row_practice_subject_list.view.*


class PracticeSubjectAdapter(
    val context: Context,
    private val practiceSubjectItems: ArrayList<PracticeSubjectItem>
) : RecyclerView.Adapter<PracticeSubjectAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_practice_subject_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val practiceSubject = practiceSubjectItems[position]
        holder.itemView.subject.text = practiceSubject.subject
        holder.itemView.progressValue.text = practiceSubject.progressValue.toString() + "%"
        holder.itemView.practiceProgressBar.progress = practiceSubject.progressValue.toInt()

        val practiceTopicAdapter = PracticeTopicAdapter(context,practiceSubject.practiceTopicItem)
        holder.itemView.topicRecycler.adapter = practiceTopicAdapter
    }

    override fun getItemCount(): Int {
        return practiceSubjectItems.size
    }
}