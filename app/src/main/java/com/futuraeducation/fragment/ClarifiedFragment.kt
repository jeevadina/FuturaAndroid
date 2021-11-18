package com.futuraeducation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.futuraeducation.R
import com.futuraeducation.adapter.ClarifiedDoubtAdapter
import com.futuraeducation.model.ClarifiedDoubtItem
import kotlinx.android.synthetic.main.fragment_clarified.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class ClarifiedFragment : Fragment() {

    private var studyList = ArrayList<ClarifiedDoubtItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clarified, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studyList.add(
            ClarifiedDoubtItem(
                "Mathematics",
                "Basic of Trigonometry",
                "19th Mar,\n9:30AM", R.color.caribbean_green
            )
        )
        studyList.add(
            ClarifiedDoubtItem(
                "Biology",
                "Digestive System",
                "18th Mar,\n9:30AM", R.color.light_coral
            )
        )
        studyList.add(
            ClarifiedDoubtItem(
                "Physics",
                "Solar System",
                "17th Mar,\n9:30AM", R.color.blue_violet_crayola
            )
        )
        studyList.add(
            ClarifiedDoubtItem(
                "Chemistry",
                "Periodic Table",
                "16th Mar,\n9:30AM", R.color.safety_yellow
            )
        )

        val studyAdapter = ClarifiedDoubtAdapter(requireContext(), studyList)
        clarifiedRecycler.adapter = studyAdapter
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
            ClarifiedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}