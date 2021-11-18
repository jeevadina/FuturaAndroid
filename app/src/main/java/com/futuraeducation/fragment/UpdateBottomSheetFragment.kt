package com.futuraeducation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.futuraeducation.R
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import kotlinx.android.synthetic.main.fragment_bottom_sheet_dialog_update.*
import android.content.DialogInterface
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.futuraeducation.utils.Utils


class UpdateBottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var mRemoteConfig: FirebaseRemoteConfig
    lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPreferences = MyPreferences(requireContext())
        mRemoteConfig = FirebaseRemoteConfig.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_dialog_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtTitle.text = mRemoteConfig.getString(Define.APP_UPDATE_TITLE)
        txtContent.text = mRemoteConfig.getString(Define.APP_UPDATE_MESSAGE)

        btnCancel.setOnClickListener {
            dismiss()
        }
        btnUpdate.setOnClickListener {
           Utils.loadUrl(requireActivity(),mRemoteConfig.getString(Define.APP_PLAY_STORE_URL))
            dismiss()
        }
    }
//
//    private fun clearUserData() {
//        myPreferences.clearAllData()
//    }

//    private fun logoutRequest() {
//        btnUpdate.visibility = View.INVISIBLE
//        progressBar.visibility = View.VISIBLE
//        val jsonObject = JSONObject()
//        jsonObject.put("id", myPreferences.getString(Define.ACCESS_TOKEN).toString())
//
//        networkHelper.postCall(
//            URLHelper.logout,
//            jsonObject,
//            "logout",
//            ApiUtils.getAuthorizationHeader(requireContext(), jsonObject.toString().length),
//            this
//        )
//    }

//    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {
//        if (responseCode == networkHelper.responseSuccess && tag == "logout") {
//            Toast.makeText(requireContext(), "logout successful", Toast.LENGTH_LONG).show()
//            val intent = Intent(requireContext(), LoginActivity::class.java)
//            requireContext().startActivity(intent)
//            myPreferences.setString(Define.LOGIN_DATA, "")
//            activity?.finishAffinity()
//        } else {
//            btnUpdate.visibility = View.VISIBLE
//            progressBar.visibility = View.INVISIBLE
//            Toast.makeText(
//                requireContext(),
//                "logout Failed...Please try sometimes later",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }

    override fun onCancel(dialog: DialogInterface) {

    }

}