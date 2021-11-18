package com.futuraeducation.database

import android.content.Context
import android.os.AsyncTask
import com.futuraeducation.database.model.NotificationItem
import com.futuraeducation.database.model.VideoPlayedItem
import com.futuraeducation.model.CompletedTest
import com.futuraeducation.model.Quesion
import com.futuraeducation.model.TestPaperVo
import com.futuraeducation.model.TestResultsModel
import com.futuraeducation.model.onBoarding.AverageBatchTests

class DatabaseHelper(context: Context) {
    private var db: AppDatabase? = null

    init {
        db = AppDatabase.getInstance(context)
    }

    fun saveAvg(notificationItem: AverageBatchTests) {

        db!!.averageBatchDao.addAvg(notificationItem)
    }

    fun getAllAverageBatchTest(): MutableList<AverageBatchTests> {
        return db!!.averageBatchDao.getAll()
    }

    fun saveResult(testResultsModel: TestResultsModel) {
        db!!.resultsDao.addResult(testResultsModel)
    }

    fun getAllResult(): MutableList<TestResultsModel> {
        return db!!.resultsDao.getAll()
    }

    fun getTestResponse(testPaperId: String): TestResultsModel {
        return db!!.resultsDao.getResponse(testPaperId)
    }

    fun isExists(testPaperId: String): Boolean {
        return db!!.resultsDao.isExists(testPaperId)
    }

    fun saveNotificationItem(notificationItem: NotificationItem): Long {
        var notificationID: Long = 0


        object : AsyncTask<NotificationItem, Void, Long>() {
            override fun doInBackground(vararg params: NotificationItem): Long {
                if (params != null) {
                    val saveFavNews = params[0]
                    if (saveFavNews.notifyID > 0) {
                        db!!.notificationDao.update(notificationItem)
                    } else {
                        notificationID = db!!.notificationDao.add(notificationItem)
                    }
                } else {
                    notificationID = db!!.notificationDao.add(notificationItem)
                }
                return notificationID
            }


        }.execute(notificationItem)

        return notificationID
    }

    fun getAllNotification(): List<NotificationItem> {
        return db!!.notificationDao.getAll()
    }

    fun getAllUnreadNotification(): List<NotificationItem> {
        return db!!.notificationDao.getAllUnreadNotification()
    }

    fun saveTestPaper(testPaperVo: TestPaperVo) {
        db!!.testDAO.addTestPaper(testPaperVo)
    }

    fun addQuestions(question: Quesion) {
        db!!.testDAO.addQuestionList(question)
    }

    fun getQuestionList(testPaperId: String): MutableList<Quesion> {
        return db!!.testDAO.getQuestion(testPaperId)
    }

    fun updateAnswer(questionId: String, answer: String?) {
        db!!.testDAO.updateQuestion(questionId, answer)
    }

    fun addCompletedTest(completedTest: CompletedTest) {
        if (completedTest.id == 0) {
            db!!.completedTestDAO.add(completedTest)
        } else {
            db!!.completedTestDAO.update(completedTest)
        }
    }

    fun getCompletedTest(): List<CompletedTest> {
        return db!!.completedTestDAO.getAll()
    }

    fun getCompletedTest(id:String): CompletedTest {
        return db!!.completedTestDAO.getTest(id)
    }

    fun deleteTest(id:String) {
        db!!.completedTestDAO.deleteTest(id)
    }

    fun saveVideoData(sectionItem: VideoPlayedItem){
        db!!.videoDao.addVideo(sectionItem)
    }

    fun getAllVideos() :MutableList<VideoPlayedItem>{
        return db!!.videoDao.getAll()
    }
}