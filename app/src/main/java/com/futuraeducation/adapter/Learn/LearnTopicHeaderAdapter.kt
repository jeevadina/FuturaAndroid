package com.futuraeducation.adapter.Learn

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import com.futuraeducation.model.VideoMaterial

class LearnTopicHeaderAdapter(
    val context: Context,
    val subjects: List<VideoMaterial>?,
    val listener: VideoClickListener
) :
    RecyclerView.Adapter<LearnTopicHeaderAdapter.ViewHolder>() {

    var isDataAvailable = false

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chapternametxt = itemView.findViewById(R.id.videoName) as AppCompatTextView
        val noContTxt = itemView.findViewById(R.id.noContTxt) as AppCompatTextView
        val subTopicTitleCard = itemView.findViewById(R.id.subTopicTitleCard) as LinearLayout
        val viewDividerLearn = itemView.findViewById(R.id.viewDividerLearn) as View
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_sub_topic, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (isDataAvailable) {
            holder.chapternametxt.visibility = View.VISIBLE
            holder.noContTxt.visibility = View.GONE
            if (position == subjects!!.size-1){holder.viewDividerLearn.visibility = View.GONE}else holder.viewDividerLearn.visibility = View.VISIBLE
            holder.chapternametxt.text = subjects?.get(position)?.title
            holder.subTopicTitleCard.setOnClickListener {
                listener.onVideoSelected(subjects!!, position)
            }
        }else{
            holder.chapternametxt.visibility = View.GONE
            holder.viewDividerLearn.visibility = View.GONE
            holder.noContTxt.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return if (subjects?.size!!>0){
            isDataAvailable = true
            subjects?.size!!
        }else{
            isDataAvailable = false
            1
        }
    }
}

interface VideoClickListener{
    fun onVideoSelected(videoMaterial: List<VideoMaterial>,  position: Int)
}