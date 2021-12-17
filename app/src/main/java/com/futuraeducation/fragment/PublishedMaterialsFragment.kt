package com.futuraeducation.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.futuraeducation.R
import com.futuraeducation.model.onBoarding.Course
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.ApiInterface
import com.futuraeducation.network.ApiUtils
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.network.RetroFitCall
import com.futuraeducation.profile.ProfileActivity
import com.futuraeducation.qrCode.QRCodeActivity
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_assignment.*
import kotlinx.android.synthetic.main.fragment_publish_materials.*
import kotlinx.android.synthetic.main.fragment_upcoming_live.*
import kotlinx.android.synthetic.main.layout_toolbar_custom.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import android.text.Editable

import android.text.TextWatcher
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.futuraeducation.model.*
import com.futuraeducation.model.live.Batch
import com.futuraeducation.model.publish.PublishData
import com.futuraeducation.model.publish.PublishDataValue
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PublishedMaterialsFragment : Fragment() {

    lateinit var networkHelper: NetworkHelper
    lateinit var myPreferences: MyPreferences
    private var loginData = LoginData()

    lateinit var courseList: ArrayList<Datum>
    lateinit var courseSubjectList: ArrayList<Subject>
    lateinit var subjectChapterList: ArrayList<Subject>
    lateinit var chapterTopicsList: ArrayList<Subject>
    lateinit var materialList: ArrayList<Material>
    lateinit var branchList: ArrayList<Branch>
    lateinit var batchList: ArrayList<Batch>
    var publishData: PublishData? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPreferences = MyPreferences(requireContext())
        networkHelper = NetworkHelper(requireContext())
        publishData = PublishData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publish_materials, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)

        setMenuItems()
    }


    override fun onStart() {
        super.onStart()
        logoTool.setOnClickListener {
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))
        }
        if(loginData.userDetail != null && loginData.userDetail!!.coachingCentre != null) {
            val institute = loginData.userDetail!!.coachingCentre!!.coachingCentreName
            val items = listOf(institute)
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, items)
            (textFieldInstitute.editText as? AutoCompleteTextView)?.setAdapter(adapter)

            (textFieldInstitute.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
                lifecycleScope.launch {
                    publishData!!.coachingCentreId = loginData.userDetail!!.coachingCentre!!.id!!
                    statefulLayout.showProgress()
                    getCourseList()
                }
            }
        }

        (textFieldPublishDate.editText as? AutoCompleteTextView)?.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build().apply {
            addOnPositiveButtonClickListener { dateInMillis -> onDateSelected(dateInMillis) }
        }
            datePicker.show(childFragmentManager,MaterialDatePicker::class.java.canonicalName)
        }
        (textFieldPublishTime.editText as? AutoCompleteTextView)?.setOnClickListener {

            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setTitleText("Select time")
                    .build().apply {
                        addOnPositiveButtonClickListener {
                            onTimeSelected(this)
                        }
                    }
            picker.show(childFragmentManager,MaterialDatePicker::class.java.canonicalName)


        }

        btnPublish.setOnClickListener {
            validatePublishData()
        }
    }

    private fun onDateSelected(dateTimeStampInMillis: Long) {
        val dateFormater = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date = dateFormater.format(dateTimeStampInMillis)
        (textFieldPublishDate.editText as? AutoCompleteTextView)?.setText(date)
        publishData!!.publishDate = date
    }

    private fun onTimeSelected(picker: MaterialTimePicker) {
        val newHour: Int = picker.hour
        val newMinute: Int = picker.minute
        val time = "$newHour:$newMinute"
        (textFieldPublishTime.editText as? AutoCompleteTextView)?.setText(time)
        publishData!!.publishTime = time
    }
    companion object {
        fun newInstance(param1: String, param2: String) =
            PublishedMaterialsFragment().apply {
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
                    //getAssignments(loginData.userDetail?.batchList?.get(i)?.id!!)
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

    private suspend fun getCourseList() : Boolean{

        RetroFitCall.retroFitCall(requireContext())
        val service = RetroFitCall.retrofit.create(ApiInterface::class.java)
        val response = service.getCoursesByCoachingCenter(publishData!!.coachingCentreId!!, ApiUtils.getHeader(context))
        return if (response.isSuccessful) {
            if (response.code() == 200) {
                this.lifecycleScope.launch {
                    courseList = response.body()!! as ArrayList<Datum>
                    statefulLayout.showContent()
                    displayCourseDropDown()
                }
                true
            } else {
                retryGetCourseList()
                false
            }
        } else {
            retryGetCourseList()
            false
        }
    }

    private fun retryGetCourseList(){
        try {
            lifecycleScope.launch {
                statefulLayout.showOffline()
                statefulLayout.setOfflineText("Failed to getting Course List, Try Again.")
                statefulLayout.setOfflineRetryOnClickListener {
                    statefulLayout.showProgress()
                    lifecycleScope.launch {
                        getCourseList()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun displayCourseDropDown(){
        val items = ArrayList<String>()
        for(i in 0 until courseList.size){
            items.add((courseList[i] as Datum).courseName!!)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, items)
        (textFieldCourse.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        (textFieldCourse.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
            lifecycleScope.launch {
                publishData!!.courseId = courseList[i].id!!
                statefulLayout.showProgress()
                getBranchList()
            }
        }
    }
    private suspend fun getBranchList() : Boolean{

        RetroFitCall.retroFitCall(requireContext())
        val service = RetroFitCall.retrofit.create(ApiInterface::class.java)
        val response = service.getBranchesForCourse(loginData.userDetail!!.coachingCentre!!.id!!,publishData!!.courseId!!, ApiUtils.getHeader(context))
        return if (response.isSuccessful) {
            if (response.code() == 200) {
                this.lifecycleScope.launch {
                    branchList = response.body()!! as ArrayList<Branch>
                    statefulLayout.showContent()
                    displayBranchDropDown()
                }
                true
            } else {
                retryGetBranchList()
                false
            }
        } else {
            retryGetBranchList()
            false
        }
    }
    private fun retryGetBranchList(){
        try {
            lifecycleScope.launch {
                statefulLayout.showOffline()
                statefulLayout.setOfflineText("Failed to getting Branch List, Try Again.")
                statefulLayout.setOfflineRetryOnClickListener {
                    statefulLayout.showProgress()
                    lifecycleScope.launch {
                        getBranchList()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun displayBranchDropDown(){
        val items = ArrayList<String>()
        for(i in 0 until branchList.size){
            items.add(branchList[i].branchName)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, items)
        (textFieldBranch.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        (textFieldBranch.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
            lifecycleScope.launch {
                val publishDataValue = PublishDataValue()
                publishDataValue.value = branchList[i].id
                publishDataValue.label = branchList[i].branchName
                if(publishData!!.branchId == null){
                    publishData!!.branchId = ArrayList()
                }
                publishData!!.branchId!!.add(publishDataValue)
                statefulLayout.showProgress()
                getBatchList()
            }
        }
    }
    private suspend fun getBatchList() : Boolean{

        val data: MutableMap<String, String> = HashMap()
        try {
            data["coachingCentreId"] = publishData!!.coachingCentreId!!
            data["branchIds"] = publishData!!.branchId!![0].value!!
            data["courseId"] = publishData!!.courseId!!
        } catch (e: Exception) {
            e.printStackTrace()
        }

        RetroFitCall.retroFitCall(requireContext())
        val service = RetroFitCall.retrofit.create(ApiInterface::class.java)
        val response = service.getBatchesByCourse(data, ApiUtils.getHeader(context))
        return if (response.isSuccessful) {
            if (response.code() == 200) {
                this.lifecycleScope.launch {
                    batchList = response.body()!! as ArrayList<Batch>
                    statefulLayout.showContent()
                    displayBatchDropDown()
                }
                true
            } else {
                retryGetBatchList()
                false
            }
        } else {
            retryGetBatchList()
            false
        }
    }
    private fun retryGetBatchList(){
        try {
            lifecycleScope.launch {
                statefulLayout.showOffline()
                statefulLayout.setOfflineText("Failed to getting Batch List, Try Again.")
                statefulLayout.setOfflineRetryOnClickListener {
                    statefulLayout.showProgress()
                    lifecycleScope.launch {
                        getBatchList()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun displayBatchDropDown(){
        val items = ArrayList<String>()
        for(i in 0 until batchList.size){
            items.add(batchList[i].batchName!!)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, items)
        (textFieldBatch.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        (textFieldBatch.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
            lifecycleScope.launch {
                val publishDataValue = PublishDataValue()
                publishDataValue.value = batchList[i].id
                publishDataValue.label = batchList[i].batchName
                if(publishData!!.batchId == null){
                    publishData!!.batchId = ArrayList()
                }
                publishData!!.batchId!!.add(publishDataValue)
                if(publishData!!.batchIds == null){
                    publishData!!.batchIds = ArrayList()
                }
                publishData!!.batchIds!!.add(batchList[i].id!!)

                statefulLayout.showProgress()
                getSubjectList()
            }
        }
    }
    private suspend fun getSubjectList() : Boolean{

        RetroFitCall.retroFitCall(requireContext())
        val service = RetroFitCall.retrofit.create(ApiInterface::class.java)
        val response = service.getCourseSubject(publishData!!.courseId!!, ApiUtils.getHeader(context))
        return if (response.isSuccessful) {
            if (response.code() == 200) {
                this.lifecycleScope.launch {
                    val subjectData = response.body()!!
                    courseSubjectList = subjectData.data as ArrayList<Subject>
                    statefulLayout.showContent()
                    displaySubjectDropDown()
                }
                true
            } else {
                retrySubjectList()
                false
            }
        } else {
            retrySubjectList()
            false
        }
    }
    private fun retrySubjectList(){
        try {
            lifecycleScope.launch {
                statefulLayout.showOffline()
                statefulLayout.setOfflineText("Failed to getting Subject List, Try Again.")
                statefulLayout.setOfflineRetryOnClickListener {
                    statefulLayout.showProgress()
                    lifecycleScope.launch {
                        getSubjectList()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun displaySubjectDropDown(){
        val items = ArrayList<String>()
        for(i in 0 until courseSubjectList.size){
            items.add(courseSubjectList[i].courseName!!)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, items)
        (textFieldSubject.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        (textFieldSubject.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
            lifecycleScope.launch {
                publishData!!.subjectId = courseSubjectList[i].id
                statefulLayout.showProgress()
                getChapterList()
            }
        }
    }
    private suspend fun getChapterList() : Boolean{

        RetroFitCall.retroFitCall(requireContext())
        val service = RetroFitCall.retrofit.create(ApiInterface::class.java)
        val response = service.getSubjectChapter(publishData!!.subjectId!!, ApiUtils.getHeader(context))
        return if (response.isSuccessful) {
            if (response.code() == 200) {
                this.lifecycleScope.launch {
                    val subjectData = response.body()!!
                    subjectChapterList = subjectData.data as ArrayList<Subject>
                    statefulLayout.showContent()
                    displayChapterDropDown()
                }
                true
            } else {
                retryChapterList()
                false
            }
        } else {
            retryChapterList()
            false
        }
    }
    private fun retryChapterList(){
        try {
            lifecycleScope.launch {
                statefulLayout.showOffline()
                statefulLayout.setOfflineText("Failed to getting Chapter List, Try Again.")
                statefulLayout.setOfflineRetryOnClickListener {
                    statefulLayout.showProgress()
                    lifecycleScope.launch {
                        getChapterList()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun displayChapterDropDown(){
        val items = ArrayList<String>()
        for(i in 0 until subjectChapterList.size){
            items.add(subjectChapterList[i].courseName!!)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, items)
        (textFieldChapter.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        (textFieldChapter.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
            lifecycleScope.launch {
                if(publishData!!.chapterId == null){
                    publishData!!.chapterId = ArrayList()
                }
                publishData!!.chapterId!!.add(subjectChapterList[i].id)
                statefulLayout.showProgress()
                getTopicsList()
            }
        }
    }
    private suspend fun getTopicsList() : Boolean{

        RetroFitCall.retroFitCall(requireContext())
        val service = RetroFitCall.retrofit.create(ApiInterface::class.java)
        val response = service.getChapterTopics(publishData!!.chapterId!![0], ApiUtils.getHeader(context))
        return if (response.isSuccessful) {
            if (response.code() == 200) {
                this.lifecycleScope.launch {
                    val subjectData = response.body()!!
                    chapterTopicsList = subjectData.data as ArrayList<Subject>
                    statefulLayout.showContent()
                    displayTopicsDropDown()
                }
                true
            } else {
                retryTopicsList()
                false
            }
        } else {
            retryTopicsList()
            false
        }
    }
    private fun retryTopicsList(){
        try {
            lifecycleScope.launch {
                statefulLayout.showOffline()
                statefulLayout.setOfflineText("Failed to getting Topics List, Try Again.")
                statefulLayout.setOfflineRetryOnClickListener {
                    statefulLayout.showProgress()
                    lifecycleScope.launch {
                        getTopicsList()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun displayTopicsDropDown(){
        val items = ArrayList<String>()
        for(i in 0 until chapterTopicsList.size){
            items.add(chapterTopicsList[i].courseName!!)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, items)
        (textFieldTopic.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        (textFieldTopic.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
            lifecycleScope.launch {
                publishData!!.topicId = chapterTopicsList[i].id
                statefulLayout.showProgress()
                getMaterialList()
            }
        }
    }
    private suspend fun getMaterialList() : Boolean{

        val data: MutableMap<String, String> = HashMap()
        try {
            data["chapterId"] = publishData!!.topicId!!
            data["batchId"] = publishData!!.batchId!![0].value!!
        } catch (e: Exception) {
            e.printStackTrace()
        }


        RetroFitCall.retroFitCall(requireContext())
        val service = RetroFitCall.retrofit.create(ApiInterface::class.java)
        val response = service.getMaterial(data, ApiUtils.getHeader(context))
        return if (response.isSuccessful) {
            if (response.code() == 200) {
                this.lifecycleScope.launch {
                    materialList = response.body()!! as ArrayList<Material>
                    statefulLayout.showContent()
                    displayMaterialDropDown()
                }
                true
            } else {
                retryMaterialList()
                false
            }
        } else {
            retryMaterialList()
            false
        }
    }
    private fun retryMaterialList(){
        try {
            lifecycleScope.launch {
                statefulLayout.showOffline()
                statefulLayout.setOfflineText("Failed to getting Material List, Try Again.")
                statefulLayout.setOfflineRetryOnClickListener {
                    statefulLayout.showProgress()
                    lifecycleScope.launch {
                        getMaterialList()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun displayMaterialDropDown(){
        val items = ArrayList<String>()
        for(i in 0 until materialList.size){
            items.add(materialList[i].courseName!!)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_layout, items)
        (textFieldMaterial.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        (textFieldMaterial.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
            lifecycleScope.launch {
                publishData!!.materialId = materialList[i].id
                //statefulLayout.showProgress()
                //getBranchList(batchList!![i].id!!)
            }
        }
    }

    private fun validatePublishData(){
        if(publishData == null){
            (textFieldInstitute.editText as? AutoCompleteTextView)?.error = "Please Select institute"
        }else if(publishData!!.coachingCentreId == null){
            (textFieldCourse.editText as? AutoCompleteTextView)?.error = "Please Select institute"
        }else if(publishData!!.courseId == null){
            (textFieldCourse.editText as? AutoCompleteTextView)?.error = "Please Select course"
        }else if(publishData!!.branchId == null){
            (textFieldBranch.editText as? AutoCompleteTextView)?.error = "Please Select branch"
        }else if(publishData!!.batchId == null){
            (textFieldBatch.editText as? AutoCompleteTextView)?.error = "Please Select batch"
        }else if(publishData!!.publishDate == null){
            (textFieldPublishDate.editText as? AutoCompleteTextView)?.error = "Please Select date"
        }else if(publishData!!.publishTime == null){
            (textFieldPublishTime.editText as? AutoCompleteTextView)?.error = "Please Select time"
        }else {
            lifecycleScope.launch {
                publishMaterials()
            }
        }
    }

    private suspend fun publishMaterials() : Boolean{

        //val jsonObject = JSONObject()
       val jsonObject = Gson().fromJson(Gson().toJson(publishData),JsonObject::class.java)

        Log.d("jsonObject",Gson().toJson(publishData))

        RetroFitCall.retroFitCall(requireContext())
        val service = RetroFitCall.retrofit.create(ApiInterface::class.java)
        val response = service.publishMaterials(jsonObject, ApiUtils.getHeader(context))
        return if (response.isSuccessful) {
            if (response.code() == 200) {
                this.lifecycleScope.launch {
                    val responseData = response.body()!!

                    Toast.makeText(requireContext(),responseData.data, Toast.LENGTH_LONG).show()
                    clearDropDown()
                }
                true
            } else {
                this.lifecycleScope.launch {
//                    if (myProgressBar.isShowing()) {
//                        myProgressBar.dismiss()
//                    }
                    //     showErrorMsg("You have no Completed Live Sessions right now")
//                    statefulLayout.showOffline()
//                    statefulLayout.setOfflineRetryOnClickListener {
//                        statefulLayout.showContent()
//                       // clearDropDown()
//                    }
                    Toast.makeText(requireContext(),"Something went wrong", Toast.LENGTH_LONG).show()
                }
                false
            }
        } else {
            this.lifecycleScope.launch {
//                statefulLayout.showOffline()
//                statefulLayout.setOfflineRetryOnClickListener {
//                    statefulLayout.showContent()
//                   // clearDropDown()
//                }
                Toast.makeText(requireContext(),"Something went wrong", Toast.LENGTH_LONG).show()
            }
            false
        }
    }


    private fun clearDropDown()
    {
        publishData = PublishData()
        (textFieldInstitute.editText as? AutoCompleteTextView)?.setText("")
        (textFieldCourse.editText as? AutoCompleteTextView)?.setText("")
        (textFieldBranch.editText as? AutoCompleteTextView)?.setText("")
        (textFieldBatch.editText as? AutoCompleteTextView)?.setText("")
        (textFieldSubject.editText as? AutoCompleteTextView)?.setText("")
        (textFieldChapter.editText as? AutoCompleteTextView)?.setText("")
        (textFieldTopic.editText as? AutoCompleteTextView)?.setText("")
        (textFieldMaterial.editText as? AutoCompleteTextView)?.setText("")
        (textFieldPublishDate.editText as? AutoCompleteTextView)?.setText("")
        (textFieldPublishTime.editText as? AutoCompleteTextView)?.setText("")
    }
}