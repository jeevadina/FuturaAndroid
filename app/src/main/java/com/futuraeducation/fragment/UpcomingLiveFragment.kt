package com.futuraeducation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.futuraeducation.R
import com.futuraeducation.adapter.UpcomingLiveAdapter
import com.futuraeducation.model.LiveResponse
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.ApiUtils
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.network.OnNetworkResponse
import com.futuraeducation.network.URLHelper
import com.futuraeducation.network.UrlConstants.kUPCOMING
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import kotlinx.android.synthetic.main.fragment_upcoming_live.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UpcomingLiveFragment : Fragment(), OnNetworkResponse {

    lateinit var networkHelper: NetworkHelper
    lateinit var myPreferences: MyPreferences
    private var loginData = LoginData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPreferences = MyPreferences(requireContext())
        networkHelper = NetworkHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_live, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)

        requestSessions()

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
            UpcomingLiveFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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
            jsonObject.put("sessionTense", kUPCOMING)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        networkHelper.postCall(
            URLHelper.getSessions,
            jsonObject,
            "upcomingSessions",
            ApiUtils.getAuthorizationHeader(requireContext(), jsonObject.toString().length),
            this
        )
    }

    fun showErrorMsg(errorMsg: String) {
      /*  stateful.showOffline()
        stateful.setOfflineText(errorMsg)
        stateful.setOfflineImageResource(R.drawable.ic_no_data)
        stateful.setOfflineRetryOnClickListener {
            requestSessions()
        }*/
        errorTxt.text = errorMsg
        recycler.visibility = View.GONE
        errorUpcomingCard.visibility = View.VISIBLE
        retryUpcoming.setOnClickListener {
            requestSessions()
        }
    }


    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {
        if (responseCode == networkHelper.responseSuccess && tag == "upcomingSessions") {
            val liveItemResponse = Gson().fromJson(response, LiveResponse::class.java)
            if (liveItemResponse.data.isNotEmpty()) {
                recycler.visibility = View.VISIBLE
                errorUpcomingCard.visibility = View.GONE
                val studyAdapter = UpcomingLiveAdapter(requireContext(), liveItemResponse.data)
                recycler.adapter = studyAdapter
                recycler.visibility = View.VISIBLE
                noCompletedSession.visibility = View.GONE
            }else{
                recycler.visibility = View.GONE
                showErrorMsg("You have no Upcoming Live Sessions right now")
            }
        } else {
            recycler.visibility = View.GONE
            showErrorMsg("You have no Upcoming Live Sessions right now")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkHelper.cancel("upcomingSessions")
    }
}