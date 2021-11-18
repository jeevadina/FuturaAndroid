package com.futuraeducation.adapter

import com.futuraeducation.model.MOCKTEST
import com.futuraeducation.model.onBoarding.AttemptedTest

interface TestClickListener {
    fun onTestClicked(isClicked : Boolean,mockTest: MOCKTEST)
    fun onResultClicked(id : String)
    fun onResultClicked(attempt :Int, studentId : String, testPaperId: String)
    fun onReviewClicked(attempt : AttemptedTest)
}