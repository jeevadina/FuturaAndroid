package com.futuraeducation.learn

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.futuraeducation.R
import com.futuraeducation.activity.NotificationsActivity
import com.futuraeducation.learn.fragment.StudyMaterialFragment
import com.futuraeducation.learn.fragment.VideoFragment
import com.futuraeducation.model.VideoMaterial
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import com.futuraeducation.utils.Utils
import kotlinx.android.synthetic.main.activity_learn_video.*
import kotlinx.android.synthetic.main.layout_backpress.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.lang.reflect.Type

class LearnVideoActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment
    lateinit var sharedPreferences: SharedPreferences
    lateinit var myPreferences: MyPreferences
    private var pos: Int ?= null
    var videoData = ArrayList<VideoMaterial>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_video)
        Utils.updateStatusBarColor(this,"#FFF0BE")
        Utils.updateStatusBarColor(this,"#FFF0BE")

        sharedPreferences = getSharedPreferences("MySharedPref", 0)
        myPreferences = MyPreferences(this)

        //Assign Appbar properties
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        val listType: Type = object : TypeToken<ArrayList<VideoMaterial?>?>() {}.type
        videoData = Gson().fromJson(myPreferences.getString(Define.VIDEO_DATA), listType)
        pos = myPreferences.getInt(Define.VIDEO_POS)

        actionBar?.title = videoData[pos!!].title

        fragment = VideoFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.commit()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> fragment = VideoFragment()
                    1 -> fragment = StudyMaterialFragment()
                }
                val fm: FragmentManager = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frameLayout, fragment)
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ft.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        val courseName = intent.getStringExtra("courseName")
        if(courseName != null){
            titleCountLearn.text = courseName
        }
    }

    override fun onStart() {
        super.onStart()
        logoTool.setOnClickListener {
            finish()
        }
    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        try {
//            menuInflater.inflate(R.menu.menu_learn, menu)
//            val item1 =
//                menu.findItem(R.id.action_menu_notification).actionView.findViewById(R.id.layoutNotification) as RelativeLayout
//            item1.setOnClickListener {
//                startActivity(Intent(this, NotificationsActivity::class.java))
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> finish()
//        }
//        return super.onOptionsItemSelected(item)
//    }

}