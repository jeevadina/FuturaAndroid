package com.futuraeducation.practiceTest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.futuraeducation.R
import com.futuraeducation.adapter.AnswerClickListener
import com.futuraeducation.model.AnswerChooseItem
import com.futuraeducation.model.SectionQuestion
import kotlinx.android.synthetic.main.row_question_list.view.*


class ReviewAdapter(
    private val mContext: Context,
    private val questionItems: List<SectionQuestion?>,
    private val answerClickListener: AnswerClickListener,
    private val isReview: Boolean
) :
    PagerAdapter() {

    override fun getCount(): Int {
        return questionItems.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView =
            LayoutInflater.from(mContext).inflate(R.layout.row_question_list, container, false)
        val item = questionItems[position]

        itemView.questionNumber.text = "Question: " + (position + 1)

//        val question = item?.question?.replace("\n", "")?.replace("<p class=\\\"p4\\\">", "")
//        itemView.question.apply {
//            textZoom = 60
//            textColor = Color.GREEN.toString()
//            textAlign = TextAlign.LEFT
//            text = question
//        }
        itemView.question.loadData(item!!.question!!,"text/html", "UTF-8")

        val answerChooseItem = ArrayList<AnswerChooseItem>()

        answerChooseItem.add(AnswerChooseItem(item?.optionA?.replace("\n", "")))
        answerChooseItem.add(AnswerChooseItem(item?.optionB?.replace("\n", "")))
        answerChooseItem.add(AnswerChooseItem(item?.optionC?.replace("\n", "")))
        answerChooseItem.add(AnswerChooseItem(item?.optionD?.replace("\n", "")))
        itemView.answerChoose.adapter =
            AnswerChooseAdapter(
                mContext,
                answerChooseItem,
                item?.submittedAnswered,
                item?.correctAnswer,
                answerClickListener,
                position,
                isReview
            )
        container.addView(itemView)
        return itemView
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {}

}