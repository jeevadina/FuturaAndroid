package com.futuraeducation.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.futuraeducation.R
import com.futuraeducation.adapter.ScheduledTestAdapter
import com.futuraeducation.adapter.TestClickListener
import com.futuraeducation.database.DatabaseHelper
import com.futuraeducation.model.MOCKTEST
import com.futuraeducation.model.onBoarding.AttemptedTest
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.DialogUtils
import com.futuraeducation.utils.MyPreferences
import com.futuraeducation.utils.Utils
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.dialog_confirm_test.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class TestGroupActivity : AppCompatActivity() , TestClickListener{

    private var loginData = LoginData()
    lateinit var networkHelper: NetworkHelper
    lateinit var myPreferences: MyPreferences
    lateinit var db: DatabaseHelper
    lateinit var dialogUtils: DialogUtils
    lateinit var testPaperId: String
    var attemptedTest: ArrayList<MOCKTEST>? = ArrayList()
    var clickedTestPaperId = ""
    var batchId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        myPreferences = MyPreferences(this)
        networkHelper = NetworkHelper(this)
        db = DatabaseHelper(this)
        dialogUtils = DialogUtils()

        val response = intent.getStringExtra("practiceTest")
        val subject = intent.getStringExtra("subjectName")

        //Assign Appbar properties
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar?.title = "Test"

        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)

        var listType = object : TypeToken<List<MOCKTEST>>() {}.type
        val data: List<MOCKTEST> = Gson().fromJson(response, listType)

        val filteredData = data.filter {
            it.courseName == subject
        }

        batchId = loginData.userDetail?.batchList?.get(0)?.id!!

        callAdapter(filteredData)
    }

    fun callAdapter(filteredData: List<MOCKTEST>) {

        if (filteredData.isNullOrEmpty()) {
            recyclerViewChildTest.visibility = View.GONE
            txtErrorTest.visibility = View.VISIBLE
        } else {
            recyclerViewChildTest.visibility =
                View.VISIBLE
            txtErrorTest.visibility = View.GONE
            val scheduledTestAdapter = ScheduledTestAdapter(
                this,
                filteredData,
                this
            )
            recyclerViewChildTest.adapter =
                scheduledTestAdapter
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


    private fun showDialog(mockTest: MOCKTEST) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_confirm_test)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.attributes.gravity = Gravity.CENTER
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.close.setOnClickListener {
            dialog.cancel()
            dialog.hide()
        }
        dialog.disagree.setOnClickListener {
            myPreferences.setBoolean(Define.TAKE_TEST_MODE_OFFLINE, false)
            goToTestScreen(mockTest)
            dialog.dismiss()
        }
        dialog.agree.setOnClickListener {
            myPreferences.setBoolean(Define.TAKE_TEST_MODE_OFFLINE, true)
            goToTestScreen(mockTest)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun goToTestScreen(mockTest: MOCKTEST) {
        val intent = Intent(this, TakeTestActivity::class.java)
        intent.putExtra("mockTest", mockTest)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivityForResult(intent, Utils.LAUNCH_SECOND_ACTIVITY)
    }


    override fun onTestClicked(isClicked: Boolean, mockTest: MOCKTEST) {
        showDialog(mockTest)
    }

    override fun onResultClicked(id: String) {
        TODO("Not yet implemented")
    }

    override fun onResultClicked(attempt: Int, studentId: String, testPaperId: String) {
        TODO("Not yet implemented")
    }

    override fun onReviewClicked(attempt: AttemptedTest) {
        TODO("Not yet implemented")
    }
}