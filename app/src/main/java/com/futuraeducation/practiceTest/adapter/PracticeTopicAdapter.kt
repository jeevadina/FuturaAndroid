package com.futuraeducation.practiceTest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import com.futuraeducation.model.PracticeTopicItem
import kotlinx.android.synthetic.main.row_practice_topic_list.view.*


class PracticeTopicAdapter(
    val context: Context,
    private val practiceTopicItem: ArrayList<PracticeTopicItem>
) : RecyclerView.Adapter<PracticeTopicAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_practice_topic_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val practiceTopic = practiceTopicItem[position]
        holder.itemView.topic.text = practiceTopic.topic
        if (practiceTopic.isSelected) {
            holder.itemView.topic.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_check_circle,
                0
            )
            holder.itemView.topic.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.tea_green
                )
            )
        }else{
            holder.itemView.topic.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_add_circle,
                0
            )
            holder.itemView.topic.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.alice_blue
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return practiceTopicItem.size
    }
}