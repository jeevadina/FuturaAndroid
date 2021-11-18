package com.futuraeducation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.futuraeducation.R
import com.futuraeducation.model.PlansModel
import java.util.*


class PlanSelectionAdapter(
    private val models: ArrayList<PlansModel>,
    private val context: Context
) : PagerAdapter() {
    //    private var layoutInflater: LayoutInflater? = null
    override fun getCount(): Int {
        return models.size
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        layoutInflater = LayoutInflater.from(context)
//        val view = layoutInflater.inflate(R.layout.item, container, false)
        val mView = LayoutInflater.from(context).inflate(R.layout.plan_selection_item,container,false)
        val title: TextView
        val txt_title: TextView
        title = mView.findViewById(R.id.txt_des)
        txt_title = mView.findViewById(R.id.txt_title)
        title.text = models[position].price
        txt_title.text = models[position].title
        container.addView(mView, 0)
        return mView
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as View)
    }

}