package com.futuraeducation.network

import com.futuraeducation.model.assignment.AssignmentModel
import com.futuraeducation.model.onBoarding.CompletedSession
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @POST("session/getCompletedSessionsSubject")
    suspend fun getData(@Body jsonObject: JSONObject , @HeaderMap hashMap: HashMap<String, String>): Response<List<CompletedSession>>

    @GET("assignment/getAssignment/{batchId}")
    suspend fun getAssignments(@Path("batchId")  url: String, @HeaderMap hashMap: HashMap<String, String>): Response<List<AssignmentModel>>

    @POST("addAssignment")
    suspend fun postAssignment(@Query("title") title: String, @Query("description") description:String,
                               @Query("subject") subject:String, @Query("batchId") batchId:String,
                               @Query("date") date:String, @Query("teacherName") teacherName:String,
                               @HeaderMap hashMap: HashMap<String, String>): Response<ResponseBody>

}