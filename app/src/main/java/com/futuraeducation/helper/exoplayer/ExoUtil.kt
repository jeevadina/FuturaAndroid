package com.futuraeducation.helper.exoplayer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.RenderersFactory
import com.google.android.exoplayer2.util.Assertions
import com.google.android.exoplayer2.util.Util
import com.futuraeducation.R
import com.futuraeducation.activity.VideoPlayActivity
import com.futuraeducation.helper.MyProgressBar

object ExoUtil {

    fun buildMediaItems(activity: Activity, supportFragmentManager:FragmentManager,titleFrom: String,url: String,preDownload:Boolean) {
        val extension = null
        val subtitleUri = null
        val title = titleFrom
        val uri = Uri.parse(url)
        val mediaItem = MediaItem.Builder()
        val adaptiveMimeType =
            Util.getAdaptiveMimeTypeForContentType(Util.inferContentType(uri, extension))
        mediaItem
            .setUri(uri)
            .setMediaMetadata(MediaMetadata.Builder().setTitle(title).build())
            .setMimeType(adaptiveMimeType)

        val playlist= PlaylistHolder(title, listOf(mediaItem.build()))

        if(preDownload) {
            val myProgress = MyProgressBar(activity)
            val downloadTracker = DemoUtil.getDownloadTracker( /* context= */activity)
            if (!downloadTracker!!.isDownloaded(playlist.mediaItems[0])) {
                val playlist = PlaylistHolder(playlist.mediaItems[0].mediaId, playlist.mediaItems)
                onSampleDownloadButtonClicked(
                    activity,
                    playlist,
                    downloadTracker,
                    supportFragmentManager
                )
                Handler(activity.mainLooper).postDelayed({
                    myProgress.dismiss()
                    goToVideoPlayer(activity, playlist)
                }, 10000)
                myProgress.show()
            } else {
                goToVideoPlayer(activity, playlist)
            }
        }else{
            goToVideoPlayer(activity, playlist)
        }
    }
    fun goToVideoPlayer(activity:Activity, playlist:PlaylistHolder){

        val intent = Intent(activity, VideoPlayActivity::class.java)
        intent.putExtra(
            IntentUtil.PREFER_EXTENSION_DECODERS_EXTRA,
            true
        )
        IntentUtil.addToIntent(playlist.mediaItems, intent)
        activity.startActivity(intent)

    }
    fun onSampleDownloadButtonClicked(activity: Activity,playlistHolder: PlaylistHolder, downloadTracker: DownloadTracker,supportFragmentManager:FragmentManager ) {
        val downloadUnsupportedStringId: Int = getDownloadUnsupportedStringId(playlistHolder)
        if (downloadUnsupportedStringId != 0) {
            Toast.makeText(activity, downloadUnsupportedStringId, Toast.LENGTH_LONG)
                .show()
        } else {
//            val renderersFactory: RenderersFactory = DemoUtil.buildRenderersFactory( /* context= */
//                activity, isNonNullAndChecked(
//                    preferExtensionDecodersMenuItem
//                )
//            )
            val renderersFactory: RenderersFactory = DemoUtil.buildRenderersFactory( /* context= */
                activity, true
            )
            downloadTracker!!.toggleDownload(
                supportFragmentManager, playlistHolder.mediaItems.get(0), renderersFactory
            )
        }
    }
    private fun getDownloadUnsupportedStringId(playlistHolder: PlaylistHolder): Int {
        if (playlistHolder.mediaItems.size > 1) {
            return R.string.download_playlist_unsupported
        }
        val playbackProperties =
            Assertions.checkNotNull(playlistHolder.mediaItems[0].playbackProperties)
        if (playbackProperties.adsConfiguration != null) {
            return R.string.download_ads_unsupported
        }
        val scheme = playbackProperties.uri.scheme
        return if (!("http" == scheme || "https" == scheme)) {
            R.string.download_scheme_unsupported
        } else 0
    }
    private fun isNonNullAndChecked(menuItem: MenuItem?): Boolean {
        // Temporary workaround for layouts that do not inflate the options menu.
        return menuItem != null && menuItem.isChecked
    }
}