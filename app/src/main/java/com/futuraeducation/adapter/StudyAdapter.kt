package com.futuraeducation.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.GlideApp
import com.futuraeducation.R
import com.futuraeducation.model.Data
import com.futuraeducation.utils.Utils
import kotlinx.android.synthetic.main.row_study.view.*
import kotlinx.android.synthetic.main.row_upcoming_live.view.subject

class StudyAdapter(
    val context: Context,
    private val studyItem: List<Data>
) : RecyclerView.Adapter<StudyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.row_study, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val studyItem = studyItem[position]
        holder.itemView.subject.text = studyItem.subject.courseName
        holder.itemView.lesson.text = studyItem.topicName
        holder.itemView.date.text = Utils.getDateValue(studyItem.startDateTime)
        holder.itemView.backgroundLayout.setBackgroundColor(context.getColor(R.color.caribbean_green))
        GlideApp.with(context).load(R.drawable.mathematics).into(holder.itemView.studyImg)
        holder.itemView.setOnClickListener {
            if (Utils.isPackageInstalled(context, "us.zoom.videomeetings")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(studyItem.url))
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                }
                Toast.makeText(context, "Zoom Application", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please Install Zoom Application", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun getItemCount(): Int {
        return studyItem.size
    }
}