package com.futuraeducation.doubt

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
import kotlinx.android.synthetic.main.activity_ask_doubt.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.row_file_name.*


class AskDoubtActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_doubt)

        //Assign Appbar properties
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar?.title = "Doubt"

        val boardList: MutableList<String> = ArrayList()
        boardList.add("NEET (Boards)")
        val boardAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.spinner_item, boardList)
        boardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        board.adapter = boardAdapter
        boardArrow.setOnClickListener {
            board.performClick()
        }

        val titleList: MutableList<String> = ArrayList()
        titleList.add("Physics")
        val titleAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.spinner_item, titleList)
        titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        titleSpinner.adapter = titleAdapter
        titleArrow.setOnClickListener {
            titleSpinner.performClick()
        }

        val subTitleList: MutableList<String> = ArrayList()
        subTitleList.add("Solar System")
        val subTitleAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.spinner_item, subTitleList)
        subTitleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        subTitle.adapter = subTitleAdapter
        subtitleArrow.setOnClickListener {
            subTitle.performClick()
        }

        fileName.text = "Important basic elements"
        fileIcon.setBackgroundResource(R.drawable.ic_pdf)

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