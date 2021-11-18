package com.futuraeducation.qrCode

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.*
import com.google.gson.Gson
import com.futuraeducation.R
import com.futuraeducation.helper.MyProgressBar
import com.futuraeducation.helper.exoplayer.ExoUtil.buildMediaItems
import com.futuraeducation.model.GetQRCode
import com.futuraeducation.network.ApiUtils
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.network.OnNetworkResponse
import com.futuraeducation.network.URLHelper
import vimeoextractor.OnVimeoExtractionListener
import vimeoextractor.VimeoExtractor
import vimeoextractor.VimeoVideo

class QRCodeActivity : AppCompatActivity(), OnNetworkResponse {
    private lateinit var codeScanner: CodeScanner
    lateinit var networkHelper: NetworkHelper
    lateinit var myProgressBar: MyProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        networkHelper = NetworkHelper(this)
        myProgressBar = MyProgressBar(this)

        codeScanner = CodeScanner(this, scannerView)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                if (!it.text.isNullOrEmpty()){
                    getVideoId(it.text!!)
                    Log.e("videoid", it.text!!)

                }
            }

        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(
                    this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun getVideoId(id: String) {
        myProgressBar.show()
        networkHelper.getCall(
            URLHelper.qrcode + id,
            "qrcode1",
            ApiUtils.getHeader(this),
            this
        )
    }

    private fun playVideo(title: String, id:String){
        val videoId = id.replace("https://vimeo.com/", "")
        VimeoExtractor.getInstance()
            .fetchVideoWithIdentifier(videoId, null, object : OnVimeoExtractionListener {
                override fun onSuccess(video: VimeoVideo) {
                    val hdStream = video.streams["720p"]
                    println("VIMEO VIDEO STREAM$hdStream")
                    hdStream?.let {
                        runOnUiThread {
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
    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {
        myProgressBar.dismiss()
        if (responseCode == networkHelper.responseSuccess && tag == "qrcode1") {
            val qrResponse = Gson().fromJson(response, GetQRCode::class.java)
            if (qrResponse.data.videoUrl.contains("vimeo", true)){
                playVideo(qrResponse.data.id,qrResponse.data.videoUrl)
            }else {
                navigateVideoPlayer(qrResponse.data.id,qrResponse.data.videoUrl)
            }
        }else{
            Toast.makeText(this,"Unable to view the video... Try again later...",Toast.LENGTH_LONG).show()
        }
    }

    fun navigateVideoPlayer(title: String, url: String){
        buildMediaItems(
            this,
            supportFragmentManager,title,
            url,false)

        finish()
    }
}