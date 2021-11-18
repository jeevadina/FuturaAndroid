package com.futuraeducation.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.futuraeducation.R
import kotlinx.android.synthetic.main.fragment_otp.*

class OtpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_otp)

        clickListener()

    }

    private fun clickListener() {

        continueButton.setOnClickListener {
            val intent = Intent(this, CourseSelectionActivity::class.java)
            startActivity(intent)
        }

        otp2.setOnKeyListener { _, i, _ ->
            if (i == KeyEvent.KEYCODE_DEL) {
                if (otp2.text.toString().isEmpty()) {
                    otp1.setText("")
                    otp1.requestFocus()
                }
            }
            false
        }

        otp3.setOnKeyListener { _, i, _ ->
            if (i == KeyEvent.KEYCODE_DEL) {
                if (otp3.text.toString().isEmpty()) {
                    otp2.setText("")
                    otp2.requestFocus()
                }
            }
            false
        }

        otp4.setOnKeyListener { _, i, _ ->
            if (i == KeyEvent.KEYCODE_DEL) {
                if (otp4.text.toString().isEmpty()) {
                    otp3.setText("")
                    otp3.requestFocus()
                }
            }
            false
        }

        otp1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != "") {
                    otp2.isFocusable = true
                    otp2.requestFocus()
                }
            }
        })

        otp2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                if (s.toString() != "") {
                    if (otp1.text.toString().isEmpty()) {
                        otp2.setText("")
                        otp1.text = s
                    } else {
                        otp3.isFocusable = true
                        otp3.requestFocus()
                    }
                }
            }
        })

        otp3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != "") {
                    if (otp2.text.toString().isEmpty()) {
                        otp3.setText("")
                        otp2.text = s
                    } else {
                        otp4.isFocusable = true
                        otp4.requestFocus()
                    }
                }
            }
        })

        otp4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != "") {
                    if (otp3.text.toString().isEmpty()) {
                        otp4.setText("")
                        otp3.text = s
                    } else {
                        otp4.isFocusable = true
                        otp4.requestFocus()
                    }
                }
            }
        })
    }


}
