package com.futuraeducation.model.chapter

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.futuraeducation.model.VideoMaterial

data class ChapterResponse(
    @SerializedName("data")
    @Expose
    var data: List<ChapterResponseData>? = null
)

data class ChapterResponseData(
    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("courseName")
    @Expose
    var courseName: String? = null,

    @SerializedName("parentId")
    @Expose
    var parentId: String? = null,

    @SerializedName("parentName")
    @Expose
    var parentName: Any? = null,

    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("coachingCentre")
    @Expose
    var coachingCentre: Any? = null,

    @SerializedName("coachingCentreId")
    @Expose
    var coachingCentreId: Any? = null,

    @SerializedName("videoCount")
    @Expose
    var videoCount: String? = null,

    @SerializedName("createdAt")
    @Expose
    var createdAt: Long? = null,

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: Long? = null,

    @SerializedName("topic")
    @Expose
    var topic: Any? = null,

    @SerializedName("materialList")
    @Expose
    var materialList: Any? = null,

    @SerializedName("topicMaterialResponses")
    @Expose
    var topicMaterialResponses: List<TopicMaterialResponse>? = null
)

data class Topic(
    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("courseName")
    @Expose
    var courseName: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null
)

data class TopicMaterialResponse(
    @SerializedName("topic")
    @Expose
    var topic: Topic? = null,

    @SerializedName("materialList")
    @Expose
    var materialList: List<VideoMaterial>? = null
)