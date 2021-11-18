package com.futuraeducation.adapter.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import com.futuraeducation.model.ListTopRanker
import kotlinx.android.synthetic.main.row_leaderboard_item.view.*

class LeaderboardItemAdapter(
    val context: Context,
    private val leaderboardItems: List<ListTopRanker?>,
) : RecyclerView.Adapter<LeaderboardItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.row_leaderboard_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val leaderboardItem = leaderboardItems[position]
        holder.itemView.txtCount.text = "${leaderboardItem!!.rank}"
        holder.itemView.txtName.text = leaderboardItem.studentName
        holder.itemView.txtAverage.text = "${leaderboardItem.totalMarks}"
    }

    override fun getItemCount(): Int {
        return leaderboardItems.size
    }
}