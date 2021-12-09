package com.futuraeducation.network

import com.futuraeducation.model.assignment.AssignmentModel
import com.futuraeducation.model.onBoarding.CompletedSession
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @POST("session/getCompletedSessionsSubject")
    suspend fun getData(@Body jsonObject: JSONObject , @HeaderMap hashMap: HashMap<String, String>): Response<List<CompletedSession>>

    @GET("assignment/getAssignment/{batchId}")
    suspend fun getAssignments(@Path("batchId")  url: String, @HeaderMap hashMap: HashMap<String, String>): Response<List<AssignmentModel>>

}