package com.futuraeducation.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.futuraeducation.database.model.VideoPlayedItem

@Dao
interface VideoDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addVideo(sectionItem: VideoPlayedItem)

    @Query("SELECT * FROM VideoItems")
    fun getAll(): MutableList<VideoPlayedItem>

}