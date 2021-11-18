package com.futuraeducation.practiceTest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.futuraeducation.R
import com.futuraeducation.adapter.AnswerClickListener
import com.futuraeducation.model.AnswerChooseItem
import kotlinx.android.synthetic.main.row_answer_choose_list.view.*
import kotlinx.android.synthetic.main.row_attempted_test.view.*
import kotlinx.android.synthetic.main.row_question_list.view.*


class AnswerChooseAdapter(
    val context: Context,
    private val answerChooseItem: ArrayList<AnswerChooseItem>,
    private val submittedAnswer: String?,
    private val correctAnswer: String? = "",
    private val answerClickListener: AnswerClickListener,
    private val questionPosition: Int,
    private val isReview: Boolean
) : RecyclerView.Adapter<AnswerChooseAdapter.ViewHolder>() {

    private var previousPosition = -1
    private var ansPosition = -1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_answer_choose_list, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val answerItem = answerChooseItem[position]
        if (answerItem.isSelected) previousPosition = position

        if(answerItem.answer != null) {
            val ans = answerItem.answer!!.replace("\n", "").replace("<p class=\\\"p4\\\">", "")
//            holder.itemView.mvTest.apply {
//                textZoom = 60
//                textColor = Color.GREEN.toString()
//                textAlign = TextAlign.LEFT
//                backgroundColor = "#EEF4FA"
//                text = ans
//            }
            holder.itemView.mvTest.loadData(answerItem.answer!!,"text/html", "UTF-8")
        }

        if (!isReview) {
            holder.itemView.setOnClickListener {
                if (!answerItem.isSelected) {
                    if (previousPosition > -1) {
                        answerChooseItem[previousPosition].isSelected = false
                        notifyItemChanged(previousPosition)
                    }
                    answerItem.isSelected = true
                    notifyItemChanged(position)
                    val answer: Char = (position + 97).toChar()
                    answerClickListener.onAnswerClicked(true, answer, questionPosition)
                } else {
                    answerItem.isSelected = false
                    notifyItemChanged(position)
                    answerClickListener.onAnswerClicked(false, '-', questionPosition)
                }
            }
            holder.itemView.ansView.setOnClickListener {
                if (!answerItem.isSelected) {
                    if (previousPosition > -1) {
                        answerChooseItem[previousPosition].isSelected = false
                        notifyItemChanged(previousPosition)
                    }
                    answerItem.isSelected = true
                    notifyItemChanged(position)
                    val answer: Char = (position + 97).toChar()
                    answerClickListener.onAnswerClicked(true, answer, questionPosition)
                } else {
                    answerItem.isSelected = false
                    notifyItemChanged(position)
                    answerClickListener.onAnswerClicked(false, '-', questionPosition)
                }
            }
        }
        if (!submittedAnswer.isNullOrEmpty()) {
            ansPosition = submittedAnswer[0].code.minus(97)
            if (ansPosition == position && previousPosition < 0) {
                previousPosition = position
                answerItem.isSelected = true
            }
        }

        if (isReview) {
            if (submittedAnswer.equals(
                    correctAnswer,
                    ignoreCase = true
                ) && position == ansPosition
            ) {
                holder.itemView.answer.setImageResource(R.drawable.ic_check_circle)
                holder.itemView.parentView.background.setTint(
                    ContextCompat.getColor(
                        context,
                        R.color.tea_green
                    )
                )
                holder.itemView.mvTest.setBackgroundColor(Color.parseColor("#D5FBD3"))

//                holder.itemView.mvTest.apply {
//                    backgroundColor = "#D5FBD3"
//                }
            } else if (position == ansPosition) {
                holder.itemView.answer.setImageResource(R.drawable.ic_close_outline)
                holder.itemView.parentView.background.setTint(
                    ContextCompat.getColor(
                        context,
                        R.color.pale_pink
                    )
                )
                holder.itemView.mvTest.setBackgroundColor(Color.parseColor("#FBD3D3"))
//                holder.itemView.mvTest.apply {
//                    backgroundColor = "#FBD3D3"
//                }
            } else {
                holder.itemView.answer.setImageResource(R.drawable.ic_circle_outline)
                holder.itemView.parentView.background.setTint(
                    ContextCompat.getColor(
                        context,
                        R.color.alice_blue
                    )
                )
                holder.itemView.mvTest.setBackgroundColor(Color.parseColor("#EEF4FA"))
//                holder.itemView.mvTest.apply {
//                    backgroundColor = "#EEF4FA"
//                }
            }
        } else {
            if (answerItem.isSelected) {
                holder.itemView.answer.setImageResource(R.drawable.ic_check_circle)
                holder.itemView.parentView.background.setTint(
                    ContextCompat.getColor(
                        context,
                        R.color.tea_green
                    )
                )
                holder.itemView.mvTest.setBackgroundColor(Color.parseColor("#D5FBD3"))
//                holder.itemView.mvTest.apply {
//                    backgroundColor = "#D5FBD3"
//                }
            } else {
                holder.itemView.answer.setImageResource(R.drawable.ic_circle_outline)
                holder.itemView.parentView.background.setTint(
                    ContextCompat.getColor(
                        context,
                        R.color.alice_blue
                    )
                )
                holder.itemView.mvTest.setBackgroundColor(Color.parseColor("#EEF4FA"))
//                holder.itemView.mvTest.apply {
//                    backgroundColor = "#EEF4FA"
//                }
            }
        }
    }

    override fun getItemCount(): Int {
        return answerChooseItem.size
    }
}