package com.futuraeducation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.futuraeducation.R
import com.futuraeducation.network.ApiUtils
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.network.OnNetworkResponse
import com.futuraeducation.network.URLHelper
import com.futuraeducation.onBoarding.LoginActivity
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import kotlinx.android.synthetic.main.fragment_bottom_sheet_dialog_logout.*
import org.json.JSONObject

class LogOutBottomSheetFragment : BottomSheetDialogFragment(), OnNetworkResponse {

    lateinit var networkHelper: NetworkHelper
    lateinit var myPreferences: MyPreferences
    lateinit var mRemoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPreferences = MyPreferences(requireContext())
        networkHelper = NetworkHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_dialog_logout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancelButton.setOnClickListener {
            dismiss()
        }
        yesButton.setOnClickListener {
            logoutRequest()
            clearUserData()
        }
    }

    private fun clearUserData() {
        myPreferences.clearAllData()
    }

    private fun logoutRequest() {
        yesButton.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        val jsonObject = JSONObject()
        jsonObject.put("id", myPreferences.getString(Define.ACCESS_TOKEN).toString())

        networkHelper.postCall(
            URLHelper.logout,
            jsonObject,
            "logout",
            ApiUtils.getAuthorizationHeader(requireContext(), jsonObject.toString().length),
            this
        )
    }

    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {
        if (responseCode == networkHelper.responseSuccess && tag == "logout") {
            Toast.makeText(requireContext(), "logout successful", Toast.LENGTH_LONG).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            requireContext().startActivity(intent)
            myPreferences.setString(Define.LOGIN_DATA, "")
            activity?.finishAffinity()
        } else {
            yesButton.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
            Toast.makeText(
                requireContext(),
                "logout Failed...Please try sometimes later",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}