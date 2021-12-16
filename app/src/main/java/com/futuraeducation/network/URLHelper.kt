package com.futuraeducation.network

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.futuraeducation.utils.Define

object URLHelper {
    val mRemoteConfig = Firebase.remoteConfig
    private var baseURL = mRemoteConfig.getString(Define.BASE_URL)
    private var baseBATH = mRemoteConfig.getString(Define.BASE_PATH)

     var productionUrl = "http://adminconsole.upmyranks.com/app/api/v1/"
    // var productionUrl = "https://api.upmyranks.com/app/api/v1/"


    private val baseURLSession = productionUrl + "session/"
    val getSessions = baseURLSession + "getSessions"
    val getCompletedSessionsSubject = baseURLSession + "getCompletedSessionsSubject"
    val baseURLAuth = productionUrl + "auth/"
    val courseURL = productionUrl + "course/child/"
    val courseURL1 = productionUrl + "course/child"
    val assignment = productionUrl + "assignment/"
    private val testPaperAssign = productionUrl + "testPaperAssign/"
    private val testPaperVo = productionUrl + "testPaperVo/"
    private val studentAnswer = productionUrl + "studentAnswer/"
    private val studentTestPaperAnswer = productionUrl + "studentTestPaperAnswer/"
    val answeredTestPapers = studentTestPaperAnswer + "answeredTestPaper"
    private val material = productionUrl + "material/"
    val testResultUrl = studentTestPaperAnswer + "myTestResultList"
    val averageBatchTests = testPaperAssign + "averageBatchTests"
    val unattemptedTests = testPaperAssign + "unattemptedTests"
    val attemptedTests = testPaperAssign + "attemptedTests"
    val scheduleTestsForStudent = testPaperAssign + "scheduleTestsForStudent"
    val testPaperForStudent = testPaperVo + "testPaperForStudent"
    val getStudentTestPaper = testPaperVo + "getStudentTestPaper"
    val testStatus = studentAnswer + "testStatus"
    val submitTestPaper = studentTestPaperAnswer + "submitTestPaper"
    val answeredTestPaper = studentTestPaperAnswer + "answeredTestPaper"
    val next = studentAnswer + "next"
    val publishedMaterialsByChapter = material + "publishedMaterialsByChapter"
    val logout = baseURLAuth + "logout"
    val qrcode = productionUrl + "qrcode/getById/"
}