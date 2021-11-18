package com.futuraeducation.learn.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.futuraeducation.R
import com.futuraeducation.adapter.VideoPlayedAdapter
import com.futuraeducation.database.AppDatabase
import com.futuraeducation.database.model.VideoPlayedItem
import com.futuraeducation.helper.exoplayer.ExoUtil.buildMediaItems
import com.futuraeducation.model.VideoMaterial
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.ImageLoader
import com.futuraeducation.utils.MyPreferences
import kotlinx.android.synthetic.main.fragment_video.*
import vimeoextractor.OnVimeoExtractionListener
import vimeoextractor.VimeoExtractor
import vimeoextractor.VimeoVideo
import java.io.*
import java.lang.reflect.Type


class VideoFragment : Fragment(),VideoPlayedAdapter.ActionCallback {

    private lateinit var downloadFolder: File
    private lateinit var fileName: String
    lateinit var sharedPreferences: SharedPreferences
    lateinit var myPreferences: MyPreferences
    lateinit var file: File
    private var pos: Int ?= null
    var videoData = ArrayList<VideoMaterial>()
    lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("MySharedPref", 0)
        myPreferences = MyPreferences(requireContext())
        downloadFolder = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!
        db = AppDatabase.getInstance(requireContext())!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listType: Type = object : TypeToken<ArrayList<VideoMaterial?>?>() {}.type
        videoData = Gson().fromJson(myPreferences.getString(Define.VIDEO_DATA), listType)
        pos = myPreferences.getInt(Define.VIDEO_POS)

        val myList = ArrayList<VideoMaterial>()
        var myPos = pos
        val listSize = videoData.size - (pos!! + 1)

        for (i in 1..listSize) {
            myList.add(
                VideoMaterial(
                    description = videoData[myPos!! + i].description,
                    videoData[myPos!! + i].courseName,
                    null,
                    videoData[myPos!! + i].filePath,
                    null,
                    null,
                    videoData[myPos!! + i].title,
                    videoData[myPos!! + i].videoId
                )
            )
        }

        title.text = videoData[pos!!].title

        if (!myList.isNullOrEmpty()) {
            upNext.visibility = View.VISIBLE
            setAdapter(myList)
        } else {
            upNext.visibility = View.GONE
        }


        if (videoData != null) {
            videoData[pos!!].filePath?.let {
                ImageLoader.loadFull(
                    requireContext(),
                    it, videoPlaceholder
                )
            }
        }
        ImageLoader.loadFull(requireContext(), videoData[pos!!].filePath!!, videoPlaceholder)

        val myClass = VideoPlayedItem(
            videoUrl = videoData[pos!!].description.toString(),
            lastPlayed = "4:10",
            logoImg = videoData[pos!!].filePath.toString(),
            videoTitle = videoData[pos!!].title.toString()
        )
        db.videoDao.addVideo(myClass)
    }

    override fun onStart() {
        super.onStart()

        videoPlaceholder.setOnClickListener {
            if(videoData != null) {
                if (videoData[pos!!].description!!.contains("vimeo", true)) {
                    loadVMEOVideos(videoData[pos!!].courseName!!, videoData[pos!!].description!!)
                }else{
                    buildMediaItems(
                        requireActivity(),
                        childFragmentManager,videoData[pos!!].courseName!!,videoData[pos!!].description!!,false)
                }
            }
        }
    }

    fun loadVMEOVideos(title: String,url: String){
        val videoId = url.replace("https://vimeo.com/", "")
        VimeoExtractor.getInstance()
            .fetchVideoWithIdentifier(videoId, null, object : OnVimeoExtractionListener {
                override fun onSuccess(video: VimeoVideo) {
                    val hdStream = video.streams["720p"]
                    println("VIMEO VIDEO STREAM$hdStream")
                    hdStream?.let {
                        requireActivity().runOnUiThread {
                            //code that runs in main
                            buildMediaItems(
                                requireActivity(),
                                childFragmentManager,title,
                                it,false)
                        }
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    Log.d("failure", throwable.message!!)
                }
            })
    }

    fun setAdapter(myList: ArrayList<VideoMaterial>){
        val videoRecyclerView = view?.findViewById(R.id.upNextRecycler) as RecyclerView
        val videoAdapter = VideoPlayedAdapter(requireActivity(), "1",null, myList , this)
        videoRecyclerView.adapter = videoAdapter
    }

    override fun onVideoClickListener(videoPlayedItem: VideoPlayedItem) {

    }

    override fun onVideoClickListener1(videoPlayedItem: VideoMaterial) {
        if (videoPlayedItem.description!!.contains("vimeo", true)) {
            loadVMEOVideos(videoPlayedItem.courseName!!, videoPlayedItem.description!!)
        }else{
            buildMediaItems(
                requireActivity(),
                childFragmentManager,videoPlayedItem.courseName!!,videoPlayedItem.description!!,false)
        }
    }
}