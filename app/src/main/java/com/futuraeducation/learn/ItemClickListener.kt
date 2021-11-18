package com.futuraeducation.learn

import com.futuraeducation.model.VideoMaterial

interface TopicClickListener {
    fun onTopicSelected(subTopicItems: List<VideoMaterial>)
}