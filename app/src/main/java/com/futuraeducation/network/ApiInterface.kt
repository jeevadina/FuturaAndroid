package com.futuraeducation.network

import com.futuraeducation.model.onBoarding.CompletedSession
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ApiInterface {

    @POST("session/getCompletedSessionsSubject")
    suspend fun getData(@Body jsonObject: JSONObject , @HeaderMap hashMap: HashMap<String, String>): Response<List<CompletedSession>>

}