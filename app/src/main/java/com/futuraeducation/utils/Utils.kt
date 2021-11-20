package com.futuraeducation.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.text.Layout
import android.util.Log
import android.util.TypedValue
import com.futuraeducation.BuildConfig
import com.futuraeducation.helper.TextDrawable
import org.ocpsoft.prettytime.PrettyTime
import java.io.IOException
import java.math.BigDecimal
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import android.view.WindowManager






object Utils {
    const val LAUNCH_SECOND_ACTIVITY = 2

    fun getAppVersionCode(context: Context): Long {
        var versionName: Long = 0
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                versionName = packageInfo.longVersionCode
            }else{
                versionName = packageInfo.versionCode.toLong()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return versionName
    }


    fun getAppVersionName(context: Context): String? {
        var versonName: String? = ""
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            versonName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return versonName
    }

    fun getLocaleTime(context: Context, dateStr: String): String {
        var formattedDate = dateStr
        try {
            if (dateStr.contains("ago") || dateStr.contains("now")) {
                return dateStr
            }
            val dfSource = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
            val srcDate = dfSource.parse(dateStr)
            formattedDate = getPrettyTime(srcDate.time)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return formattedDate
    }

    fun getPrettyTime(ms: Long): String {
        val p = PrettyTime()
        val date = Date(ms)
        return p.format(date)
    }

    fun getFontDrawable(context: Context, icon: String, color: String, size: Float): TextDrawable {

        val faIcon = TextDrawable(context)
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        faIcon.setTextColor(Color.parseColor(color))
        faIcon.textAlign = Layout.Alignment.ALIGN_CENTER
        faIcon.typeface = FontManager.getTypeface(context, FontManager.FONTAWESOME)
        faIcon.text = icon

        return faIcon
    }

    fun getFontDrawableIcon(context: Context, icon: String, color: Int, size: Float): TextDrawable {

        val faIcon = TextDrawable(context)
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        faIcon.setTextColor(color)
        faIcon.textAlign = Layout.Alignment.ALIGN_CENTER
        faIcon.typeface = FontManager.getTypeface(context, FontManager.FONTAWESOME)
        faIcon.text = icon

        return faIcon
    }

    //Read JSON File from assert folder to return String
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String? {
        var json: String? = null
        try {

            val `is` = context.assets.open("$jsonFileName.json")

            val size = `is`.available()

            val buffer = ByteArray(size)

            `is`.read(buffer)

            `is`.close()

            json = String(buffer)


        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json

    }

    fun loadUrl(activity: Context, url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        activity.startActivity(i)
    }

    fun isAppInstalled(context: Context, packageName: String): Boolean {
        try {
            context.packageManager.getApplicationInfo(packageName, 0)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }

    }

    fun log(tag: String, message: String?) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message!!)
        }
    }

    fun testLog(message: String) {
        if (BuildConfig.DEBUG) {
            Log.d("s2s", "sara $message")
        }
    }

    fun getDateValue(date: Long): String {
        val date = Date(date)
        var dateVal = ""
        val dfs = DateFormatSymbols()
        val months: Array<String> = dfs.months
        val myDate: Calendar = GregorianCalendar()
        val dateFormat: DateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        myDate.time = date
        try {

            dateVal =
                when {
                    myDate[Calendar.DATE] == 1 -> myDate[Calendar.DATE].toString() + "st "
                    myDate[Calendar.DATE] == 2 -> myDate[Calendar.DATE].toString() + "nd "
                    myDate[Calendar.DATE] == 3 -> myDate[Calendar.DATE].toString() + "rd "
                    else -> myDate[Calendar.DATE].toString() + "th "
                }
            myDate[Calendar.MONTH]
            if (myDate[Calendar.MONTH] <= 11) {
                dateVal = dateVal + months[myDate[Calendar.MONTH]] + ", " + myDate.time
            }
           // dateVal += dateFormat.format(date).uppercase(Locale.getDefault())

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateVal
    }

    fun getDateTime(date: Long) : String{
        //val date = Date(date)

        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")

        return dateFormat.format(Date(date))
    }

    fun getDuration(duration: Int?): String {
        return if (duration == null) {
            "-"
        } else if (duration < 60) {
            duration.toString() + "mins"
        } else {
            if (duration % 60 == 0) (duration / 60).toString() + "hr"
            else (duration / 60).toString() + "hr, " + (duration % 60).toString() + "mins"

        }
    }

    fun getMarkPercentage(d: Double?): String {
        if (d == null) {
            return "-"
        } else if (d % 1 == 0.0) {
            return "${d.toInt()}%"
        }
        var bd = BigDecimal(d.toString())
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
        return "$bd%"
    }

    fun isPackageInstalled(context: Context, packageName: String?): Boolean {
        return try {
            context.packageManager.getApplicationInfo(packageName!!, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun updateStatusBarColor(activity: Activity, color:String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor(color)
        }
    }

}