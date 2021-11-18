package com.futuraeducation.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.futuraeducation.R
import com.futuraeducation.model.live.CompletedLiveModel
import com.futuraeducation.model.onBoarding.CompletedSession
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.*
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class CompletedLiveActivity: AppCompatActivity() , OnNetworkResponse {

    private var loginData = LoginData()
    lateinit var myPreferences: MyPreferences
    lateinit var networkHelper: NetworkHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compeleted_live)

        myPreferences = MyPreferences(this)
        networkHelper = NetworkHelper(this)

        //Assign Appbar properties
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar?.title = "Completed Live"

        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)
        val data = Gson().fromJson(intent.getStringExtra("completedLive"), CompletedSession::class.java)

        requestSessions()
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

    private fun requestSessions() {

        val myBatchList = JSONArray()
        loginData.userDetail?.batchList?.forEach {
            myBatchList.put(it.id!!)
        }
        val myBranchIDs = JSONArray()
        loginData.userDetail?.branchList?.forEach {
            myBranchIDs.put(it.id!!)
        }

        val jsonObject = JSONObject()
        try {
            jsonObject.put("branchIds", myBranchIDs)
            jsonObject.put("coachingCentreId", loginData.userDetail?.coachingCenterId.toString())
            jsonObject.put("batchIds", myBatchList)
            jsonObject.put("subjectId", "3b23c1b1-ef09-460b-b782-8da616cad4fb")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        networkHelper.postCall(
            URLHelper.getSessions,
            jsonObject,
            "completedSessionPost",
            ApiUtils.getAuthorizationHeader(this, jsonObject.toString().length),
            this
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {
        if (tag == "completedSessionPost"){
            val data = Gson().fromJson(response, CompletedLiveModel::class.java)
        }
    }
}