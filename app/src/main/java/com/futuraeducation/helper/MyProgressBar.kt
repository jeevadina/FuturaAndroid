package com.futuraeducation.helper

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.futuraeducation.R


class MyProgressBar(val activity: Activity) {
    private var alertDialog: AlertDialog? = null

    fun show(){
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity,R.style.DialogTheme)
        val inflater: LayoutInflater = activity.getLayoutInflater()
        val dialogView: View = inflater.inflate(R.layout.default_placeholder_progress, null)
        dialogBuilder.setView(dialogView)

        val editText = dialogView.findViewById<View>(R.id.state_text) as TextView
        editText.text = "loading.."
        alertDialog = dialogBuilder.create()
        alertDialog!!.setCancelable(false)
        alertDialog!!.show()
    }

    fun dismiss(){
        if(alertDialog != null && alertDialog!!.isShowing) {
            alertDialog!!.dismiss()
        }
    }

    fun isShowing(): Boolean{
        return if (alertDialog!= null) alertDialog.let { it?.isShowing!! }
        else false
    }

}