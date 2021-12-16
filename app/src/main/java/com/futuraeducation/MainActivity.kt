 package com.futuraeducation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.futuraeducation.adapter.HomeTabViewAdapter
import com.futuraeducation.database.DatabaseHelper
import com.futuraeducation.doubt.AskDoubtActivity
import com.futuraeducation.fragment.LogOutBottomSheetFragment
import com.futuraeducation.fragment.UpdateBottomSheetFragment
import com.futuraeducation.helper.BottomNavigationBehavior
import com.futuraeducation.model.onBoarding.LoginData
import com.futuraeducation.network.NetworkHelper
import com.futuraeducation.network.OnNetworkResponse
import com.futuraeducation.profile.ProfileActivity
import com.futuraeducation.qrCode.QRCodeActivity
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.ImageLoader
import com.futuraeducation.utils.MyPreferences
import com.futuraeducation.utils.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_notification_icon.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_toolbar_custom.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity(), OnNetworkResponse, InstallStateUpdatedListener {
    private lateinit var appUpdateManager: AppUpdateManager
    lateinit var mRemoteConfig: FirebaseRemoteConfig
    var cacheExpiration: Long = 3600
    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private val REQUEST_CODE_IMMEDIATE_UPDATE = 17362
    }

    lateinit var homeTabViewAdapter: HomeTabViewAdapter
    lateinit var bottomNavigationBehavior: BottomNavigationBehavior
    lateinit var networkHelper: NetworkHelper
    lateinit var headerLayout: View
    lateinit var loginResponse: LoginData
    private val imageLoader = ImageLoader
    lateinit var databaseHelper: DatabaseHelper
    lateinit var myPreferences: MyPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Assign Appbar properties
        myPreferences = MyPreferences(this)
        mRemoteConfig = FirebaseRemoteConfig.getInstance()

        loginResponse =
            Gson().fromJson(MyPreferences(this).getString(Define.LOGIN_DATA), LoginData::class.java)

        //in app update checker
        appUpdateManager = AppUpdateManagerFactory.create(this)
        // Before starting an update, register a listener for updates.
        appUpdateManager.registerListener(this)
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo


        if(mRemoteConfig.getBoolean(Define.IN_APP_UPDATE_ENABLE)) {
            appUpdateInfoTask.addOnSuccessListener {
                if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && it.isUpdateTypeAllowed(
                        AppUpdateType.IMMEDIATE
                    )
                ) {   //  check for the type of update flow you want
                    requestUpdate(it)
                }
            }
        }


        //Assign Drawer properties
        val drawer = findViewById<DrawerLayout>(R.id.drawer)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        //Get header view
        headerLayout = nav_view.getHeaderView(0)
        setNavigationValue(loginResponse)

        listeners()

        networkHelper = NetworkHelper(this)

        val params = HashMap<String, String>()
        params.put("", "")

        bottomNavigationBehavior = BottomNavigationBehavior()
        val layoutParams = navigationView.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = bottomNavigationBehavior

        homeTabViewAdapter = HomeTabViewAdapter(this, loginResponse)
        viewPager.adapter = homeTabViewAdapter
        viewPager.offscreenPageLimit = 3
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 4) askDoubt.visibility = View.GONE
                else askDoubt.visibility = View.GONE
            }
        })

        askDoubt.setOnClickListener {
            val intent = Intent(this, AskDoubtActivity::class.java)
            startActivity(intent)
        }

        //check update via remoteconfig
        updateViews()
    }

    private fun setNavigationValue(response: LoginData) {
        var userName = ""
        if (!response.userDetail?.firstName.isNullOrEmpty()) userName =
            response.userDetail?.userName.toString()
        if (userName.isNotEmpty()) {
            headerLayout.name.text = userName
        }
    }


    private fun listeners() {

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logOut -> {
                    drawer.closeDrawer(GravityCompat.START)
                    val bottomSheetFragment = LogOutBottomSheetFragment()
                    bottomSheetFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                }
                R.id.qrScanner -> {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        val intent = Intent(this, QRCodeActivity::class.java)
                        startActivity(intent)
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.CAMERA),
                            100
                        )
                    }
                }
            }
            true
        }

        headerLayout.close.setOnClickListener {
            drawer.closeDrawer(GravityCompat.START)
        }

        headerLayout.profileImage.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        mRemoteConfig.fetch(cacheExpiration)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // task successful. Activate the fetched data
                    mRemoteConfig.activate()
                    //update views?
                    updateViews()
                }
            }
        val pageChangeCallback: ViewPager2.OnPageChangeCallback =
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (loginResponse.role?.equals("COACH")!!) {

                        if (position == 0) {
                            navigationView.selectedItemId = R.id.navigation_dash
                        } /*else if (position == 2) {
                        navigationView.selectedItemId = R.id.navigation_home
                    }*/ /*else if (position == 2) {
                        navigationView.selectedItemId = R.id.navigation_practice
                    }*/ else if (position == 1) {
                       /*     navigationView.selectedItemId = R.id.navigation_publish
                        } else if (position == 2) {*/
                       /*     navigationView.selectedItemId = R.id.navigation_test
                        }
                        else if (position == 2) {*/
                            navigationView.selectedItemId = R.id.navigation_assignment
                        }
                    }else{
                        if (position == 0) {
                            navigationView.selectedItemId = R.id.navigation_learn
                        } else if (position == 1) {
                            navigationView.selectedItemId = R.id.navigation_test
                        } else if (position == 2) {
                            navigationView.selectedItemId = R.id.navigation_assignment
                        }

                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                    //appBarLayout.setExpanded(true)
                    bottomNavigationBehavior.showBottomNavigationView(navigationView)
                }
            }
        viewPager.registerOnPageChangeCallback(pageChangeCallback)

        if (loginResponse.role?.equals("COACH")!!){
            navigationView.menu.removeItem(R.id.navigation_learn)
            navigationView.menu.removeItem(R.id.navigation_live)
            navigationView.menu.removeItem(R.id.navigation_test)
        }else{
   /*         navigationView.menu.removeItem(R.id.navigation_publish)
            navigationView.menu.removeItem(R.id.navigation_dash)*/
            navigationView.menu.removeItem(R.id.navigation_dash)
            navigationView.menu.removeItem(R.id.navigation_live)
        }

        navigationView.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                if (loginResponse.role?.equals("COACH")!!) {

                    when (item.itemId) {
                        R.id.navigation_dash -> {
                            viewPager.currentItem = 0
                            return@OnNavigationItemSelectedListener true
                        }
               /*         R.id.navigation_publish -> {
                            viewPager.currentItem = 1
                            return@OnNavigationItemSelectedListener true
                        }*/
                     /*   R.id.navigation_test -> {
                            viewPager.currentItem = 1
                            return@OnNavigationItemSelectedListener true
                        }*/
                        R.id.navigation_assignment -> {
                            viewPager.currentItem = 1
                            return@OnNavigationItemSelectedListener true
                        }

                    }
                }else{
                    when (item.itemId) {
                        R.id.navigation_learn -> {
                            viewPager.currentItem = 0
                            return@OnNavigationItemSelectedListener true
                        }
                        R.id.navigation_test -> {
                            viewPager.currentItem = 1
                            return@OnNavigationItemSelectedListener true
                        }
                        R.id.navigation_assignment -> {
                            viewPager.currentItem = 2
                            return@OnNavigationItemSelectedListener true
                        }

                    }

                }
                false
            })

        viewPager.currentItem = myPreferences.getInt(Define.HOME_SCREEN_LAST_KNOWN_TAB_POSITION, 0)
    }

    override fun onNetworkResponse(responseCode: Int, response: String, tag: String) {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            openQRCodeScreen()
        }
    }

    fun openQRCodeScreen() {
        val intent = Intent(this, QRCodeActivity::class.java)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        myPreferences.setInt(Define.HOME_SCREEN_LAST_KNOWN_TAB_POSITION, viewPager.currentItem)
    }

    override fun onBackPressed() {
        //Back event handled when drawer open
        when {
            drawer.isDrawerOpen(GravityCompat.START) -> {
                drawer.closeDrawer(GravityCompat.START)
            }
            viewPager.currentItem != 0 -> {
                viewPager.setCurrentItem(0, true)
            }
            else -> {
                myPreferences.setInt(Define.HOME_SCREEN_LAST_KNOWN_TAB_POSITION, 0)
                this.moveTaskToBack(true)
                System.exit(1)
                this.finish()
            }
        }
    }

    //in app update request method
    private fun requestUpdate(appUpdateInfo: AppUpdateInfo?) {
        try {
            if (appUpdateInfo != null) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE, //  HERE specify the type of update flow you want
                    this,   //  the instance of an activity
                    REQUEST_CODE_IMMEDIATE_UPDATE
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onResume() {
        super.onResume()
        try {
            appUpdateManager.appUpdateInfo.addOnSuccessListener {
                if (it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        it,
                        AppUpdateType.IMMEDIATE,
                        this,
                        REQUEST_CODE_IMMEDIATE_UPDATE
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMMEDIATE_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        appUpdateManager.unregisterListener(this)
    }
    override fun onStateUpdate(state: InstallState) {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            notifyUser()
        }
    }
    private fun notifyUser() {
        Snackbar
            .make(toolbar, R.string.restart_to_update, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.action_restart) {
                appUpdateManager.completeUpdate()
                appUpdateManager.unregisterListener(this)
            }
            .show()
    }
    private fun updateViews() {
        if (mRemoteConfig.getLong(Define.APP_VERSION_CODE) != Utils.getAppVersionCode(this)) {
          //  appUpdateDialog()
        }
    }
    private fun appUpdateDialog(){
        val bottomSheetFragment = UpdateBottomSheetFragment()
        bottomSheetFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        if(mRemoteConfig.getBoolean(Define.APP_FORCE_UPDATE)) {
            bottomSheetFragment.isCancelable = false
        }
    }
}