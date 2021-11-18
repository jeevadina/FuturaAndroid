package com.futuraeducation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.futuraeducation.R
import com.futuraeducation.adapter.StudyAdapter
import com.futuraeducation.fragment.practiceTest.ScheduledTestFragment
import com.futuraeducation.helper.MyProgressBar
import com.futuraeducation.model.LiveResponse
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.ApiUtils
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.network.OnNetworkResponse
import com.futuraeducation.network.URLHelper.getSessions
import com.futuraeducation.network.UrlConstants.kLIVE
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import kotlinx.android.synthetic.main.fragment_live.*
import kotlinx.android.synthetic.main.fragment_live.viewPager
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LiveFragment : Fragment(), OnNetworkResponse {

    private var loginData = LoginData()
    lateinit var networkHelper: NetworkHelper
    lateinit var myPreferences: MyPreferences
    lateinit var myProgressBar: MyProgressBar


    lateinit var liveFragmentTabAdapter: LiveFragmentTabAdapter
    private var titles = arrayOf<String>("Upcoming Live Sessions","Completed Sessions")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPreferences = MyPreferences(requireContext())
        networkHelper = NetworkHelper(requireContext())
        myProgressBar = MyProgressBar(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveFragmentTabAdapter = LiveFragmentTabAdapter(requireActivity(), titles)
        viewPager.adapter = liveFragmentTabAdapter

        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)

        requestSessions()

        refreshLayout.setOnRefreshListener {
            requestSessions()
            refreshLayout.isRefreshing = false
        }

        }

    override fun onStart() {
        super.onStart()

        TabLayoutMediator(slidingTabLayout, viewPager,
            ({ tab, position -> tab.text = titles[position] })
        ).attach()
        viewPager.currentItem = requireArguments().getInt("currentPosition",0)

    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            LiveFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun requestSessions() {
        if (!myProgressBar.isShowing()) {
            myProgressBar.show()
        }
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
            jsonObject.put("sessionTense", kLIVE)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        networkHelper.postCall(
            getSessions,
            jsonObject,
            "liveSessions",
            ApiUtils.getHeader(requireContext()),
            this
        )
    }

    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {
        if (myProgressBar.isShowing()) {
            myProgressBar.dismiss()
        }
        if (responseCode == networkHelper.responseSuccess && tag == "liveSessions") {
            val liveItemResponse = Gson().fromJson(response, LiveResponse::class.java)
            if (liveItemResponse.data.isNotEmpty()) {
                val studyAdapter = StudyAdapter(requireContext(), liveItemResponse.data)
                studyRecycler.adapter = studyAdapter
                errorLive.visibility = View.GONE
                recyclerScroll.visibility = View.VISIBLE
                upcomingSession.visibility = View.VISIBLE
                upcomingSession.background = null
                //StudyLabel.visibility = View.VISIBLE
                noUpcomingSession.visibility = View.GONE
                retryLive.visibility =View.GONE
                retryLive1.visibility =View.GONE
                retryLive2.visibility =View.GONE
            }else{
                upcomingSession.setBackgroundResource(R.drawable.ic_no_live)
                recyclerScroll.visibility = View.GONE
                errorLive.visibility = View.VISIBLE
               // StudyLabel.visibility = View.VISIBLE
                retryLive.visibility =View.VISIBLE
                retryLive1.visibility =View.VISIBLE
                retryLive2.visibility =View.VISIBLE
                retryLive.setOnClickListener {
                    requestSessions()
                }
            }
        }else{
            upcomingSession.setBackgroundResource(R.drawable.ic_no_live)
            recyclerScroll.visibility = View.GONE
            errorLive.visibility = View.VISIBLE
           // StudyLabel.visibility = View.VISIBLE
            retryLive.visibility =View.VISIBLE
            retryLive1.visibility =View.VISIBLE
            retryLive2.visibility =View.VISIBLE
            retryLive.setOnClickListener {
                requestSessions()
            }
        }
    }
    override fun onPause() {
        super.onPause()

        requireArguments().putInt("currentPosition", viewPager.currentItem)
    }
    class LiveFragmentTabAdapter(fm: FragmentActivity, val titles: Array<String>) : FragmentStateAdapter(fm) {
        override fun getItemCount(): Int {
            return titles.size
        }
        override fun createFragment(position: Int): Fragment {

            return when(position){
                0 -> UpcomingLiveFragment.newInstance(titles[position],"")
                1 -> CompletedLiveFragment.newInstance(titles[position],"")
                else -> ScheduledTestFragment.newInstance(titles[position],"")
            }
        }
    }
}