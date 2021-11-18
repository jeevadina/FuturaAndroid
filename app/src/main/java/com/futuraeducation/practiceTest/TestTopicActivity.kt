package com.futuraeducation.practiceTest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.futuraeducation.R
import com.futuraeducation.activity.NotificationsActivity
import com.futuraeducation.model.PracticeSubjectItem
import com.futuraeducation.model.PracticeTopicItem
import com.futuraeducation.practiceTest.adapter.PracticeSubjectAdapter
import kotlinx.android.synthetic.main.activity_test_topic.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class TestTopicActivity : AppCompatActivity() {

    private val practiceSubjectList = ArrayList<PracticeSubjectItem>()
    private val practiceTopicList1 = ArrayList<PracticeTopicItem>()
    private val practiceTopicList2 = ArrayList<PracticeTopicItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_topic)

        //Assign Appbar properties
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar?.title = "Create Practice"

        practiceTopicList1.add(PracticeTopicItem("Reproduction", true))
        practiceTopicList1.add(PracticeTopicItem("Diversity in Living World", false))
        practiceTopicList1.add(PracticeTopicItem("Ecology & Environment", true))
        practiceTopicList1.add(PracticeTopicItem("Uncategorized", false))

        practiceTopicList2.add(PracticeTopicItem("Oscillations and waves", false))
        practiceTopicList2.add(PracticeTopicItem("Electrostatic", true))

        practiceSubjectList.add(PracticeSubjectItem("Biology", 10.11, practiceTopicList1))
        practiceSubjectList.add(PracticeSubjectItem("Physics", 10.11, practiceTopicList2))

        val practiceSubjectAdapter = PracticeSubjectAdapter(this, practiceSubjectList)
        practiceSubjectRecycler.adapter = practiceSubjectAdapter

        next.setOnClickListener {
            val intent = Intent(this, SelectedTopicActivity::class.java)
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