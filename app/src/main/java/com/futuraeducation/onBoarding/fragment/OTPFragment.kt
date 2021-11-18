package com.futuraeducation.onBoarding.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.futuraeducation.MainActivity
import com.futuraeducation.R
import kotlinx.android.synthetic.main.fragment_otp.*

class OTPFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListener()
    }

    private fun clickListener() {

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

        continueButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            activity?.startActivity(intent)
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
