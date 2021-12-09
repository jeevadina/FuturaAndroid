package com.futuraeducation.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.futuraeducation.R
import com.futuraeducation.activity.AttachmentActivity
import com.futuraeducation.adapter.assignment.AssignmentAdapter
import com.futuraeducation.helper.MyProgressBar
import com.futuraeducation.model.OnEventData
import com.futuraeducation.model.assignment.AssignmentModel
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.ApiInterface
import com.futuraeducation.network.ApiUtils
import com.futuraeducation.network.RetroFitCall
import com.futuraeducation.qrCode.QRCodeActivity
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_assignment.*
import kotlinx.android.synthetic.main.layout_toolbar_custom.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import java.util.ArrayList


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AssignmentFragment : Fragment(), AssignmentListener {

    lateinit var myPreferences: MyPreferences
    private var loginData = LoginData()
    lateinit var myProgressBar: MyProgressBar
    lateinit var auditList: List<AssignmentModel>

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
        return inflater.inflate(R.layout.fragment_assignment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)
        //showErrorMsg("No Completed Live Sessions")

        if (loginData.role?.equals("COACH")!!) {
            noAssignmentTxt.visibility = View.GONE
            teacherAssign.visibility = View.VISIBLE
        } else {
            /* teacherAssign.visibility = View.GONE
             noAssignmentTxt.visibility = View.VISIBLE*/
            teacherAssign.visibility = View.GONE
            getAssignments(loginData.userDetail?.batchList?.get(0)?.id!!)
        }

        setMenuItems()
    }

    private fun getAssignments(batchId: String) {

        CoroutineScope(Dispatchers.IO).launch {
            RetroFitCall.retroFitCall()
            val service = RetroFitCall.retrofit.create(ApiInterface::class.java)
            val response = service.getAssignments(
                batchId,
                ApiUtils.getHeader(context)
            )

            if (response.isSuccessful) {
                if (response.code() == 200) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        auditList = response.body()!!
                        teacherAssign.visibility = View.GONE
                        noAssignmentTxt.visibility = View.GONE
                        recyclerAssignment.visibility = View.VISIBLE
                        myProgressBar.dismiss()
                        if (!auditList.isNullOrEmpty()) {
                            setAdapter(auditList)
                        }else{
                            teacherAssign.visibility = View.GONE
                            recyclerAssignment.visibility = View.GONE
                            noAssignmentTxt.visibility = View.VISIBLE
                        }
                        Log.e("retoCall1", auditList.toString())

                    }
                } else {
                    viewLifecycleOwner.lifecycleScope.launch {
                        myProgressBar.dismiss()
                        teacherAssign.visibility = View.GONE
                        recyclerAssignment.visibility = View.GONE
                        noAssignmentTxt.visibility = View.VISIBLE
                    }
                }
            } else {
                viewLifecycleOwner.lifecycleScope.launch {
                        myProgressBar.dismiss()
                    teacherAssign.visibility = View.GONE
                    recyclerAssignment.visibility = View.GONE
                    noAssignmentTxt.visibility = View.VISIBLE
                }
                Log.e("retoCall1", response.isSuccessful.toString())
            }
        }
    }

    private fun setAdapter(completedLive: List<AssignmentModel>) {
        recyclerAssignment.visibility = View.VISIBLE
        val completedLiveAdapter = AssignmentAdapter(requireContext(), completedLive, this)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerAssignment.layoutManager = layoutManager
        recyclerAssignment.adapter = completedLiveAdapter
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
            AssignmentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setMenuItems() {
        userNameTool.text = "Hi "+loginData.userDetail?.userName.toString()

        val newList = ArrayList<String>()
        newList.apply {
            loginData.userDetail?.batchList?.forEach {
                this.add(it.batchName.toString())
            }
            Log.e("popData", newList.toString())
            val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, newList)
            batchSpinner.adapter = adapter

            batchSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    getAssignments(loginData.userDetail?.batchList?.get(i)?.id!!)
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        }

        qrScannerTool.setOnClickListener{
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                openQRCodeScreen()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    100
                )
            }
        }
    }

    private fun openQRCodeScreen() {
        val intent = Intent(requireContext(), QRCodeActivity::class.java)
        startActivity(intent)
    }

    override fun onAssignmentClicked(url: String) {
        val intent = Intent(requireContext(), AttachmentActivity::class.java)
        intent.putExtra("url", url)
        requireContext().startActivity(intent)
    }
}

interface AssignmentListener{
    fun onAssignmentClicked(url: String)
}