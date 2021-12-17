package com.futuraeducation.model.publish

import com.google.gson.annotations.SerializedName

class PublishData {
    @SerializedName("coachingCentreId")
    var coachingCentreId: String? = null
    @SerializedName("branchId")
    var branchId: ArrayList<PublishDataValue>? = null
    @SerializedName("courseId")
    var courseId: String? = null
    @SerializedName("batchId")
    var batchId: ArrayList<PublishDataValue>? = null
    @SerializedName("batchIds")
    var batchIds: ArrayList<String>? = null
    @SerializedName("subjectId")
    var subjectId: String? = null
    @SerializedName("chapterId")
    var chapterId: ArrayList<String>? = null
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

class PublishMaterialResponse{
    @SerializedName("data")
    var data: String? = null
}