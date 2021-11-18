package com.futuraeducation.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.GlideApp
import com.futuraeducation.R
import com.futuraeducation.database.model.VideoPlayedItem
import com.futuraeducation.model.VideoMaterial
import kotlinx.android.synthetic.main.row_played_video.view.*

class VideoPlayedAdapter(
    val context: Activity,
    val identifier: String,
    private val studyItems: MutableList<VideoPlayedItem> ?= null,
    private val videoMaterial: MutableList<VideoMaterial> ?= null
    , val callback: ActionCallback) : RecyclerView.Adapter<VideoPlayedAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.row_played_video, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (identifier.equals("0")) {
            val studyItem = studyItems?.get(position)
            holder.itemView.videoTitle.text = studyItem!!.videoTitle
            GlideApp.with(context).load(studyItem.logoImg).into(holder.itemView.videoImgs)
            holder.itemView.videoLayout.setOnClickListener {
                callback.onVideoClickListener(studyItem)
            }
        }else{
            val studyItem = videoMaterial
                ?.get(position)
            holder.itemView.videoTitle.text = studyItem!!.title
            GlideApp.with(context).load(studyItem.filePath).into(holder.itemView.videoImgs)
            holder.itemView.videoLayout.setOnClickListener {
                callback.onVideoClickListener1(studyItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (identifier.equals("0")){
            studyItems!!.size
        }else videoMaterial?.size!!
    }

    interface ActionCallback {
        fun onVideoClickListener(videoPlayedItem: VideoPlayedItem)
        fun onVideoClickListener1(videoPlayedItem: VideoMaterial)
    }
}