package com.futuraeducation.helper.exoplayer

import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.Assertions
import java.util.*
import kotlin.collections.ArrayList

class PlaylistHolder constructor(title: String, mediaItems: List<MediaItem>) {
    val title: String
    val mediaItems: List<MediaItem>

    init {
        Assertions.checkArgument(!mediaItems.isEmpty())
        this.title = title
        this.mediaItems = Collections.unmodifiableList(ArrayList(mediaItems))
    }
}