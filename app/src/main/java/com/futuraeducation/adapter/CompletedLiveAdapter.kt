package com.futuraeducation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import com.futuraeducation.fragment.CompletedLiveAdapterListener
import com.futuraeducation.model.onBoarding.CompletedSession
import com.futuraeducation.utils.Utils
import kotlinx.android.synthetic.main.row_upcoming.view.*

class CompletedLiveAdapter(
    val context: Context,
    private val completedLive: List<CompletedSession>,
    private val listener: CompletedLiveAdapterListener
) : RecyclerView.Adapter<CompletedLiveAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.row_upcoming, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val studyItem = completedLive[position]
        holder.itemView.live_name.text = studyItem.courseName
        holder.itemView.live_start_date.text = Utils.getDateTime(studyItem.updatedAt!!)

        holder.itemView.setOnClickListener {
            listener.onClicked(studyItem)
        }
/*        val completedLive = completedLive[position]
        holder.itemView.subjectTxt.text = completedLive.courseName

        holder.itemView.cardComplete.setOnClickListener {
            listener.onClicked(completedLive)
        }

        when(completedLive.courseName){
            "CHEMISTRY"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_color_chem)
            }
            "PHYSICS"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_color_phy)
            }
            "BIOLOGY"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_color_bio)
            }
            "TAMIL"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_tamil)
            }
            "URDU"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_urdu)
            }
            "SOCIAL SCIENCE"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_social)
            }
            "MATHEMATICS"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_color_math)
            }
            "ACCOUNTANCY"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_accoutancy)
            }
            "ENGLISH"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_english)
            }
            "HINDI"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_hindi)
            }
            "SCIENCE"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_science)
            }
            "KANNADA"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_kannada)
            }
            "HISTORY"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_history)
            }
            "ECONOMICS"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_economics)
            }
            "COMPUTER SCIENCE"->{
                holder.itemView.subjectimg.setImageResource(R.drawable.ic_comp_sci)
            }
        }*/

    }

    override fun getItemCount(): Int {
        return completedLive.size
    }
}