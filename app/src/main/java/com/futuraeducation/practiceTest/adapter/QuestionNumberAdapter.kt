package com.futuraeducation.practiceTest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import com.futuraeducation.model.QuestionNumberItem
import com.futuraeducation.model.QuestionType
import com.futuraeducation.practiceTest.QuestionClickListener
import kotlinx.android.synthetic.main.row_question_number_list.view.*

class QuestionNumberAdapter(
    val context: Context,
    private var questionNumberItem: ArrayList<QuestionNumberItem>,
    var questionClick: QuestionClickListener
) : RecyclerView.Adapter<QuestionNumberAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_question_number_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val questionNumber = questionNumberItem[position]
        holder.itemView.questionNumber.text = questionNumber.questionNumber.toString()
        holder.itemView.setOnClickListener {
            questionClick.onQuestionClicked(position)
        }
        when (questionNumber.questionType) {
            QuestionType.ATTEMPT -> {
                holder.itemView.numberBackground.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.emerald
                    )
                )
                holder.itemView.questionNumber.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            }
            QuestionType.NOT_ATTEMPT -> {

                holder.itemView.numberBackground.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.light_goldenrod_yellow
                    )
                )
                holder.itemView.questionNumber.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.charcoal
                    )
                )
            }
            QuestionType.MARK_FOR_REVIEW -> {

                holder.itemView.numberBackground.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.bittersweet
                    )
                )
                holder.itemView.questionNumber.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            }
            else -> {
                holder.itemView.numberBackground.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.alice_blue
                    )
                )
                holder.itemView.questionNumber.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.charcoal
                    )
                )
            }
        }
    }

    fun setItems(questionNumberItem: ArrayList<QuestionNumberItem>) {
        this.questionNumberItem = questionNumberItem
    }

    override fun getItemCount(): Int {
        return questionNumberItem.size
    }
}