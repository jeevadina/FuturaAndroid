package com.futuraeducation.fragment.practiceTest

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.futuraeducation.R
import com.futuraeducation.activity.TakeTestActivity
import com.futuraeducation.activity.TestResultActivity
import com.futuraeducation.adapter.ScheduledTestAdapter
import com.futuraeducation.adapter.SubjectClickListener
import com.futuraeducation.adapter.TestClickListener
import com.futuraeducation.adapter.practice.PracticeAdapter
import com.futuraeducation.database.DatabaseHelper
import com.futuraeducation.model.*
import com.futuraeducation.model.onBoarding.AttemptedResponse
import com.futuraeducation.model.onBoarding.AttemptedTest
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.ApiUtils
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.network.OnNetworkResponse
import com.futuraeducation.network.URLHelper
import com.futuraeducation.practiceTest.TestReviewActivity
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.DialogUtils
import com.futuraeducation.utils.MyPreferences
import com.futuraeducation.utils.Utils
import kotlinx.android.synthetic.main.dialog_confirm_test.*
import kotlinx.android.synthetic.main.dialog_jump_to_questions.close
import kotlinx.android.synthetic.main.fragment_practice_tab.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import org.json.JSONObject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PracticeTabFragment : Fragment(), OnNetworkResponse, TestClickListener, SubjectClickListener {

    private var subjectList = ArrayList<PracticeSubjects>()
    var attemptedTest: ArrayList<MOCKTEST>? = ArrayList()
    lateinit var scheduledTest: List<MOCKTEST>
    lateinit var db: DatabaseHelper
    private var loginData = LoginData()
    lateinit var networkHelper: NetworkHelper
    lateinit var myPreferences: MyPreferences
    var clickedTestPaperId = ""
    lateinit var testPaperId: String
    private var batchId: String = ""
    lateinit var attempt1: AttemptedTest
    lateinit var dialogUtils: DialogUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_practice_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = DatabaseHelper(requireContext())
        networkHelper = NetworkHelper(requireContext())
        myPreferences = MyPreferences(requireContext())
        //dialogUtils = DialogUtils()

        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)

        batchId = loginData.userDetail?.batchList?.get(0)?.id!!
        requestTest(batchId)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

        expandablePracticeTxt.text = requireContext().getString(R.string.practice_by)

       /* expandablePracticeTestAttempted.parentLayout.txtParentTitle.text = "Attempted Test"

        expandablePracticeTestAttempted.secondLayout.txtError.text =
            "No Test Found in Attempted Test"

        expandablePracticeTestAttempted.parentLayout.setOnClickListener {
            if (expandablePracticeTestAttempted.isExpanded)
                expandablePracticeTestAttempted.collapse()
            else {
                expandablePracticeTestAttempted.expand()
            }
        }*/

    }


    fun requestTest(batchId: String) {
        networkHelper.getCall(
            URLHelper.scheduleTestsForStudent + "?batchId=${
                batchId
            }&studentId=${loginData.userDetail?.usersId}",
            "scheduledTest1",
            ApiUtils.getHeader(requireContext()),
            this
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LearnFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PracticeTabFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Utils.LAUNCH_SECOND_ACTIVITY) {
            requestTest(batchId)
        }
    }

    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {
        if (responseCode == networkHelper.responseSuccess && tag == "scheduledTest1") {
            var scheduledTestResponse =
                Gson().fromJson(response, ScheduledClass::class.java)
            Log.e("testCheck", scheduledTestResponse.PRACTICE.toString())

            scheduledTest = scheduledTestResponse.PRACTICE

            if (!scheduledTestResponse.PRACTICE.isNullOrEmpty()) {
                val completedList = db.getCompletedTest()
                completedList.forEachIndexed { _, completedListElement ->
                    attemptedTest!!.addAll(
                        scheduledTestResponse.PRACTICE.filter { it.testPaperId == completedListElement.testPaperId }
                            .toMutableList())
                    scheduledTestResponse.PRACTICE =
                        scheduledTestResponse.PRACTICE.filterNot { it.testPaperId == completedListElement.testPaperId }
                }
            }
            callAdapter(scheduledTest)
            getAttemptedTest(batchId)
        } else if (responseCode == networkHelper.responseSuccess && tag == "getAttempted") {
            val attempted = Gson().fromJson(response, AttemptedResponse::class.java)
            if (attemptedTest?.size!! > 0) {
                for (attempt in attemptedTest!!) {
                    attempted.pRACTICE.add(
                        AttemptedTest(
                            "completed",
                            attempt.testPaperVo?.correctMark!!,
                            attempt.testPaperVo.duration,
                            attempt.expiryDate,
                            attempt.expiryDateTime,
                            attempt.expiryTime,
                            attempt.testPaperVo.name,
                            attempt.publishDate,
                            attempt.publishDateTime,
                            attempt.publishTime!!,
                            attempt.testPaperVo.questionCount,
                            attempt.testPaperVo.status,
                            "",
                            loginData.userDetail?.usersId!!,
                            loginData.userDetail?.userName!!,
                            attempt.testPaperVo.testCode,
                            attempt.testPaperId,
                            attempt.testPaperVo.testType,
                            attempt.testPaperVo.attempts,
                            attempt.testPaperVo.duration.toString(),
                            attempt.testPaperVo.correctMark.toString(),
                        )
                    )
                }
            }
            attemptedSetup(attempted)

            //dialogUtils.dismissLoader()
        } else if (responseCode == networkHelper.responseSuccess && tag == "submitTestPaper") {
            val submittedResult = Gson().fromJson(response, SubmittedResult::class.java)
            db.deleteTest(submittedResult.testPaperId)
            attemptedTest =
                attemptedTest?.filterNot { it.testPaperId == submittedResult.testPaperId } as ArrayList<MOCKTEST>?
            getAttemptedTest(batchId)
        }
    }

    private fun attemptedSetup(attempted: AttemptedResponse) {

        Log.e("popAttempted", attempted.pRACTICE.toString())
        if (view != null) {

/*
            if (attempted.pRACTICE.size > 0) {
                expandablePracticeTestAttempted.secondLayout.txtError.visibility = View.GONE
                expandablePracticeTestAttempted.secondLayout.recyclerViewChild.visibility =
                    View.GONE
                val attemptedAdapter = AttemptedTestAdapter(
                    requireContext(),
                    attempted.pRACTICE.reversed(), this
                )
                expandablePracticeTestAttempted.secondLayout.recyclerViewChild.adapter =
                    attemptedAdapter
            } else {
                expandablePracticeTestAttempted.secondLayout.recyclerViewChild.visibility =
                    View.GONE
                expandablePracticeTestAttempted.secondLayout.txtError.visibility = View.GONE
            }
*/
        }
    }

    private fun getAttemptedTest(batchId: String) {
        networkHelper.getCall(
            URLHelper.attemptedTests + "?batchId=${
                batchId
            }&studentId=${loginData.userDetail?.usersId}",
            "getAttempted",
            ApiUtils.getHeader(requireContext()),
            this
        )
    }

    private fun showDialog(mockTest: MOCKTEST) {
        val dialog = Dialog(requireContext())
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

    fun submitTest(jsonObject: JSONObject) {
        networkHelper.postCall(
            URLHelper.submitTestPaper,
            jsonObject,
            "submitTestPaper",
            ApiUtils.getAuthorizationHeader(requireContext(), jsonObject.toString().length),
            this
        )
    }

    private fun goToTestScreen(mockTest: MOCKTEST) {
        val intent = Intent(requireContext(), TakeTestActivity::class.java)
        intent.putExtra("mockTest", mockTest)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivityForResult(intent, Utils.LAUNCH_SECOND_ACTIVITY)
    }

    override fun onTestClicked(isClicked: Boolean, mockTest: MOCKTEST) {
        showDialog(mockTest)
    }

    override fun onResultClicked(id: String) {
        clickedTestPaperId = id
        val completedTest = db.getCompletedTest(id)

        val jsonAnsArray = JSONArray(completedTest.questionAnswerList!!)

        val jsonAnsObject = JSONObject()
        jsonAnsObject.put("testPaperId", completedTest.testPaperId)
        jsonAnsObject.put("attempt", completedTest.attempt)
        jsonAnsObject.put("studentId", completedTest.studentId)
        jsonAnsObject.put("testDurationTime", completedTest.testDurationTime)
        jsonAnsObject.put(
            "questionAnswerList",
            jsonAnsArray
        )
        submitTest(jsonAnsObject)
    }

    override fun onResultClicked(attempt: Int, studentId: String, testPaperID: String) {
        testPaperId = testPaperID
        val intent = Intent(requireContext(), TestResultActivity::class.java)
        intent.putExtra("attempt", attempt)
        intent.putExtra("studentId", studentId)
        intent.putExtra("testPaperId", testPaperID)
        startActivity(intent)

    }

    override fun onReviewClicked(attempt: AttemptedTest) {
        testPaperId = attempt.testPaperId
        attempt1 = attempt
        val intent = Intent(context, TestReviewActivity::class.java)
        intent.putExtra("AttemptedTest", attempt)
        startActivity(intent)
    }

    private fun subjectCall(subjectList: ArrayList<Datum>) {
        if (subjectList.size > 0) {
            txtError.visibility = View.GONE
            subjectsRecycler1.visibility = View.VISIBLE

            val adapter = PracticeAdapter(requireContext(), subjectList, batchId, this)
            //now adding the adapter to recyclerview
            subjectsRecycler1.adapter = adapter

          //
        //  callAdapter(subjectList[0])
        } else {
            subjectsRecycler1.visibility = View.GONE
            txtError.visibility = View.VISIBLE
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: OnEventData?) {
        Log.e("popThread", "123")
        //dialogUtils.showLoader(requireContext())
        val data = loginData.userDetail?.batchList?.get(event?.batchPosition!!)
        batchId = data?.id!!
        requestTest(data.id!!)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLearnEvent(event: CourseResponse) {
        Log.e("popThread", "123")
        event.data?.let {
            Log.e("popThread", "321")
            subjectCall(it)
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onSubjectClicked(id: String, batchId: String, title: String) {
        /*   Log.e("subjectPop", title.toString())
           val intent = Intent(requireContext(), TestGroupActivity::class.java)
           intent.putExtra("practiceTest", Gson().toJson(scheduledTest).toString())
           intent.putExtra("subjectName", title)
           requireContext().startActivity(intent)*/
        val filteredData = scheduledTest.filter {
            it.courseName == title
        }

        callAdapter(filteredData)
    }

    fun callAdapter(filteredData: List<MOCKTEST>) {

        subjectsRecycler2.visibility =
            View.VISIBLE
        val scheduledTestAdapter = ScheduledTestAdapter(
            requireContext(),
            filteredData,
            this
        )
        subjectsRecycler2.adapter =
            scheduledTestAdapter

    }

}