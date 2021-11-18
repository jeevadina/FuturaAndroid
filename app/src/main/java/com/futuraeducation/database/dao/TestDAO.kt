package com.futuraeducation.database.dao

import androidx.room.*
import com.futuraeducation.model.Quesion
import com.futuraeducation.model.TestPaperVo

@Dao
interface TestDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTestPaper(testPaperVo: TestPaperVo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addQuestionList(question: Quesion)

    @Transaction
    @Query("SELECT * FROM question_list WHERE testPaperId = :id")
    fun getQuestion(id: String): MutableList<Quesion>

    @Transaction
    @Query("UPDATE question_list SET answer=:answer WHERE id = :id")
    fun updateQuestion(id: String, answer: String?)

}