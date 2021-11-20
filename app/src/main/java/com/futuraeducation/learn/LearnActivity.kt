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
import com.futuraeducation.model.chapter.ChapterResponseData
import com.futuraeducation.model.chapter.TopicMaterialResponse
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import com.futuraeducation.utils.Utils
import kotlinx.android.synthetic.main.activity_learn.*
import kotlinx.android.synthetic.main.layout_backpress.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.lang.reflect.Type


class LearnActivity : AppCompatActivity(), VideoClickListener {
    var chaptersList: List<ChapterResponseData>?= null
    private var loginData = LoginData()
    lateinit var networkHelper: NetworkHelper
    lateinit var myPreferences: MyPreferences
    var topicResponse: List<TopicMaterialResponse>? = null
    lateinit var subTopicListAdapter: SubTopicsAdapter
    var chapterId = ""
    var batchId = ""
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        Utils.updateStatusBarColor(this,"#FFF0BE")

        val data = intent.getStringExtra("data")
        position = intent.getIntExtra("position",0)
        if(data != null){
            val listType: Type = object : TypeToken<ArrayList<ChapterResponseData?>?>() {}.type
            chaptersList = Gson().fromJson(data, listType)
        }

        displayData(position)
//        val datas: List<TopicMaterialResponse> =
//            Gson().fromJson(
//                intent.getStringExtra("materials"),
//                object : TypeToken<List<TopicMaterialResponse?>?>() {}.type
//            )
//
//        topicResponse = datas

        myPreferences = MyPreferences(this)
        networkHelper = NetworkHelper(this)
        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)
        nxtChapter.visibility = View.VISIBLE

        nxtChapter.setOnClickListener {
            position += 1
            displayData(position= position)
        }

        logoTool.setOnClickListener {
            finish()
        }

    }

    private fun displayData(position: Int){
        if(chaptersList != null){
            titleLearn.text = "Chapter ${position+1}"
            titleCountLearn.text = chaptersList!![position].courseName

            chapterId = chaptersList!![position].id!!
            batchId = intent.getStringExtra("batchID")!!
            topicResponse = chaptersList!![position].topicMaterialResponses
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
        intent.putExtra("courseName",chaptersList!![this.position].courseName)
        startActivity(intent)
    }
}
