package com.futuraeducation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.futuraeducation.R
import kotlinx.android.synthetic.main.fragment_doubt.*
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DoubtFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doubt, container, false)
    }

    override fun onStart() {
        super.onStart()
        stateful.showEmpty()
        stateful.setEmptyText("This feature coming soon..")
        stateful.setEmptyImageResource(R.drawable.icon_happy)
        childFragmentManager.beginTransaction().replace(R.id.doubtFrameLayout, ClarifiedFragment.newInstance("","")).commit()

        doubtTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> childFragmentManager.beginTransaction().replace(R.id.doubtFrameLayout, ClarifiedFragment.newInstance("","")).commit()

                    1 -> childFragmentManager.beginTransaction().replace(R.id.doubtFrameLayout, ClarifiedFragment.newInstance("","")).commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
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
            DoubtFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}