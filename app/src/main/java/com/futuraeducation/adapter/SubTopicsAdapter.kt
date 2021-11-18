package com.futuraeducation.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.futuraeducation.R
import com.futuraeducation.learn.LearnVideoActivity
import com.futuraeducation.model.VideoMaterial
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import kotlinx.android.synthetic.main.row_sub_topics_video.view.*

class SubTopicsAdapter(
    val context: Context,
    private val subTopicItems: List<VideoMaterial>
) : RecyclerView.Adapter<SubTopicsAdapter.ViewHolder>() {

    lateinit var myPreferences: MyPreferences

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_sub_topics_video, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.videoName.text = subTopicItems[position].title
        holder.itemView.setOnClickListener {
            myPreferences = MyPreferences(context)
            myPreferences.setString(Define.VIDEO_DATA, Gson().toJson(subTopicItems))
            myPreferences.setInt(Define.VIDEO_POS, position)
            val intent = Intent(context, LearnVideoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return subTopicItems.size
    }
}