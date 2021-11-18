package com.futuraeducation.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Notification")
class NotificationItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notifyID")
    @SerializedName("notifyID")var notifyID: Long = 0

    @ColumnInfo(name = "id")
    @SerializedName("id")var id: String? = null

    @ColumnInfo(name = "title")
    @SerializedName("title")var title: String? = null

    @ColumnInfo(name = "date")
    @SerializedName("date")var date: String? = null

    @ColumnInfo(name = "categoryId")
    @SerializedName("categoryId")var categoryId: String? = null

    @ColumnInfo(name = "type")
    @SerializedName("type")var type: String? = null

    @ColumnInfo(name = "data")
    @SerializedName("data")var data: String? = null

    @ColumnInfo(name = "updateTime")
    @SerializedName("updateTime")var updateTime: Long? = 0

    @ColumnInfo(name = "status")
    @SerializedName("status")var status: Long? = 0


}