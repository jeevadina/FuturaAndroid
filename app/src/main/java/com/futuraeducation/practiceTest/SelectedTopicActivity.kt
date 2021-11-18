package com.futuraeducation.practiceTest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.futuraeducation.R
import com.futuraeducation.activity.NotificationsActivity
import com.futuraeducation.model.PracticeTopicItem
import com.futuraeducation.practiceTest.adapter.PracticeTopicAdapter
import kotlinx.android.synthetic.main.activity_selected_topic.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class SelectedTopicActivity : AppCompatActivity() {
    private val practiceTopicList = ArrayList<PracticeTopicItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_topic)

        //Assign Appbar properties
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar?.title = "Create Practice"

        practiceTopicList.add(PracticeTopicItem("Reproduction", true))
        practiceTopicList.add(PracticeTopicItem("Ecology & Environment", true))
        practiceTopicList.add(PracticeTopicItem("Electrostatic", true))
        val practiceTopicAdapter = PracticeTopicAdapter(this, practiceTopicList)
        practiceTopicRecycler.adapter = practiceTopicAdapter

        val questionList: MutableList<String> = ArrayList()
        questionList.add("No.of Questions")
        val questionAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.spinner_item, questionList)
        questionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        questions.adapter = questionAdapter
        questionArrow.setOnClickListener {
            questions.performClick()
        }

        val durationList: MutableList<String> = ArrayList()
        durationList.add("Duration (mins)")
        val durationAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.spinner_item, durationList)
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        duration.adapter = durationAdapter
        durationArrow.setOnClickListener {
            duration.performClick()
        }

        next.setOnClickListener {
            val intent = Intent(this, StartPracticeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        try {
            menuInflater.inflate(R.menu.menu_learn, menu)
            val item1 =
                menu.findItem(R.id.action_menu_notification).actionView.findViewById(R.id.layoutNotification) as RelativeLayout
            item1.setOnClickListener {
                startActivity(Intent(this, NotificationsActivity::class.java))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}