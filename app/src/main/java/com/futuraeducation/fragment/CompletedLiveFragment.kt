package com.futuraeducation.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.futuraeducation.R
import com.futuraeducation.activity.CompletedLiveActivity
import com.futuraeducation.adapter.CompletedLiveAdapter
import com.futuraeducation.helper.MyProgressBar
import com.futuraeducation.model.onBoarding.CompletedSession
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.ApiInterface
import com.futuraeducation.network.ApiUtils
import com.futuraeducation.network.RetroFitCall
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import kotlinx.android.synthetic.main.fragment_upcoming_live.*
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CompletedLiveFragment : Fragment(), CompletedListener, CompletedLiveAdapterListener {

    lateinit var myPreferences: MyPreferences
    private var loginData = LoginData()
    lateinit var myProgressBar: MyProgressBar
    lateinit var auditList: List<CompletedSession>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPreferences = MyPreferences(requireContext())
        myProgressBar = MyProgressBar(requireActivity())
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
        getApiCall(requireContext(), this)
        //showErrorMsg("No Completed Live Sessions")

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
            CompletedLiveFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun getApiCall(context: Context, listener: CompletedListener) {

        this.lifecycleScope.launch {
            if (!myProgressBar.isShowing()) {
                myProgressBar.show()
            }
            getCompletedLiveData()
        }
    }

    suspend fun getCompletedLiveData() : Boolean{
        val myBatchList = JSONArray()
        loginData.userDetail?.batchList?.forEach {
            myBatchList.put(it.id!!)
        }
        val myBranchIDs = JSONArray()
        loginData.userDetail?.branchList?.forEach {
            myBranchIDs.put(it.id!!)
        }

        val jsonObject = JSONObject()
        jsonObject.put("branchIds", myBranchIDs)
        jsonObject.put("coachingCentreId", loginData.userDetail?.coachingCenterId.toString())
        jsonObject.put("batchIds", myBatchList)

        RetroFitCall.retroFitCall()
        val service = RetroFitCall.retrofit.create(ApiInterface::class.java)
        val response = service.getData(jsonObject, ApiUtils.getHeader(context))
           return if (response.isSuccessful) {
                if (response.code() == 200) {
                    this.lifecycleScope.launch {
                        auditList = response.body()!!
                        recycler.visibility = View.VISIBLE
                        errorUpcomingCard.visibility = View.GONE
                        if (myProgressBar.isShowing()) {
                            myProgressBar.dismiss()
                        }
                        setAdapter(auditList)
                        Log.e("retoCall1", auditList.toString())
                    }
                    true
                } else {
                    this.lifecycleScope.launch {
                        if (myProgressBar.isShowing()) {
                            myProgressBar.dismiss()
                        }
                        showErrorMsg("You have no Completed Live Sessions right now")
                    }
                    false
                }
            } else {
               this.lifecycleScope.launch {
                   if (myProgressBar.isShowing()) {
                       myProgressBar.dismiss()
                   }
                   showErrorMsg("You have no Completed Live Sessions right now")
               }
               Log.e("retoCall1", response.isSuccessful.toString())
               false
            }
    }

    private fun setAdapter(completedLive: List<CompletedSession>) {
        val completedLiveAdapter = CompletedLiveAdapter(requireContext(), completedLive, this)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
        recycler.adapter = completedLiveAdapter
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
            getApiCall(requireContext(), this)
        }
    }

    override fun onSuccess(auditList: List<CompletedSession>) {
        //setAdapter(auditList)
    }

    override fun onFailure() {
       // myProgressBar.dismiss()
    }

    override fun onClicked(completedSession: CompletedSession) {
        val intent = Intent(requireContext(), CompletedLiveActivity::class.java)
        intent.putExtra("completedLive", Gson().toJson(completedSession))
        requireContext().startActivity(intent)
    }
}

interface CompletedListener {
    fun onSuccess(auditList: List<CompletedSession>)
    fun onFailure()
}

interface CompletedLiveAdapterListener {
    fun onClicked(completedSession : CompletedSession)
}