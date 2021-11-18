package com.futuraeducation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.tabs.TabLayoutMediator
import com.futuraeducation.R
import com.futuraeducation.adapter.VideoPlayedAdapter
import com.futuraeducation.database.AppDatabase
import com.futuraeducation.database.model.VideoPlayedItem
import com.futuraeducation.fragment.practiceTest.*
import com.futuraeducation.helper.exoplayer.ExoUtil
import com.futuraeducation.model.VideoMaterial
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.slidingTabLayout
import vimeoextractor.OnVimeoExtractionListener
import vimeoextractor.VimeoExtractor
import vimeoextractor.VimeoVideo

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment(),VideoPlayedAdapter.ActionCallback {

    lateinit var db: AppDatabase
    lateinit var homeFragmentTabAdapter: HomeFragmentTabAdapter
    private var titles = arrayOf<String>("Upcoming Live Sessions","Scheduled Test")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getInstance(requireContext())!!

        homeFragmentTabAdapter = HomeFragmentTabAdapter(requireActivity(), titles)
        viewPager.adapter = homeFragmentTabAdapter
        initChart()

        Log.e("popTable", db.videoDao.getAll().toString())

        if (db.videoDao.getAll().isNullOrEmpty()){
            previosVideo.visibility = View.GONE
        }else{
            previosVideo.visibility = View.VISIBLE
        }

    }

    override fun onStart() {
        super.onStart()

        TabLayoutMediator(slidingTabLayout, viewPager,
            ({ tab, position -> tab.text = titles[position] })
        ).attach()
        viewPager.currentItem = requireArguments().getInt("currentPosition",0)
//        childFragmentManager.beginTransaction()
//            .replace(R.id.frameLayout, UpcomingLiveFragment.newInstance("", "")).commit()

//        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                when (tab.position) {
//                    0 ->
//                        childFragmentManager.beginTransaction()
//                            .replace(R.id.frameLayout, UpcomingLiveFragment.newInstance("", ""))
//                            .commit()
//                    1 ->
//                        childFragmentManager.beginTransaction()
//                            .replace(R.id.frameLayout, ScheduledTestFragment())
//                            .commit()
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {}
//            override fun onTabReselected(tab: TabLayout.Tab) {}
//        })

        val videoRecyclerView = view?.findViewById(R.id.playedRecycler) as RecyclerView
        val videoAdapter = VideoPlayedAdapter(requireActivity(),"0", db.videoDao.getAll(),null, this)
        videoRecyclerView.adapter = videoAdapter


    }

    private fun initChart() {

        //values for data input Dataset1 at your axis one positions
        val dataset1 = ArrayList<Entry>()
        dataset1.add(Entry(0f, 420f))
        dataset1.add(Entry(1f, 360f))
        dataset1.add(Entry(2f, 570f))
        dataset1.add(Entry(3f, 355f))
        dataset1.add(Entry(4f, 480f))

        val vl = LineDataSet(dataset1, "Your Score")
        vl.setDrawValues(false)
        vl.setDrawFilled(false)
        vl.color = ContextCompat.getColor(requireContext(), R.color.emerald)
        vl.lineWidth = 2f
        vl.setDrawCircles(false)
        vl.setDrawHighlightIndicators(false)

        //values for data input Dataset1 at your axis one positions
        val dataset2 = ArrayList<Entry>()
        dataset2.add(Entry(0f, 330f))
        dataset2.add(Entry(1f, 680f))
        dataset2.add(Entry(2f, 470f))
        dataset2.add(Entry(3f, 255f))
        dataset2.add(Entry(4f, 580f))

        val vl2 = LineDataSet(dataset2, "Topper's Score")
        vl2.setDrawValues(false)
        vl2.setDrawFilled(false)
        vl2.setDrawCircles(false)
        vl2.lineWidth = 2f
        vl2.color = ContextCompat.getColor(requireContext(), R.color.bittersweet)
        vl2.setDrawHighlightIndicators(false)

        val xLabel: ArrayList<String> = ArrayList()
        xLabel.add("20/07")
        xLabel.add("22/07")
        xLabel.add("28/07")
        xLabel.add("30/07")
        xLabel.add("1/8")

        val chartData = LineData()
        chartData.addDataSet(vl)
        chartData.addDataSet(vl2)

        val tf = ResourcesCompat.getFont(requireContext(), R.font.roboto_regular)

        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(xLabel)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.granularity = 1f
        lineChart.axisLeft.axisMinimum = 0f
        lineChart.axisLeft.axisMaximum = 720f
        lineChart.axisLeft.setLabelCount(13, true)
        lineChart.data = chartData
        lineChart.axisRight.isEnabled = false
        lineChart.invalidate()
        lineChart.xAxis.yOffset = 10f
        lineChart.axisLeft.xOffset = 15f
        lineChart.legend.textSize = 10f
        lineChart.axisLeft.textSize = 10f
        lineChart.xAxis.textSize = 10f
        lineChart.legend.typeface = tf
        lineChart.xAxis.typeface = tf
        lineChart.axisLeft.typeface = tf
        lineChart.legend.textColor = ContextCompat.getColor(requireContext(), R.color.text_color)
        lineChart.axisLeft.textColor = ContextCompat.getColor(requireContext(), R.color.text_color)
        lineChart.xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.text_color)
        lineChart.legend.textColor = ContextCompat.getColor(requireContext(), R.color.text_color)
        lineChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        lineChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        lineChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL
        lineChart.setExtraOffsets(0f, 0f, 0f, 10f)
        lineChart.legend.form = Legend.LegendForm.LINE
        lineChart.description.isEnabled = false
        lineChart.setNoDataText("No Test yet!")
        lineChart.animateX(1800, Easing.EaseInExpo)
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
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onVideoClickListener(videoPlayedItem: VideoPlayedItem) {
        playVideo(videoPlayedItem.videoTitle, videoPlayedItem.videoUrl)
    }

    override fun onVideoClickListener1(videoPlayedItem: VideoMaterial) {
    }

    private fun playVideo(title: String, id:String){
        val videoId = id.replace("https://vimeo.com/", "")
        VimeoExtractor.getInstance()
            .fetchVideoWithIdentifier(videoId, null, object : OnVimeoExtractionListener {
                override fun onSuccess(video: VimeoVideo) {
                    val hdStream = video.streams["720p"]
                    println("VIMEO VIDEO STREAM$hdStream")
                    hdStream?.let {
                        requireActivity().runOnUiThread {
                            //code that runs in main
                            navigateVideoPlayer(title,it)
                        }
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    Log.d("failure", throwable.message!!)
                }
            })
    }
    fun navigateVideoPlayer(title: String, url: String){
        ExoUtil.buildMediaItems(
            requireActivity(),
            childFragmentManager, title,
            url, false
        )
    }
    override fun onPause() {
        super.onPause()

        requireArguments().putInt("currentPosition", viewPager.currentItem)
    }
    class HomeFragmentTabAdapter(fm: FragmentActivity, val titles: Array<String>) : FragmentStateAdapter(fm) {
        override fun getItemCount(): Int {
            return titles.size
        }
        override fun createFragment(position: Int): Fragment {

            return when(position){
                0 -> UpcomingLiveFragment.newInstance(titles[position],"")
                1 -> ScheduledTestFragment.newInstance(titles[position],"")
                else -> ScheduledTestFragment.newInstance(titles[position],"")
            }
        }
    }
}