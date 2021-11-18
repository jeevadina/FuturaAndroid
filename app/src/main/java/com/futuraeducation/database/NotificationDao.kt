package com.futuraeducation.database

import androidx.room.*
import com.futuraeducation.database.model.NotificationItem

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(sectionItem: NotificationItem): Long
    @Update
    fun update(sectionItem: NotificationItem)

    @Delete
    fun delete(sectionItem: NotificationItem)

    @Query("SELECT * FROM Notification WHERE id = :id")
    fun getSection(id: String): NotificationItem

    @Query("SELECT * FROM Notification ORDER BY updateTime DESC")
    fun getAll(): List<NotificationItem>

    @Query("SELECT * FROM Notification WHERE status = 0 ORDER BY updateTime DESC")
    fun getAllUnreadNotification(): List<NotificationItem>
}