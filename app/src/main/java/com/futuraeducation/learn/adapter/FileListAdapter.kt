package com.futuraeducation.learn.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rajat.pdfviewer.PdfViewerActivity
import com.futuraeducation.R
import com.futuraeducation.learn.ImageViewActivity
import com.futuraeducation.model.FileNameItem
import com.futuraeducation.model.FileType
import kotlinx.android.synthetic.main.row_file_name.view.*


class FileListAdapter(
    val context: Context,
    private val fileNameItems: ArrayList<FileNameItem>
) : RecyclerView.Adapter<FileListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.row_file_name, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fileName = fileNameItems[position]
        holder.itemView.fileName.text = fileName.fileName
        if (fileName.fileType == FileType.PDF) {
            holder.itemView.fileIcon.setBackgroundResource(R.drawable.ic_pdf)
            holder.itemView.setOnClickListener {
                context.startActivity(
                    PdfViewerActivity.launchPdfFromUrl(
                        context,
                        "http://www.africau.edu/images/default/sample.pdf",
                        "Sample Pdf",
                        "",
                        enableDownload = false
                    )
                )
            }
        } else {
            holder.itemView.fileIcon.setBackgroundResource(R.drawable.ic_image)
            holder.itemView.setOnClickListener {
                context.startActivity(
                    Intent(context, ImageViewActivity::class.java)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return fileNameItems.size
    }
}