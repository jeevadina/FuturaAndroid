package com.futuraeducation.onBoarding.fragment

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
import com.futuraeducation.MainActivity
import com.futuraeducation.R
import com.futuraeducation.activity.RegistrationActivity
import com.futuraeducation.helper.MyProgressBar
import com.futuraeducation.model.onBoarding.LoginResponse
import com.futuraeducation.network.ApiUtils.getHeader
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.network.OnNetworkResponse
import com.futuraeducation.network.URLHelper
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONException
import org.json.JSONObject


class LoginFragment : Fragment(), OnNetworkResponse {

    lateinit var mRemoteConfig: FirebaseRemoteConfig
    lateinit var networkHelper: NetworkHelper

    //Google Auth
    private lateinit var auth: FirebaseAuth

    //Google signin client
    private lateinit var googleSignInClient: GoogleSignInClient

    //preference class
    lateinit var myPreferences: MyPreferences
    lateinit var myProgressBar: MyProgressBar
    private var isEyeVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPreferences = MyPreferences(requireContext())
        myProgressBar = MyProgressBar(requireActivity())
        mRemoteConfig = Firebase.remoteConfig
        networkHelper = NetworkHelper(requireContext())
        // Configure Google Sign In
   /*     val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()*/

       // googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = Define.REMOTE_CONFIG_MINIMUM_INTERVAL
        }
        mRemoteConfig.setConfigSettingsAsync(configSettings)
        mRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        mRemoteConfig.fetchAndActivate()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("LoginFragment", "Config params updated: $updated")

                }
            }

        continueButton.setOnClickListener {
            requestLogin()
        }

        signupTxt.setOnClickListener {
            val intent = Intent(requireContext(), RegistrationActivity::class.java)
            requireContext().startActivity(intent)
        }

        pastePin.setOnClickListener {
            if (!isEyeVisible) {
                mobileNumber.transformationMethod = HideReturnsTransformationMethod.getInstance()
                pastePin.setImageResource(R.drawable.ic_eye_on)
                isEyeVisible = true
            }else{
                mobileNumber.transformationMethod = PasswordTransformationMethod.getInstance()
                pastePin.setImageResource(R.drawable.ic_eye_off)
                isEyeVisible = false
            }
        }

    }

    private fun requestLogin() {
       // activity?.imgLog?.setImageResource(R.drawable.ic_login_success)
        val username = emailAddress.text.toString()
        val password = mobileNumber.text.toString()

        when {
            username.isEmpty() -> {
                emailAddress.error = "Enter valid username"
            }
            password.isEmpty() -> {
                mobileNumber.error = "Enter valid password"
            }
            else -> {
                emailAddress.error = null
                mobileNumber.error = null

                val jsonObject = JSONObject()
                try {
                    jsonObject.put("loginDevice", "mobile")
                    jsonObject.put("userName", username)
                    jsonObject.put("password", password)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                myProgressBar.show()

                networkHelper.postCall(
                    URLHelper.baseURLAuth,
                    jsonObject,
                    "login",
                    getHeader(),
                    this
                )
            }
        }
    }

    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {
        requireActivity().stateful.showContent()
        myProgressBar.dismiss()
        if (responseCode == networkHelper.responseSuccess && tag.equals("login")) {
            loginResponseData(response)
        } else {
            Toast.makeText(requireContext(), "login Failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun loginResponseData(response: String) {
        myProgressBar.dismiss()
        val loginResponse = Gson().fromJson(response, LoginResponse::class.java)
        if (loginResponse.data != null) {
            Toast.makeText(requireContext(), "login successful", Toast.LENGTH_LONG).show()
            myPreferences.setString(Define.ACCESS_TOKEN, loginResponse.data!!.token)
            myPreferences.setString(Define.LOGIN_DATA, Gson().toJson(loginResponse.data))

            //Go to dashboard screen
            requireActivity().startActivity(Intent(requireActivity(), MainActivity::class.java))
        } else {
            Toast.makeText(requireContext(), "Login failed, please try again", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        updateUI(null)
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun updateUI(user: FirebaseUser?) {
        try {
            if (user != null) {
                Log.d("USERNAME", ""+user.displayName)
                Log.d("EMAIL", ""+user.email)
                Log.d("PHONE NUMBER", "" + user.phoneNumber)
                Log.d("PHOTO URL", "" + user.photoUrl.toString())
                Log.d("UID", "" + user.uid)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }


}