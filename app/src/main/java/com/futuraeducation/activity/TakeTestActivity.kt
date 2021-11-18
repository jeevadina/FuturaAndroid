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
import com.futuraeducation.database.DatabaseHelper
import com.futuraeducation.model.MOCKTEST
import com.futuraeducation.model.TestPaperResponse
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.ApiUtils
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.network.OnNetworkResponse
import com.futuraeducation.network.URLHelper
import com.futuraeducation.network.URLHelper.testStatus
import com.futuraeducation.network.UrlConstants.kSTARTED
import com.futuraeducation.practiceTest.TodayTestActivity
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import com.futuraeducation.utils.Utils.getDuration
import kotlinx.android.synthetic.main.activity_take_test.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.json.JSONException
import org.json.JSONObject

class TakeTestActivity : AppCompatActivity(), OnNetworkResponse {

    private var loginData = LoginData()
    lateinit var networkHelper: NetworkHelper
    lateinit var myPreferences: MyPreferences
    private lateinit var db: DatabaseHelper
    lateinit var mockTest: MOCKTEST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_test)

        //Assign Appbar properties
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar?.title = "Take Test"

        myPreferences = MyPreferences(this)
        networkHelper = NetworkHelper(this)
        db = DatabaseHelper(this)

        assignValue(intent)
        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)
        takeTest.setOnClickListener {
            getTest()
        }
    }

    private fun getTest() {
        stateful.showProgress()
        stateful.setProgressText("Downloading Test..")
        val jsonObject = JSONObject()
        try {
            jsonObject.put("testPaperId", mockTest.testPaperId)
            jsonObject.put("studentId", loginData.userDetail?.userDetailId.toString())
            jsonObject.put("batchIds", loginData.userDetail?.batchList?.get(0)?.id.toString())
            jsonObject.put("status", kSTARTED)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        networkHelper.postCall(
            testStatus,
            jsonObject,
            "testStatus",
            ApiUtils.getHeader(this),
            this
        )
        networkHelper.getCall(
            URLHelper.getStudentTestPaper + "?testPaperId=${mockTest.testPaperId}&studentId=${loginData.userDetail?.userDetailId.toString()}",
            "getStudentTestPaper",
            ApiUtils.getHeader(this),
            this
        )
    }

    private fun assignValue(intent: Intent) {
        mockTest = intent.getParcelableExtra("mockTest")!!

        if(mockTest.testPaperVo != null) {
            heading.text = mockTest.testPaperVo!!.name
            questionValue.text = mockTest.testPaperVo!!.questionCount.toString()
            durationValue.text = getDuration(mockTest.testPaperVo!!.duration)
            attemptedValue.text = mockTest.testPaperVo!!.attempts.toString()
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

    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {

        if (responseCode == networkHelper.responseSuccess && tag == "getStudentTestPaper") {
            val testPaperResponse = Gson().fromJson(response, TestPaperResponse::class.java)
            for (question in testPaperResponse.quesionList) {
                question.testPaperId = mockTest.testPaperId!!
                db.addQuestions(question)
            }
            stateful.showContent()
            val intent = Intent(this, TodayTestActivity::class.java)
            intent.putExtra("mockTest", mockTest)
            startActivity(intent)
            finish()
        } else if (responseCode == networkHelper.responseFailed && tag == "getStudentTestPaper") {
            stateful.showOffline()
            stateful.setOfflineText(response)
            stateful.setOfflineImageResource(R.drawable.icon_error)
            stateful.setOfflineRetryOnClickListener {
                getTest()
            }
        }
    }
}