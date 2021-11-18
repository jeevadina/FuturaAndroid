package com.futuraeducation.profile

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.futuraeducation.R
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import com.futuraeducation.utils.ImageLoader
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class ProfileActivity : AppCompatActivity() {
    private val imageLoader = ImageLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //Assign Appbar properties
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        actionBar?.title = ""

        val classList: MutableList<String> = ArrayList()
        classList.add("JEE (11th)")
        val classAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.spinner_item, classList)
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        className.adapter = classAdapter


        val loginResponse =
            Gson().fromJson(MyPreferences(this).getString(Define.LOGIN_DATA), LoginData::class.java)
        var userName = ""
        if (!loginResponse.userDetail!!.firstName.isNullOrEmpty()) userName =
            loginResponse.userDetail!!.firstName.toString()
        if (!loginResponse.userDetail?.userName.isNullOrEmpty()) {
            userName += loginResponse.userDetail!!.userName.toString()
        }
        if (userName.isNotEmpty()) {
            name.text = userName
            profileName.text = userName
        }
      /*  if (!loginResponse.userDetail?.profileImagePath.isNullOrEmpty())
            imageLoader.loadFit(this, loginResponse.userDetail?.profileImagePath!!, image)*/
/*
        if (!loginResponse.userDetail?.mobileNumber.isNullOrEmpty()) {
            mobileNumber.text =
                loginResponse.userDetail?.mobileNumber
        }
*/
/*
        if (!loginResponse.userDetail?.email.isNullOrEmpty()) {
            email.text =
                loginResponse.userDetail?.email
            email.alpha = 0.0f
        }
*/
/*
        if (!loginResponse.userDetail?.dob.isNullOrEmpty()) {
            birthday.text =
                loginResponse.userDetail?.dob
            birthday.alpha = 0.0f
        }
*/
      /*  var address = ""
        if (!loginResponse.userDetail?.address1.isNullOrEmpty())
            address = loginResponse.userDetail!!.address1.toString() + ", "
        if (!loginResponse.userDetail?.address2.isNullOrEmpty())
            address += loginResponse.userDetail!!.address2.toString() + ", "
        if (!loginResponse.userDetail?.city.isNullOrEmpty())
            address += loginResponse.userDetail!!.city.toString() + ", "
        if (!loginResponse.userDetail?.country.isNullOrEmpty())
            address += loginResponse.userDetail!!.country.toString() + ", "
        if (!loginResponse.userDetail?.zipCode.isNullOrEmpty())
            address += loginResponse.userDetail!!.zipCode.toString() + ", "*/
      /*  if (address.trim().endsWith(","))
            address = address.substring(0, address.length - 1)
        if (address.isNotEmpty()) {
            location.text = address
            location.alpha = 0.0f
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}