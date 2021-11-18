package com.futuraeducation.utils

import android.content.Context
import android.os.AsyncTask
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.futuraeducation.GlideApp

object ImageLoader {

    fun setImage(context: Context, url: Int, imageView: ImageView){
        GlideApp.with(context).load(url).into(imageView)
    }

    fun loadFit(context: Context, url: String, imageView: ImageView) {

        GlideApp
                .with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .centerCrop()
                .override(imageView.measuredWidth,imageView.measuredHeight)
                //.placeholder(R.drawable.placeholder)
                .transforms(CenterCrop(),RoundedCorners(14))
                .into(imageView)
    }

    fun load(context: Context, url: String, imageView: ImageView) {

        GlideApp
                .with(context)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.placeholder(R.drawable.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    fun loadIcon(context: Context, url: String, imageView: ImageView) {


        GlideApp
                .with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .override(500,500)
                //.placeholder(R.drawable.placeholder)
                .into(imageView);

    }
    fun loadFull(context: Context, url: String, imageView: ImageView) {

        GlideApp
                .with(context)
                .load(url)
                .fitCenter()
               // .override(imageView.measuredWidth,imageView.measuredHeight)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                //.placeholder(R.drawable.placeholder)
                .transforms(FitCenter(),RoundedCorners(14))
                .into(imageView);


    }
    fun loadFullMeme(context: Context, url: String, imageView: ImageView) {

        GlideApp
                .with(context)
                .load(url)
                .fitCenter()
//                .transition(GenericTransitionOptions
//                        .with(android.R.anim.anticipate_overshoot_interpolator))
                // .override(imageView.measuredWidth,imageView.measuredHeight)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                //.placeholder(R.drawable.placeholder)
                .transforms(FitCenter(),RoundedCorners(14))
                .into(imageView);


    }
    fun loadFullLogo(context: Context, url: String, imageView: ImageView) {
        GlideApp
            .with(context)
            .load(url)
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(true)
            .into(imageView);
    }
    fun clearMemory(context: Context){
        doAsync(context).execute()
    }


    class doAsync(context: Context) : AsyncTask<Void, Void, Void>() {

        val context1 = context

        override fun doInBackground(vararg params: Void?): Void? {

            GlideApp.get(context1).clearDiskCache();

            return null
        }
    }

}
