package com.futuraeducation.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.futuraeducation.fragment.LearnFragment
import com.futuraeducation.fragment.LiveFragment
import com.futuraeducation.fragment.practiceTest.PracticeTabFragment
import com.futuraeducation.fragment.practiceTest.TestFragment


/**
 * Created by Prabhu2757 on 19-06-2016.
 */
class HomeTabViewAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return LearnFragment()
        } else if (position == 1) {
            return LiveFragment.newInstance("", "")
        } else  {
          /*  return PracticeTabFragment.newInstance("","")
        } else {*/
            return TestFragment.newInstance("", "")
        }
    }
}
