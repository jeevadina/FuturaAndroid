package com.futuraeducation.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import com.futuraeducation.model.Data
import com.futuraeducation.utils.Utils
import kotlinx.android.synthetic.main.row_upcoming.view.*

class UpcomingLiveAdapter(
    val context: Context,
    private val upcomingLiveItems: List<Data>
) : RecyclerView.Adapter<UpcomingLiveAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.row_upcoming, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val studyItem = upcomingLiveItems[position]
        holder.itemView.live_name.text = studyItem.subject.courseName
        holder.itemView.live_start_date.text = Utils.getDateTime(studyItem.startDateTime)
        //holder.itemView.live_start_date.text = Utils.getDateValue(studyItem.startDateTime)
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
        return upcomingLiveItems.size
    }
}