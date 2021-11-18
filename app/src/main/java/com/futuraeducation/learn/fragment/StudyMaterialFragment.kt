package com.futuraeducation.learn.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.futuraeducation.R
import com.futuraeducation.learn.adapter.FileListAdapter
import com.futuraeducation.model.FileNameItem
import com.futuraeducation.model.FileType
import kotlinx.android.synthetic.main.fragment_study_material.*

class StudyMaterialFragment : Fragment() {

    private var notesList = ArrayList<FileNameItem>()
    private var otherList = ArrayList<FileNameItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_study_material, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radioCustom(notes.id)
        materialType.setOnCheckedChangeListener { _, checkedId ->
            radioCustom(checkedId)
        }

        notesList.add(FileNameItem("demo file", FileType.PDF))
        notesList.add(FileNameItem("demo", FileType.PDF))

        otherList.add(FileNameItem("Important basic elements", FileType.PDF))
        otherList.add(
            FileNameItem(
                "Right angled triangle: ABC with right angle at abc",
                FileType.IMAGE
            )
        )
        otherList.add(FileNameItem("Core geometrical concept", FileType.PDF))
        otherList.add(FileNameItem("CCPL3-Electrostatic Potential and Capacitance", FileType.PDF))

        setRecyclerAdapter(notesList)

    }

    private fun setRecyclerAdapter(adapterList: ArrayList<FileNameItem>) {
        val fileListAdapter = FileListAdapter(requireContext(), adapterList)
        recycler.adapter = fileListAdapter
    }

    @SuppressLint("RestrictedApi")
    private fun radioCustom(selectedId: Int) {
        if (selectedId == notes.id) {
            notes.setTextColor(ContextCompat.getColor(requireContext(), R.color.carolina_blue))
            notes.supportButtonTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.carolina_blue)
            others.setTextColor(ContextCompat.getColor(requireContext(), R.color.tab_default_color))
            others.supportButtonTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.tab_default_color)
            setRecyclerAdapter(notesList)
        } else {
            notes.setTextColor(ContextCompat.getColor(requireContext(), R.color.tab_default_color))
            notes.supportButtonTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.tab_default_color)
            others.setTextColor(ContextCompat.getColor(requireContext(), R.color.carolina_blue))
            others.supportButtonTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.carolina_blue)
            setRecyclerAdapter(otherList)
        }
    }
}