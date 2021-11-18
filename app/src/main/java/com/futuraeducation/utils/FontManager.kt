package com.futuraeducation.utils

import android.content.Context
import android.graphics.Typeface
import android.text.Layout
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.futuraeducation.R
import com.futuraeducation.helper.TextDrawable

object FontManager {

    val FONTAWESOME = "fonts/MaterialIcons3-Regular.ttf"

    fun getTypeface(context: Context, font: String): Typeface {
        return Typeface.createFromAsset(context.assets, font)
    }

    fun markAsIconContainer(v: View, typeface: Typeface) {

        if (v is ViewGroup) {
            for (i in 0 until v.childCount) {
                val child = v.getChildAt(i)
                markAsIconContainer(child)
            }
        } else if (v is TextView) {
            v.typeface = typeface
        }
    }

    private fun markAsIconContainer(child: View) {}


    fun getSFLFontDrawable(context: Context, icon: String): TextDrawable {

        val faIcon = TextDrawable(context)
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 100F)
        faIcon.setTextColor(ContextCompat.getColor(context, R.color.purple_500))
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER)
        faIcon.setTypeface(getTypeface(context, FONTAWESOME))
        faIcon.setText(icon)

        return faIcon
    }
}
