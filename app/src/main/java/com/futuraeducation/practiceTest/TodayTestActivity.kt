package com.futuraeducation.practiceTest

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.SystemClock
import android.view.Gravity
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.futuraeducation.R
import com.futuraeducation.adapter.AnswerClickListener
import com.futuraeducation.database.DatabaseHelper
import com.futuraeducation.model.*
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.*
import com.futuraeducation.practiceTest.adapter.QuestionAdapter
import com.futuraeducation.practiceTest.adapter.QuestionNumberAdapter
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import com.futuraeducation.utils.Utils
import kotlinx.android.synthetic.main.activity_today_test.*
import kotlinx.android.synthetic.main.dialog_jump_to_questions.*
import kotlinx.android.synthetic.main.dialog_jump_to_questions.close
import kotlinx.android.synthetic.main.dialog_submit.*
import kotlinx.android.synthetic.main.layout_backpress_test.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.json.JSONArray
import org.json.JSONObject


class TodayTestActivity : AppCompatActivity(), OnNetworkResponse, AnswerClickListener,
    QuestionClickListener {

    private val questionNumberItem = ArrayList<QuestionNumberItem>()
    private lateinit var questionNumberAdapter: QuestionNumberAdapter
    lateinit var networkHelper: NetworkHelper
    lateinit var myPreferences: MyPreferences
    private lateinit var db: DatabaseHelper
    private var loginData = LoginData()
    lateinit var cd: ConnectionDetector
    lateinit var questionList: List<Quesion>
    lateinit var mockTest: MOCKTEST

    var noMarked: Int = 0
    var currentPosition = 0
    private lateinit var dialog: Dialog

    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_test)

        mockTest = intent.getParcelableExtra("mockTest")!!


        durationValue.base =
            SystemClock.elapsedRealtime() + mockTest.testPaperVo?.duration?.times(10000)!!
        if (mockTest.testPaperVo?.timeLeft?.toInt()!! > 0) {
            durationValue.base =
                SystemClock.elapsedRealtime() + (mockTest.testPaperVo?.timeLeft?.toInt()!! * 10000)
        }

        //Assign Appbar properties
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar?.title = mockTest.testPaperVo?.name
        actionBar?.subtitle = Utils.getDateValue(mockTest.publishDate!!)

        myPreferences = MyPreferences(this)
        networkHelper = NetworkHelper(this)
        db = DatabaseHelper(this)
        cd = ConnectionDetector(this)

        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)

        markReview()

        questionList = db.getQuestionList(mockTest.testPaperId!!)
        val noCompleted = questionList.filter {
            !it.answer.isNullOrEmpty() && it.answer != "-"
        }.size
        val completedValueText = "$noCompleted Out of ${questionList.size}"
        completedValue.text = completedValueText
        formQuestionItem(questionList.size)

        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_jump_to_questions)
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
        val manager = FlexboxLayoutManager(this, FlexDirection.ROW)
        manager.justifyContent = JustifyContent.CENTER
        dialog.questionNumber.layoutManager = manager
        questionNumberAdapter = QuestionNumberAdapter(this, questionNumberItem, this)
        questionNumberRecycler.adapter = questionNumberAdapter
        dialog.questionNumber.adapter = questionNumberAdapter

        if (mockTest.testPaperVo?.isPauseAllow!!) {
            pause.isEnabled = true
            pause.alpha = 0f
        } else {
            pause.isEnabled = false
            pause.alpha = 0.6f
        }
        pause.isEnabled = mockTest.testPaperVo?.isPauseAllow!!

        questionGroup.setOnClickListener {
            showDialog()
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                questionNumberRecycler.layoutManager?.scrollToPosition(position)
            }

            override fun onPageSelected(position: Int) {
                if (currentPosition == position - 1)
                    saveNext(position - 1)
                else if (currentPosition - 1 == position)
                    saveNext(position + 1)
                currentPosition = position
            }
        })
        markForReview.setOnClickListener {
            if (questionNumberItem[viewPager.currentItem].questionType == QuestionType.NOT_ATTEMPT || questionNumberItem[viewPager.currentItem].questionType == QuestionType.NOT_VISITED) {
                ++noMarked
                markReview()
                questionNumberItem[viewPager.currentItem].questionType =
                    QuestionType.MARK_FOR_REVIEW
                questionNumberAdapter.setItems(questionNumberItem)
                questionNumberAdapter.notifyDataSetChanged()
            }
            viewPager.currentItem++
        }

        assignQuestion()

        next.setOnClickListener {
            viewPager.currentItem++
        }
        previous.setOnClickListener {
            viewPager.currentItem--
        }
        submitTest.setOnClickListener {
            if (myPreferences.getBoolean(Define.TAKE_TEST_MODE_OFFLINE)) {
                showDialogSubmit()
            } else {
                submitTestPaper()
            }
        }
        durationValue.start()
    }

    private fun markReview() {
        markedValue.text = noMarked.toString()
    }

    private fun showDialogSubmit() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_submit)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.attributes.gravity = Gravity.CENTER
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.disagree.setOnClickListener {
            dialog.dismiss()
        }
        dialog.agree.setOnClickListener {
            submitTestPaper()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun submitTestPaper() {
        statefulLayout.showProgress()
        statefulLayout.setProgressText("Loading..")

        val jsonArray = JSONArray()
        for (question in questionList) {
            val jsonAnsObject = JSONObject()
            jsonAnsObject.put(
                "questionPaperId",
                question.id
            )
            if (question.answer.isNullOrEmpty()) {
                jsonAnsObject.put(
                    "answer",
                    ""
                )
            }else{
                jsonAnsObject.put(
                    "answer",
                    question.answer.toString()
                )
            }
            jsonAnsObject.put(
                "timeSpent",
                question.timeSpent
            )
            jsonArray.put(jsonAnsObject)
        }
        if (!myPreferences.getBoolean(Define.TAKE_TEST_MODE_OFFLINE)) {
            if (cd.isConnectingToInternet()) {
                val jsonObject = JSONObject()
                jsonObject.put("testPaperId", mockTest.testPaperId)
                jsonObject.put("attempt", mockTest.testPaperVo?.attempts)
                jsonObject.put("studentId", loginData.userDetail?.usersId)
                jsonObject.put("testDurationTime", mockTest.testPaperVo?.duration)

                jsonObject.put("questionAnswerList", jsonArray)
                networkHelper.postCall(
                    URLHelper.submitTestPaper,
                    jsonObject,
                    "submitTestPaper",
                    ApiUtils.getHeader(this),
                    this
                )

            }
        } else {
            for (question in questionList) {
                db.updateAnswer(question.id, question.answer)
            }

            val completedTest = CompletedTest()
            completedTest.testPaperId = mockTest.testPaperId
            completedTest.attempt = mockTest.testPaperVo?.attempts.toString()
            completedTest.studentId = loginData.userDetail?.usersId
            completedTest.testDurationTime = mockTest.testPaperVo?.duration!!
            completedTest.questionAnswerList = jsonArray.toString()
            db.addCompletedTest(completedTest)

            val returnIntent = Intent()
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }

    private fun saveNext(position: Int) {
        if (questionList[position].answer.isNullOrEmpty()) {
            if (questionNumberItem[position].questionType != QuestionType.MARK_FOR_REVIEW) {
                questionNumberItem[position].questionType = QuestionType.NOT_ATTEMPT
            }
            questionNumberAdapter.setItems(questionNumberItem)
            questionNumberAdapter.notifyDataSetChanged()
        } else {
            val noCompleted =
                questionList.filter { !it.answer.isNullOrEmpty() && it.answer != "-" }.size
            val completedValueText = "$noCompleted Out of ${questionList.size}"
            completedValue.text = completedValueText
            when {
                questionNumberItem[position].questionType == QuestionType.MARK_FOR_REVIEW -> {

                }
                questionList[position].answer == "-" -> {
                    questionNumberItem[position].questionType = QuestionType.NOT_ATTEMPT
                }
                else -> {
                    questionNumberItem[position].questionType = QuestionType.ATTEMPT
                }
            }
            questionNumberAdapter.setItems(questionNumberItem)
            questionNumberAdapter.notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> Snackbar.make(
                window.decorView.rootView,
                "Test is going you are not able to go back",
                Snackbar.LENGTH_LONG
            ).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialog() {
        dialog.show()
    }

    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {
        statefulLayout.showContent()
        if (responseCode == networkHelper.responseSuccess && tag == "submitTestPaper") {
            val returnIntent = Intent()
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }

    private fun formQuestionItem(questionCount: Int) {
        for (i in 1..questionCount) {
            if (questionList[i - 1].answer.isNullOrEmpty())
                questionNumberItem.add(QuestionNumberItem(i, QuestionType.NOT_VISITED))
            else
                questionNumberItem.add(QuestionNumberItem(i, QuestionType.ATTEMPT))
        }
    }

    private fun assignQuestion() {
        questionAdapter = QuestionAdapter(this, questionList, this, false)
        viewPager.adapter = questionAdapter
        viewPager.offscreenPageLimit = 15
    }

    override fun onAnswerClicked(isClicked: Boolean, option: Char, position: Int) {
        questionList[position].isAnswered = isClicked
        if (isClicked) {
            questionNumberItem[position].questionType = QuestionType.ATTEMPT
            questionNumberAdapter.setItems(questionNumberItem)
            questionNumberAdapter.notifyDataSetChanged()
            questionNumberItem[position].questionType = QuestionType.NOT_ATTEMPT
        }else{
            questionNumberItem[position].questionType = QuestionType.NOT_ATTEMPT
            questionNumberAdapter.setItems(questionNumberItem)
            questionNumberAdapter.notifyDataSetChanged()
            questionNumberItem[position].questionType = QuestionType.NOT_ATTEMPT
        }
        questionList[position].answer = option.toString()
        if (questionNumberItem[position].questionType == QuestionType.MARK_FOR_REVIEW && option.toString()
                .isNotEmpty()
        ) {
            noMarked--
            markReview()
        }
    }

    override fun onBackPressed() {
        Snackbar.make(
            window.decorView.rootView,
            "Test is going you are not able to go back",
            Snackbar.LENGTH_LONG
        ).show()
        super.onBackPressed()
    }

    override fun onQuestionClicked(position: Int) {
        if (dialog.isShowing) {
            dialog.cancel()
            dialog.hide()
        }
        saveNext(viewPager.currentItem)
        viewPager.currentItem = position
    }

}