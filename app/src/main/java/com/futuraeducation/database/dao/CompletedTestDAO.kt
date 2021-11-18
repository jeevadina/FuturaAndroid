package com.futuraeducation.database.dao

import androidx.room.*
import com.futuraeducation.model.CompletedTest

@Dao
interface CompletedTestDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(completedTest: CompletedTest): Long

    @Update
    fun update(completedTest: CompletedTest)

    @Delete
    fun delete(completedTest: CompletedTest)

    @Query("DELETE FROM completed_test where testPaperId = :id")
    fun deleteTest(id: String)

    @Query("SELECT * FROM completed_test ORDER BY id ASC")
    fun getAll(): List<CompletedTest>

    @Query("SELECT * FROM completed_test where testPaperId =:id")
    fun getTest(id: String): CompletedTest

//    @Transaction
//    @Query("SELECT * FROM completed_test WHERE testPaperId = :id")
//    fun getQuestion(id: String): MutableList<CompletedTest>

//    @Transaction
//    @Query("UPDATE completed_test SET answer=:answer WHERE id = :id")
//    fun updateQuestion(id: String, answer: String?)

}