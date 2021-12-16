package com.futuraeducation.model.publish

import com.google.gson.annotations.SerializedName

class PublishData {
    @SerializedName("coachingCentreId")
    var coachingCentreId: String? = null
    @SerializedName("branchIds")
    var branchIds: ArrayList<PublishDataValue>? = null
    @SerializedName("courseId")
    var courseId: String? = null
    @SerializedName("batchId")
    var batchId: ArrayList<PublishDataValue>? = null
    @SerializedName("subjectId")
    var subjectId: String? = null
    @SerializedName("chapterId")
    var chapterId: String? = null
    @SerializedName("topicId")
    var topicId: String? = null
    @SerializedName("materialId")
    var materialId: String? = null
    @SerializedName("publishDate")
    var publishDate: String? = null
    @SerializedName("publishTime")
    var publishTime: String? = null
}

class PublishDataValue {
    @SerializedName("value")
    var value: String? = null
    @SerializedName("label")
    var label: String? = null
}