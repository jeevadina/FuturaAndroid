package com.futuraeducation.adapter.practice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import com.futuraeducation.adapter.SubjectClickListener
import com.futuraeducation.model.Datum
import kotlinx.android.synthetic.main.practice_items.view.*

class PracticeAdapter(
    val context: Context,
    val subjects: ArrayList<Datum>,
    val batchIds: String,
    var subjectClickListener: SubjectClickListener
) :
    RecyclerView.Adapter<PracticeAdapter.ViewHolder>() {

    private var position = 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.practice_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = subjects[position]
        holder.itemView.practiceSubjectTxt.text = items.courseName
        holder.itemView.practiceLyt.setOnClickListener {
            this.position = holder.position
            notifyDataSetChanged()
            subjectClickListener.onSubjectClicked(items.id.toString(),batchIds,items.courseName.toString())
        }

        if (this.position==position){
            holder.itemView.practiceLyt.setBackgroundResource(R.drawable.practice_card_selected)
        }else{
            holder.itemView.practiceLyt.setBackgroundResource(R.drawable.practice_card_bg)
        }

/*
        when(items.courseName){
            "CHEMISTRY"->{
                holder.subjectImg.setImageResource(R.drawable.ic_chem_v1)
            }
            "PHYSICS"->{
                holder.subjectImg.setImageResource(R.drawable.ic_phy_v1)
            }
            "BIOLOGY"->{
                holder.subjectImg.setImageResource(R.drawable.ic_bio_v1)
            }
            "TAMIL"->{
                holder.subjectImg.setImageResource(R.drawable.ic_tamil)
            }
            "URDU"->{
                holder.subjectImg.setImageResource(R.drawable.ic_urdu)
            }
            "SOCIAL SCIENCE"->{
                holder.subjectImg.setImageResource(R.drawable.ic_social)
            }
            "MATHEMATICS"->{
                holder.subjectImg.setImageResource(R.drawable.ic_math)
            }
            "ACCOUNTANCY"->{
                holder.subjectImg.setImageResource(R.drawable.ic_accoutancy)
            }
            "ENGLISH"->{
                holder.subjectImg.setImageResource(R.drawable.ic_english)
            }
            "HINDI"->{
                holder.subjectImg.setImageResource(R.drawable.ic_hindi)
            }
            "SCIENCE"->{
                holder.subjectImg.setImageResource(R.drawable.ic_science)
            }
            "KANNADA"->{
                holder.subjectImg.setImageResource(R.drawable.ic_kannada)
            }
            "HISTORY"->{
                holder.subjectImg.setImageResource(R.drawable.ic_history)
            }
            "ECONOMICS"->{
                holder.subjectImg.setImageResource(R.drawable.ic_economics)
            }
            "COMPUTER SCIENCE"->{
                holder.subjectImg.setImageResource(R.drawable.ic_computer_v1)
            }
        }
*/

    }

    override fun getItemCount(): Int {
        return subjects.size
    }
}