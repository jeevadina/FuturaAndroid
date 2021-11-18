package com.futuraeducation.learn

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.futuraeducation.R
import com.futuraeducation.activity.NotificationsActivity
import com.futuraeducation.adapter.Learn.TopicVideoAdapter
import com.futuraeducation.adapter.Learn.VideoClickListener
import com.futuraeducation.adapter.SubTopicsAdapter
import com.futuraeducation.model.VideoMaterial
import com.futuraeducation.model.chapter.TopicMaterialResponse
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import kotlinx.android.synthetic.main.activity_learn.*
import kotlinx.android.synthetic.main.layout_backpress.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class LearnActivity : AppCompatActivity(), VideoClickListener {

    private var loginData = LoginData()
    lateinit var networkHelper: NetworkHelper
    lateinit var myPreferences: MyPreferences
    var topicResponse: List<TopicMaterialResponse>? = null
    lateinit var subTopicListAdapter: SubTopicsAdapter
    var chapterId = ""
    var batchId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)


        titleLearn.text = "Chapter "+ intent.getStringExtra("position")!!
        titleCountLearn.text = intent.getStringExtra("title")!!

        chapterId = intent.getStringExtra("id")!!
        batchId = intent.getStringExtra("batchID")!!

        val datas: List<TopicMaterialResponse> =
            Gson().fromJson(
                intent.getStringExtra("materials"),
                object : TypeToken<List<TopicMaterialResponse?>?>() {}.type
            )

        topicResponse = datas

        myPreferences = MyPreferences(this)
        networkHelper = NetworkHelper(this)
        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)
        nxtChapter.visibility = View.VISIBLE

        nxtChapter.setOnClickListener {
            finish()
        }

        logoTool.setOnClickListener {
            finish()
        }

        val topicData = ArrayList<TopicMaterialResponse>()
        topicResponse!!.sortedBy {
            it.topic?.createdAt
        }.map {
            topicData.add(it)
        }

        if (topicResponse!!.isNotEmpty()) {
            val titleAdapter = TopicVideoAdapter(this, topicData , this)
            tabsRecycler.adapter = titleAdapter
        } else {
            tabsRecycler.visibility = View.GONE
           // showErrorMsg("Currently no topics available.")
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
/*
    fun showErrorMsg(errorMsg: String) {
        stateful.showOffline()
        stateful.setOfflineText(errorMsg)
        stateful.setOfflineImageResource(R.drawable.icon_error)
    }*/

    override fun onVideoSelected(videoMaterial: List<VideoMaterial>, position: Int) {
        myPreferences = MyPreferences(this)
        myPreferences.setString(Define.VIDEO_DATA, Gson().toJson(videoMaterial))
        myPreferences.setInt(Define.VIDEO_POS, position)
        val intent = Intent(this, LearnVideoActivity::class.java)
        startActivity(intent)
    }
}
