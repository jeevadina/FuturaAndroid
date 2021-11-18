package com.futuraeducation.model

import com.google.gson.annotations.SerializedName

data class CourseResponse(
    @SerializedName("data")
    var data: ArrayList<Datum>? = null
)

data class Datum (
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("courseName")
    var courseName: String? = null,

    @SerializedName("parentId")
    var parentId: String? = null,

    @SerializedName("parentName")
    var parentName: Any? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("coachingCentre")
    var coachingCentre: Any? = null,

    @SerializedName("coachingCentreId")
    var coachingCentreId: Any? = null,

    @SerializedName("createdAt")
    var createdAt: Long? = null,

    @SerializedName("updatedAt")
    var updatedAt: Long? = null,

    @SerializedName("createdBy")
    var createdBy: Any? = null,

    @SerializedName("updatedBy")
    var updatedBy: Any? = null,

    @SerializedName("type")
    var type: String? = null
)