package com.futuraeducation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.futuraeducation.R
import kotlinx.android.synthetic.main.activity_attach.*

class AttachmentActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attach)

        val url = intent.getStringExtra("url")

        webView.loadUrl(url!!)
    }
}