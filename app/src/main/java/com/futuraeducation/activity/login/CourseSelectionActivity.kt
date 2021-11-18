package com.futuraeducation.activity.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.futuraeducation.R
import com.futuraeducation.adapter.CourseSelectionAdapter
import kotlinx.android.synthetic.main.activity_course_selection.*

class CourseSelectionActivity : AppCompatActivity() {

    private val myList= ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_selection)

        myList?.apply {
            this.add("NEET")
            this.add("JEE")
            this.add("Foundation")
            this.add("NEET")
            this.add("Grade 12 (NEET)")
            this.add("Grade 11 (NEET)")
        }

        recyclerCall(myList)

        submitCorse.setOnClickListener {
            val intent = Intent(this, PlanSelectionActivity::class.java)
            startActivity(intent)
        }

    }

    private fun recyclerCall(resultList: ArrayList<String>?) {
        val adapter = CourseSelectionAdapter(this, resultList)
        //now adding the adapter to recyclerview
        courseSelectionRecycler.adapter = adapter
    }

}