package com.futuraeducation.model.assignment

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class AssignmentModel (
    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("teacherName")
    @Expose
    var teacherName: String? = null,

    @SerializedName("subject")
    @Expose
    var subject: String? = null,

    @SerializedName("date")
    @Expose
    var date: String? = null,

    @SerializedName("batchId")
    @Expose
    var batchId: String? = null,

    @SerializedName("attachment")
    @Expose
    var attachment: String? = null,

    @SerializedName("createdAt")
    @Expose
    var createdAt: Long? = null,

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: Long? = null,

    @SerializedName("createdBy")
    @Expose
    var createdBy: Any? = null,

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: Any? = null
)

class AssignmentResponse{
    @SerializedName("data")
    var data: String? = null
}