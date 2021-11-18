package com.futuraeducation.practiceTest

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import com.jstarczewski.pc.mathview.src.TextAlign
import com.futuraeducation.R
import com.futuraeducation.activity.NotificationsActivity
import com.futuraeducation.adapter.AnswerClickListener
import com.futuraeducation.helper.MyProgressBar
import com.futuraeducation.model.QuestionNumberItem
import com.futuraeducation.model.QuestionType
import com.futuraeducation.model.SectionQuestion
import com.futuraeducation.model.TestResultsModel
import com.futuraeducation.model.onBoarding.AttemptedTest
import com.futuraeducation.network.ApiUtils
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.network.OnNetworkResponse
import com.futuraeducation.network.URLHelper
import com.futuraeducation.practiceTest.adapter.QuestionNumberAdapter
import com.futuraeducation.practiceTest.adapter.ReviewAdapter
import com.futuraeducation.utils.Utils
import kotlinx.android.synthetic.main.activity_test_review.*
import kotlinx.android.synthetic.main.dialog_jump_to_questions.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.json.JSONObject

class TestReviewActivity : AppCompatActivity(), OnNetworkResponse, AnswerClickListener,
    QuestionClickListener {

    private val questionNumberItem = ArrayList<QuestionNumberItem>()
    private lateinit var dialog: Dialog
    lateinit var networkHelper: NetworkHelper
    lateinit var myProgressBar: MyProgressBar
    lateinit var questionList: List<SectionQuestion?>
    private lateinit var questionNumberAdapter: QuestionNumberAdapter
    private lateinit var attemptedTest: AttemptedTest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_review)

        dialog = Dialog(this)
        myProgressBar = MyProgressBar(this)

        //Assign Appbar properties
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)


        networkHelper = NetworkHelper(this)

        attemptedTest = intent.getParcelableExtra("AttemptedTest")!!
        actionBar?.title = attemptedTest.name
        actionBar?.subtitle = Utils.getDateValue(attemptedTest.publishDate)
        requestAttemptedTest(attemptedTest)

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
                val correctAnswer =
                    getAns(questionList[position]?.correctAnswer.toString(), position)
                ansMathView.apply {
                    textZoom = 60
                    textColor = Color.GREEN.toString()
                    textAlign = TextAlign.LEFT
                    backgroundColor = "#D5FBD3"
                    text = correctAnswer
                }
            }
        })

        questionGroup.setOnClickListener {
            dialog.show()
        }

    }

    fun getAns(ans: String, pos: Int): String {
        return when (ans) {
            "a", "A" -> {
                questionList[pos]?.optionA!!.replace("\n", "").replace("<p class=\\\"p4\\\">", "")
            }
            "b", "B" -> {
                questionList[pos]?.optionB!!.replace("\n", "").replace("<p class=\\\"p4\\\">", "")
            }
            "c", "C" -> {
                questionList[pos]?.optionC!!.replace("\n", "").replace("<p class=\\\"p4\\\">", "")
            }
            else -> {
                questionList[pos]?.optionD!!.replace("\n", "").replace("<p class=\\\"p4\\\">", "")
            }
        }
    }

    private fun requestAttemptedTest(attemptedTest: AttemptedTest?) {

        myProgressBar.show()
        val jsonObject = JSONObject()
        jsonObject.put("attempt", attemptedTest?.totalAttempts)
        jsonObject.put("studentId", attemptedTest?.studentId)
        jsonObject.put("testPaperId", attemptedTest?.testPaperId)

        networkHelper.postCall(
            URLHelper.answeredTestPapers,
            jsonObject,
            "answeredTestPapers",
            ApiUtils.getHeader(this),
            this
        )

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
        if (responseCode == networkHelper.responseSuccess && tag == "answeredTestPapers") {
            myProgressBar.dismiss()
            val testResponseResult = Gson().fromJson(response, TestResultsModel::class.java)
            questionList = testResponseResult?.sectionsData?.get(0)?.sectionQuestion!!
            val ans = getAns(questionList[0]?.correctAnswer!!,0)
            ansMathView.apply {
                textZoom = 60
                textColor = Color.GREEN.toString()
                textAlign = TextAlign.LEFT
                backgroundColor = "#D5FBD3"
                text = ans
            }
            timeTaken.text = "${testResponseResult.totalConsumeTime}s"
            topperTime.text = "${testResponseResult.totalTimeTakenByTopper}s"
            subjectName.text = testResponseResult.sectionsData[0]?.sectionName.toString()
            assignQuestion()
            formQuestionItem(questionList.size)
        }
    }

    private fun assignQuestion() {
        val questionAdapter = ReviewAdapter(this, questionList, this, true)
        viewPager.adapter = questionAdapter
        viewPager.offscreenPageLimit = 15
    }

    private fun formQuestionItem(questionCount: Int) {
        for (i in 1..questionCount) {
            if (questionList[i - 1]?.submittedAnswered.isNullOrEmpty())
                questionNumberItem.add(QuestionNumberItem(i, QuestionType.NOT_ATTEMPT))
            else if (questionList[i - 1]?.submittedAnswered.equals(
                    questionList[i - 1]?.correctAnswer,
                    ignoreCase = true
                )
            )
                questionNumberItem.add(QuestionNumberItem(i, QuestionType.ATTEMPT))
            else
                questionNumberItem.add(QuestionNumberItem(i, QuestionType.MARK_FOR_REVIEW))
        }
        questionNumberAdapter = QuestionNumberAdapter(this, questionNumberItem, this)
        questionNumberRecycler.adapter = questionNumberAdapter
        dialog.questionNumber.adapter = questionNumberAdapter
    }

    override fun onAnswerClicked(isClicked: Boolean, option: Char, position: Int) {

    }

    override fun onQuestionClicked(position: Int) {
        if (dialog.isShowing) {
            dialog.cancel()
            dialog.hide()
        }
        viewPager.currentItem = position
    }

}