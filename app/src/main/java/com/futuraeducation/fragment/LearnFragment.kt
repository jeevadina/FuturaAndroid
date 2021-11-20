package com.futuraeducation.fragment

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.google.gson.Gson
import com.futuraeducation.R
import com.futuraeducation.activity.ChapterActivity
import com.futuraeducation.adapter.SubjectClickListener
import com.futuraeducation.adapter.SubjectsAdapter
import com.futuraeducation.adapter.VideoPlayedAdapter
import com.futuraeducation.database.AppDatabase
import com.futuraeducation.database.model.VideoPlayedItem
import com.futuraeducation.fragment.practiceTest.CourseListener
import com.futuraeducation.helper.exoplayer.ExoUtil
import com.futuraeducation.model.CourseResponse
import com.futuraeducation.model.Datum
import com.futuraeducation.model.OnEventData
import com.futuraeducation.model.VideoMaterial
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.ApiUtils
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.network.OnNetworkResponse
import com.futuraeducation.network.URLHelper
import com.futuraeducation.qrCode.QRCodeActivity
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import kotlinx.android.synthetic.main.fragment_learn.*
import kotlinx.android.synthetic.main.layout_toolbar_custom.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vimeoextractor.OnVimeoExtractionListener
import vimeoextractor.VimeoExtractor
import vimeoextractor.VimeoVideo
import java.util.*

class LearnFragment : Fragment(), CourseListener, VideoPlayedAdapter.ActionCallback,
    OnNetworkResponse, SubjectClickListener {

    private lateinit var courseRecycler: RecyclerView
    private var loginData = LoginData()
    lateinit var myPreferences: MyPreferences
    lateinit var networkHelper: NetworkHelper
    var batchIds: String? = null
    var courseId: String? = null
    lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPreferences = MyPreferences(requireContext())
        networkHelper = NetworkHelper(requireContext())
        db = AppDatabase.getInstance(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginData =
            Gson().fromJson(myPreferences.getString(Define.LOGIN_DATA), LoginData::class.java)

        Log.e("popAns","1")

        batchIds = if (loginData.userDetail?.batchList!![0].additionalCourseId.isNullOrEmpty()) {
            loginData.userDetail?.batchList!![0].id
        } else {
            loginData.userDetail?.batchList!![0].additionalCourseId
        }

        if (!courseId.isNullOrEmpty()){
            requestSessions(courseId!!)
        } else if (loginData.userDetail?.batchList?.get(0)?.additionalCourseId.isNullOrEmpty()) {
            requestSessions(loginData.userDetail?.batchList?.get(0)?.courseId!!)
        } else requestSessions(loginData.userDetail?.batchList?.get(0)?.additionalCourseId!!)
        setMenuItems()
       // selectedCourseTxt.text = "Course Name: ${loginData.userDetail?.batchList?.get(0)?.course?.courseName!!}"
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
                    Log.e("popThread", "1234")

                    EventBus.getDefault().post(OnEventData(i))
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
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            openQRCodeScreen()
        }
    }
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()
        if (db.videoDao.getAll().isNullOrEmpty()) {
            previosVideo.visibility = View.GONE
        } else {
            previosVideo.visibility = View.VISIBLE
        }
        courseCall()
    }

    private fun requestSessions(batchId: String) {
        //stateful.showProgress()
        networkHelper.getCall(
            URLHelper.courseURL + batchId,
            "getCourse",
            ApiUtils.getHeader(requireContext()),
            this
        )
    }


    @SuppressLint("WrongConstant")
    private fun courseCall() {
        val videoRecyclerView = view?.findViewById(R.id.playedRecycler) as RecyclerView
        val videoAdapter =
            VideoPlayedAdapter(requireActivity(), "0", db.videoDao.getAll(), null, this)
        videoRecyclerView.adapter = videoAdapter
    }

    fun showErrorMsg(errorMsg: String) {
        stateful.showOffline()
        stateful.setOfflineText(errorMsg)
        stateful.setOfflineImageResource(R.drawable.icon_error)
        stateful.setOfflineRetryOnClickListener {
        }
    }

    override fun onCourseClicked(batchId: String, id: String, position: Int) {
        courseRecycler.layoutManager?.scrollToPosition(position)
        batchIds = id
    }

    override fun onVideoClickListener(videoPlayedItem: VideoPlayedItem) {
        if (videoPlayedItem.videoUrl!!.contains("vimeo", true)) {
            playVideo(videoPlayedItem.videoTitle!!, videoPlayedItem.videoUrl!!)
        }else{
            ExoUtil.buildMediaItems(
                requireActivity(),
                childFragmentManager,
                videoPlayedItem.videoTitle!!,
                videoPlayedItem.videoUrl!!,
                false
            )
        }
    }

    override fun onVideoClickListener1(videoPlayedItem: VideoMaterial) {
    }

    private fun playVideo(title: String, id: String) {
        val videoId = id.replace("https://vimeo.com/", "")
        VimeoExtractor.getInstance()
            .fetchVideoWithIdentifier(videoId, null, object : OnVimeoExtractionListener {
                override fun onSuccess(video: VimeoVideo) {
                    val hdStream = video.streams["720p"]
                    println("VIMEO VIDEO STREAM$hdStream")
                    hdStream?.let {
                        requireActivity().runOnUiThread {
                            //code that runs in main
                            navigateVideoPlayer(title, it)
                        }
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    Log.d("failure", throwable.message!!)
                }
            })
    }

    fun navigateVideoPlayer(title: String, url: String) {
        ExoUtil.buildMediaItems(
            requireActivity(),
            childFragmentManager, title,
            url, false
        )
    }

    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {
        if (responseCode == networkHelper.responseSuccess && tag == "getCourse") {
            val courseResponse = Gson().fromJson(response, CourseResponse::class.java)
            subjectCall(courseResponse.data!!)
            EventBus.getDefault().post(courseResponse)
        } else {
            showErrorMsg(requireActivity().getString(R.string.sfl_default_error))
        }
    }

    override fun onSubjectClicked(Id: String, batchId: String, title: String) {
        val intent = Intent(requireContext(), ChapterActivity::class.java)
        intent.putExtra("id", Id)
        intent.putExtra("batchId", batchIds)
        intent.putExtra("title", title)
        startActivity(intent)
    }

    private fun subjectCall(subjectList: ArrayList<Datum>) {
        stateful.showContent()
        if (subjectList.size > 0) {

            val adapter = SubjectsAdapter(requireContext(), subjectList, batchIds.toString(), this)
            //now adding the adapter to recyclerview
            val layoutManager = FlexboxLayoutManager(requireContext())
            layoutManager.justifyContent = JustifyContent.CENTER
            layoutManager.alignItems = AlignItems.CENTER
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.flexWrap = FlexWrap.WRAP
         //   subjectsRecycler.layoutManager = layoutManager
            subjectsRecycler.adapter = adapter
        } else {
            showErrorMsg("No subject found.")
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: OnEventData?) {
        Log.e("popThread","123")
        val data = loginData.userDetail?.batchList?.get(event?.batchPosition!!)
        requestSessions(loginData.userDetail?.batchList?.get(event?.batchPosition!!)?.courseId!!)
        //selectedCourseTxt.text = "Course Name: ${loginData.userDetail?.batchList?.get(event?.batchPosition!!)?.course?.courseName!!}"
        batchIds = if (!data?.additionalCourseId.isNullOrEmpty()){
            data?.additionalCourseId
        }else{
            data?.id
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}