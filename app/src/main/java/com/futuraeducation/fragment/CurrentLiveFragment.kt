package com.futuraeducation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.futuraeducation.R
import com.futuraeducation.utils.Utils
import kotlinx.android.synthetic.main.live_items_layout.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

class CurrentLiveFragment : Fragment() {

    private var url = ""
    private var topicName = ""
    private var date: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.live_items_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        arguments.let {
            url = arguments?.get(ARG_PARAM1).toString()
            topicName = arguments?.get(ARG_PARAM2).toString()
            date = arguments?.getLong(ARG_PARAM3)!!
        }

        lesson.text = topicName
        time.text = Utils.getDateValue(date)
        timeJoin.setOnClickListener {
            callMeeting()
        }
    }

    fun callMeeting(){
        if (Utils.isPackageInstalled(requireContext(), "us.zoom.videomeetings")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                requireContext().startActivity(intent)
            }
            Toast.makeText(context, "Zoom Application", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Please Install Zoom Application", Toast.LENGTH_SHORT)
                .show()
        }
    }


    companion object {
        fun newInstance(url: String, topicName: String, startDateTime: Long) =
            CurrentLiveFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, url)
                    putString(ARG_PARAM2, topicName)
                    putLong(ARG_PARAM3, startDateTime)
                }
            }
    }
}

