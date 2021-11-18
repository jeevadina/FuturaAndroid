package com.futuraeducation.model.onBoarding

import com.google.gson.annotations.SerializedName

data class CompletedSession(
     @SerializedName("id")
     val id: String? = null,

     @SerializedName("courseName")
     val courseName: String? = null,

     @SerializedName("parentId")
     val parentId: String? = null,

     @SerializedName("parentName")
     val parentName: Any? = null,

     @SerializedName("description")
     val description: String? = null,

     @SerializedName("status")
     val status: String? = null,

     @SerializedName("coachingCentre")
     val coachingCentre: Any? = null,

     @SerializedName("coachingCentreId")
     val coachingCentreId: Any? = null,

     @SerializedName("createdAt")
     val createdAt: Long? = null,

     @SerializedName("updatedAt")
     val updatedAt: Long? = null,

     @SerializedName("createdBy")
     val createdBy: Any? = null,

     @SerializedName("updatedBy")
     val updatedBy: Any? = null,

     @SerializedName("type")
     val type: Any? = null
)
