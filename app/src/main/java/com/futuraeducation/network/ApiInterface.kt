package com.futuraeducation.network

import com.futuraeducation.model.*
import com.futuraeducation.model.assignment.AssignmentModel
import com.futuraeducation.model.live.Batch
import com.futuraeducation.model.onBoarding.CompletedSession
import com.futuraeducation.model.publish.PublishMaterialResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @POST("session/getCompletedSessionsSubject")
    suspend fun getData(@Body jsonObject: JSONObject , @HeaderMap hashMap: HashMap<String, String>): Response<List<CompletedSession>>

    @GET("assignment/getAssignment/{batchId}")
    suspend fun getAssignments(@Path("batchId")  url: String, @HeaderMap hashMap: HashMap<String, String>): Response<List<AssignmentModel>>

    @Multipart
    @POST("addAssignment")
    suspend fun postAssignment(@Part("Files") attachment: String,
                               @Query("title") title: String, @Query("description") description:String,
                               @Query("subject") subject:String, @Query("batchId") batchId:String,
                               @Query("date") date:String, @Query("teacherName") teacherName:String,
                               @HeaderMap hashMap: HashMap<String, String>): Response<ResponseBody>

    @GET("course/child/{courseId}")
    suspend fun getCourseSubject(@Path("courseId")  url: String, @HeaderMap hashMap: HashMap<String, String>): Response<CourseSubjectResponse>

    @GET("course/child/{subjectId}")
    suspend fun getSubjectChapter(@Path("subjectId")  url: String, @HeaderMap hashMap: HashMap<String, String>): Response<CourseSubjectResponse>

    @GET("course/child/{chapterId}")
    suspend fun getChapterTopics(@Path("chapterId")  url: String, @HeaderMap hashMap: HashMap<String, String>): Response<CourseSubjectResponse>

    @GET("course/allCoursesByCoaching")
    suspend fun getCoursesByCoachingCenter(@Query("coachingCentreId") coachingCentreId: String, @HeaderMap hashMap: HashMap<String, String>): Response<List<Datum>>

    @GET("coachingCentreBranch/branchesForCourse")
    suspend fun getBranchesForCourse(@Query("coachingCentreId") coachingCentreId: String,@Query("courseId") courseId: String, @HeaderMap hashMap: HashMap<String, String>): Response<List<Branch>>

    @GET("batch/batchesByCourse")
    suspend fun getBatchesByCourse(@QueryMap options: Map<String, String>, @HeaderMap hashMap: HashMap<String, String>): Response<List<Batch>>

    @GET("material/unPublishedMaterials")
    suspend fun getMaterial(@QueryMap options: Map<String, String>, @HeaderMap hashMap: HashMap<String, String>): Response<List<Material>>


    @POST("materialAccess/save")
    suspend fun publishMaterials(@Body jsonObject: JsonObject , @HeaderMap hashMap: HashMap<String, String>): Response<PublishMaterialResponse>

}