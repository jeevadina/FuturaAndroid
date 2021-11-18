package com.futuraeducation.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


class TopicResponse : ArrayList<TopicResponseItem>()

@Serializable
data class TopicResponseItem(
    @SerialName("materialList")
    val materialList: List<VideoMaterial>? =null,
    @SerialName("topic")
    val topic: Topic
)

@Serializable
data class VideoMaterial (
    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("courseName")
    @Expose
    var courseName: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("filePath")
    @Expose
    var filePath: String? = null,

    @SerializedName("materialType")
    @Expose
    var materialType: String? = null,

    @SerializedName("orderSequence")
    @Expose
    var orderSequence: String? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("videoId")
    @Expose
    var videoId: String? = null
)

@Serializable
data class Topic(
    @SerializedName("description")
    @Expose
    var description: String? = null,
    @SerializedName("courseName")
    @Expose
    var courseName: String? = null,
    @SerializedName("parentId")
    var parentId: String? = null,
    @SerializedName("status")
    @Expose
    var status: String? = null
)

@Serializable
data class MaterialVideoList( val materialList: List<VideoMaterial>)