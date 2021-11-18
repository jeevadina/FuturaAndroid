package com.futuraeducation.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.futuraeducation.R
import com.futuraeducation.activity.login.OtpActivity
import kotlinx.android.synthetic.main.activity_register.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class RegistrationActivity: AppCompatActivity() {

    private val myList = ArrayList<String>()
    lateinit var mAuth: FirebaseAuth

    // string for storing our verification ID
    private var verificationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        // The test phone number and code should be whitelisted in the console.
        val phoneNumber = "+918778701851"
        val smsCode = "000000"

        mAuth = Firebase.auth
        mAuth.setLanguageCode(Locale.getDefault().language)

        myList.apply {
            this.add("Student")
            this.add("Institute")
        }

        val newList: List<String> = myList
       // initClient()

       // val nonce: ByteArray? = getRequestNonce()

/*
        nonce?.let {
            SafetyNet.getClient(this).attest(it,"AIzaSyDEqF4TGq1014yZ1wgEiNprajpBLS_U2QY")
                .addOnSuccessListener(this) {
                    // Indicates communication with the service was successful.
                    // Use response.getJwsResult() to get the result data.
                    Log.e("popsi1", "success")
                }
                .addOnFailureListener(this) { e ->
                    // An error occurred while communicating with the service.
                    if (e is ApiException) {
                        // An error with the Google Play services API contains some
                        // additional details.
                        val apiException = e as ApiException

                        // You can retrieve the status code using the
                        // apiException.statusCode property.
                    } else {
                        // A different, unknown type of error occurred.
                        Log.e("popsi2", "Error: " + e.message)
                    }
                }
        }
*/

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.usertype,
            R.layout.spinnerview
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerUserType.adapter = adapter

        spinnerUserType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                //Toast.makeText(this@RegistrationActivity, newList[position],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (!confrompswd.text.isNullOrEmpty()) {
                    if (s.toString() != confrompswd.text.toString()) {
                        confrompswd.setBackgroundResource(R.drawable.ic_rectangle_red)
                    }else{
                        confrompswd.setBackgroundResource(R.drawable.ic_rectangle_new)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })


        confrompswd.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.toString() != password.text.toString()){
                    confrompswd.setBackgroundResource(R.drawable.ic_rectangle_red)
                }else{
                    confrompswd.setBackgroundResource(R.drawable.ic_rectangle_new)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        sbtbtn.setOnClickListener {

            if (validate()){
                val intent = Intent(this@RegistrationActivity, OtpActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this@RegistrationActivity, "Please Enter All Fields",Toast.LENGTH_SHORT).show()

            }


            // Configure faking the auto-retrieval with the whitelisted numbers.

        /*    // Turn off phone auth app verification.
            mAuth.getFirebaseAuthSettings()
                .setAppVerificationDisabledForTesting(true);*/
          /*  val options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    override fun onCodeSent(
                        verificationId: String,
                        forceResendingToken: PhoneAuthProvider.ForceResendingToken
                    ) {
                        // Save the verification id somewhere
                        // ...

                        // The corresponding whitelisted code above should be used to complete sign-in.
                       // this.enableUserManuallyInputCode()
                    }

                    override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                        // Sign in with the credential
                        // ...

                        Toast.makeText(this@RegistrationActivity,"success", Toast.LENGTH_LONG).show()
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        // ...
                        Toast.makeText(this@RegistrationActivity,e.toString(), Toast.LENGTH_LONG).show()
                        Log.e("popsi", e.toString())

                    }
                })
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)*/
            //  firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, smsCode)

          /*  val options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        Toast.makeText(this@RegistrationActivity, "Success", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@RegistrationActivity, CourseSelectionActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        Toast.makeText(this@RegistrationActivity, "Success", Toast.LENGTH_LONG).show()

                    }

                })
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
*/
        }
        }

    private fun initClient() {
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
            == ConnectionResult.SUCCESS
        ) {
            // The SafetyNet Attestation API is available.
            Toast.makeText(this@RegistrationActivity, "SafetyNet", Toast.LENGTH_LONG).show()

           // onClicks()
        }
    }

    private fun getRequestNonce(): ByteArray? {
        val data = System.currentTimeMillis().toString()
        val byteStream = ByteArrayOutputStream()
        val bytes = ByteArray(24)
        val random = Random()
        random.nextBytes(bytes)
        try {
            byteStream.write(bytes)
            byteStream.write(data.toByteArray())
        } catch (e: IOException) {
            return null
        }
        return byteStream.toByteArray()
    }

/*
    fun onClicks() {
        SafetyNet.getClient(this).verifyWithRecaptcha("AIzaSyDEqF4TGq1014yZ1wgEiNprajpBLS_U2QY")
            .addOnSuccessListener(this as Executor, OnSuccessListener { response ->
                // Indicates communication with reCAPTCHA service was
                // successful.
                val userResponseToken = response.tokenResult
                if (response.tokenResult?.isNotEmpty() == true) {
                    // Validate the user response token using the
                    // reCAPTCHA siteverify API.
                }
            })
            .addOnFailureListener(this as Executor, OnFailureListener { e ->
                if (e is ApiException) {
                    // An error occurred when communicating with the
                    // reCAPTCHA service. Refer to the status code to
                    // handle the error appropriately.
                    Log.d(TAG, "Error: ${CommonStatusCodes.getStatusCodeString(e.statusCode)}")
                } else {
                    // A different, unknown type of error occurred.
                    Log.d(TAG, "Error: ${e.message}")
                }
            })
    }
*/

    private fun validate(): Boolean{
        var isValid: Boolean = true
        if (userName.text.isNullOrEmpty()){
            isValid = false
        }
        if (phoneNumber.text.isNullOrEmpty()){
            isValid = false
        }
        if (emailUser.text.isNullOrEmpty()){
            isValid = false
        }
        if (password.text.isNullOrEmpty()){
            isValid = false
        }
        if (confrompswd.text.isNullOrEmpty()){
            isValid = false
        }

        return isValid
    }

}
