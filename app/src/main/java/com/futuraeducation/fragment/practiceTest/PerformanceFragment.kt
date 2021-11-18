package com.futuraeducation.fragment.practiceTest

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.futuraeducation.R
import com.futuraeducation.database.DatabaseHelper
import com.futuraeducation.model.LeaderboardItem
import com.futuraeducation.model.onBoarding.AverageBatchTests
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.ApiUtils
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.network.OnNetworkResponse
import com.futuraeducation.network.URLHelper
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import kotlinx.android.synthetic.main.fragment_performance.*
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PerformanceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PerformanceFragment : Fragment(), OnNetworkResponse {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var loginData = LoginData()
    lateinit var networkHelper: NetworkHelper
    lateinit var myPreferences: MyPreferences
    lateinit var db: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        myPreferences = MyPreferences(requireContext())
        networkHelper = NetworkHelper(requireContext())
        db = DatabaseHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_performance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)


//        if (averBatchTest.isNullOrEmpty()) {
//            requestSessions()
//        } else {
//            carouselView.adapter = CarouselAdapter(requireContext(), averBatchTest)
//        }

        requestSessions()
    }

    override fun onStart() {
        super.onStart()
        val averBatchTest = db.getAllAverageBatchTest()
       // displayData(averBatchTest)

        val leaderboardItems = ArrayList<LeaderboardItem>()
        leaderboardItems.add(LeaderboardItem("Fazalu Rehman","29/30"))
        leaderboardItems.add(LeaderboardItem("Ayesha","28/30"))
        leaderboardItems.add(LeaderboardItem("Naveen","26/30"))
        leaderboardItems.add(LeaderboardItem("Prabhu","23/30"))
        //recyclerViewLeaderboard.adapter = LeaderboardItemAdapter(requireContext(),leaderboardItems)
    }

    private fun requestSessions() {

        val myBatchList = JSONArray()
        loginData.userDetail?.batchList?.forEach {
            myBatchList.put(it.id!!)
        }

        networkHelper.getCall(
            URLHelper.averageBatchTests + "?batchId=${
                loginData.userDetail?.batchList?.get(0)?.id
            }&studentId=${loginData.userDetail?.usersId.toString()}",
            "averageBatchTests",
            ApiUtils.getHeader(requireContext()),
            this
        )

    }

    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {
        if (responseCode == networkHelper.responseSuccess && tag == "averageBatchTests") {
            val testResponse = Gson().fromJson(response, AverageBatchTests::class.java)
            db.saveAvg(testResponse)
            //carouselView.adapter = CarouselAdapter(requireContext(), db.getAllAverageBatchTest())
        }
    }

    fun displayData(testResponse:AverageBatchTests){
        val totalStudent = 60
        val string = Html.fromHtml(
                "Out of <font color=#6ec1e4> $totalStudent </font></b>Students")

        txtTotalStudent.text = string
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PerformanceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PerformanceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}