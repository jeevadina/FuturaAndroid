package com.futuraeducation.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.futuraeducation.model.TestResultsModel


@Dao
interface TestResponseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addResult(sectionItem: TestResultsModel)

    @Query("SELECT * FROM ResultReview")
    fun getAll(): MutableList<TestResultsModel>

    @Query("SELECT * FROM ResultReview where testPaperId=:testPaperId")
    fun getResponse(testPaperId: String): TestResultsModel

    @Query("SELECT EXISTS(SELECT * FROM ResultReview WHERE testPaperId=:testPaperId)")
    fun isExists(testPaperId: String): Boolean

}