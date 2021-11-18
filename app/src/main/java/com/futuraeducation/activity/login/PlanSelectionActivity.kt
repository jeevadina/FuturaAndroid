package com.futuraeducation.activity.login

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.futuraeducation.R
import com.futuraeducation.adapter.PlanSelectionAdapter
import com.futuraeducation.model.PlansModel
import com.futuraeducation.onBoarding.LoginActivity
import kotlinx.android.synthetic.main.activity_plans.*

class PlanSelectionActivity: AppCompatActivity() {

    private lateinit var adapter: PlanSelectionAdapter
    private lateinit var models: ArrayList<PlansModel>
    private lateinit var viewPager: ViewPager
    var sliderDotspanel: LinearLayout? = null
    private var dotscount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plans)

        viewPager = findViewById(R.id.view_pager)
        sliderDotspanel = findViewById(R.id.slider_dots)

        models = ArrayList()
        models.add(PlansModel("Free","Basic", "Basic"))
        models.add(PlansModel("99 USD","Elite", "Elite"))
        models.add(PlansModel("300 USD","Premium", "Premium"))


        adapter = PlanSelectionAdapter(models, this)
        viewPager.adapter = adapter

        textViewskip.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        viewPager.setPadding(30, 0, 30, 0)
        dotscount = adapter.count

        val dots = arrayOfNulls<ImageView>(dotscount)

        for (i in 0 until dotscount) {
            dots[i] = ImageView(this)
            dots[i]!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.non_active_dot))
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(8, 0, 8, 0)
            sliderDotspanel!!.addView(dots[i], params)
        }
        dots[0]?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dot))

        viewPager.setOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int){}

            override fun onPageSelected(position: Int) {
                for (i in 0 until dotscount) {
                    dots[i]?.setImageDrawable(ContextCompat.getDrawable(this@PlanSelectionActivity,R.drawable.non_active_dot))
                }
                dots[position]?.setImageDrawable(ContextCompat.getDrawable(this@PlanSelectionActivity,R.drawable.active_dot))
            }
        })

    }
}