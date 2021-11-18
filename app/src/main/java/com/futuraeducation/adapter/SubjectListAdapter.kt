package com.futuraeducation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import com.futuraeducation.R
import com.futuraeducation.learn.LearnActivity
import com.futuraeducation.model.chapter.ChapterResponseData

class SubjectListAdapter(
    val context: Context,
    private val chaptersList: List<ChapterResponseData>,
    val batchId: String
) :
    RecyclerView.Adapter<SubjectListAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chapternametxt = itemView.findViewById(R.id.chapternametxt) as TextView
        val rlt_subj = itemView.findViewById(R.id.cardCircle) as MaterialCardView
        val txtIndex = itemView.findViewById(R.id.indexTxt) as AppCompatTextView
        val chapterDetails = itemView.findViewById(R.id.chapterDetails) as AppCompatTextView
        val chapterDetailsMaterials = itemView.findViewById(R.id.chapterDetailsMaterials) as AppCompatTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_subjectlist, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = chaptersList[position]
        holder.chapternametxt.text = (chaptersList[position].courseName)

        holder.txtIndex.text = if (position<=8){
            "0" + (position + 1)
        }else{
            "" + (position + 1)
        }

        holder.chapterDetails.text = data.topicMaterialResponses?.size.toString() + " Topics,"
        holder.chapterDetailsMaterials.text =  data.videoCount.toString() + " Materials"

        when{
            position.toString().last().toString().contains("0")->{
           /*     holder.rlt_subj.setBackgroundResource(R.color.card1)
                holder.rlt_subj.radius = 12.dpToPixels(context)*/
            }
            position.toString().last().toString().contains("1")->{
               // holder.rlt_subj.setCardBackgroundColor(R.color.card2)
            }
            position.toString().last().toString().contains("2")->{
               // holder.rlt_subj.setBackgroundResource(R.color.card3)
            }
            position.toString().last().toString().contains("3")->{
              //  holder.rlt_subj.setCardBackgroundColor(R.color.card4)
            }
            position.toString().last().toString().contains("4")->{
               // holder.rlt_subj.setCardBackgroundColor(R.color.card5)
            }
            position.toString().last().toString().contains("5")->{
                //holder.rlt_subj.setCardBackgroundColor(R.color.card6)
            }
            position.toString().last().toString().contains("6")->{
               // holder.rlt_subj.setCardBackgroundColor(R.color.card7)
            }
            position.toString().last().toString().contains("7")->{
               // holder.rlt_subj.setCardBackgroundColor(R.color.card8)
            }
            position.toString().last().toString().contains("8")->{
               // holder.rlt_subj.setCardBackgroundColor(R.color.card9)
            }
            position.toString().last().toString().contains("8")->{
                //holder.rlt_subj.setCardBackgroundColor(R.color.card10)
            }
            else->{
             //   holder.rlt_subj.setCardBackgroundColor(R.color.card11)
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, LearnActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("title", chaptersList[position].courseName)
            intent.putExtra("id", chaptersList[position].id)
            intent.putExtra("materials", Gson().toJson(data.topicMaterialResponses))
            intent.putExtra("batchID", batchId)
            intent.putExtra("position", (position+1).toString())
            Log.e("popPos", position.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return chaptersList.size
    }
}