package com.futuraeducation.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import com.futuraeducation.doubt.DoubtClarificationActivity
import com.futuraeducation.model.ClarifiedDoubtItem
import kotlinx.android.synthetic.main.row_clarified_doubt.view.*

class ClarifiedDoubtAdapter(
    val context: Context,
    private val studyItems: ArrayList<ClarifiedDoubtItem>
) : RecyclerView.Adapter<ClarifiedDoubtAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.row_clarified_doubt, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val studyItem = studyItems[position]
        holder.itemView.titleName.text = studyItem.title
        holder.itemView.subTitle.text = studyItem.subTitle
        holder.itemView.date.text = studyItem.date
        holder.itemView.backgroundColor.setBackgroundColor(context.getColor(studyItem.color))

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DoubtClarificationActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return studyItems.size
    }
}