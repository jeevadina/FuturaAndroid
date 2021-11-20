package com.futuraeducation.fragment.practiceTest

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.futuraeducation.R
import com.futuraeducation.database.DatabaseHelper
import com.futuraeducation.model.OnEventData
import com.futuraeducation.model.onBoarding.AverageBatchTests
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.ApiUtils
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.network.OnNetworkResponse
import com.futuraeducation.network.URLHelper
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import com.futuraeducation.utils.Utils
import ir.mahozad.android.PieChart
import kotlinx.android.synthetic.main.fragment_test.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray

class TestFragment : Fragment(), OnNetworkResponse {

    private var loginData = LoginData()
    lateinit var networkHelper: NetworkHelper
    private var averBatchTest = mutableListOf<AverageBatchTests>()
    lateinit var myPreferences: MyPreferences
    lateinit var db: DatabaseHelper
    private var batchId =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myPreferences = MyPreferences(requireContext())
        networkHelper = NetworkHelper(requireContext())
        db = DatabaseHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Utils.updateStatusBarColor(requireActivity(),"#550D61")
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_frame, TestTabFragment.newInstance("", ""))
            .commit()

        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)
        batchId = loginData.userDetail?.batchList!![0].id!!
        averBatchTest = db.getAllAverageBatchTest()
       /* if (!averBatchTest.isNullOrEmpty()) {
            displayData(averBatchTest[0])
        }*/

        pieChart.slices = listOf(
            PieChart.Slice(0.2f, Color.BLUE),
            PieChart.Slice(0.4f, Color.MAGENTA),
            PieChart.Slice(0.3f, Color.YELLOW),
            PieChart.Slice(0.1f, Color.CYAN)
        )

    }
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        requestSessions(batchId)
    }

    private fun requestSessions(batchId: String) {
        progressView.visibility = View.VISIBLE
        val myBatchList = JSONArray()
        loginData.userDetail?.batchList?.forEach {
            myBatchList.put(it.id!!)
        }

        networkHelper.getCall(
            URLHelper.averageBatchTests + "?batchId=${
                batchId
            }&studentId=${loginData.userDetail?.usersId.toString()}",
            "averageBatchTests",
            ApiUtils.getHeader(requireContext()),
            this
        )
    }

    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {
        if (responseCode == networkHelper.responseSuccess && tag == "averageBatchTests") {
            progressView.visibility = View.GONE
            val testResponse = Gson().fromJson(response, AverageBatchTests::class.java)
            db.saveAvg(testResponse)
           // displayData(testResponse)
            //carouselView.adapter = CarouselAdapter(requireContext(), db.getAllAverageBatchTest())
        }
    }

/*    fun displayData(testResponse:AverageBatchTests){
        val totalStudent = testResponse.totalStudents
        val string = Html.fromHtml(
            "Out of <font color=#6ec1e4><b> $totalStudent </font></b>Students")

        txtTotalStudent.text = string
        txtRank.text = ""+testResponse.rank
        txtYourAverage.text = ""+testResponse.studentAverage+"%"
        txtClassAverage.text = ""+testResponse.classAverage+"%"
        txtTopperAverage.text = ""+testResponse.topperAverage+"%"
    }*/

    override fun onPause() {
        super.onPause()
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
            TestFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: OnEventData?) {
        val data = loginData.userDetail?.batchList?.get(event?.batchPosition!!)
        batchId = data?.id!!
        requestSessions(data?.id!!)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}