package com.futuraeducation.model

import com.google.gson.annotations.SerializedName


data class LiveResponse(
    @SerializedName("data") val data: List<Data>,
    @SerializedName("page") val page: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("totalCount") val totalCount: Int,
    @SerializedName("totalPages") val totalPages: Int
)

data class Data(
    @SerializedName("batchIds") val batchIds: List<String>,
    @SerializedName("batchList") val batchList: List<Batch>,
    @SerializedName("branchIds") val branchIds: List<String>,
    @SerializedName("branchList") val branchList: List<Branch>,
    @SerializedName("coachingCentre") val coachingCentre: CoachingCentreXX,
    @SerializedName("coachingCentreId") val coachingCentreId: String,
    @SerializedName("course") val course: CourseX,
    @SerializedName("courseId") val courseId: String,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("endDateTime") val endDateTime: Long,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("id") val id: String,
    @SerializedName("platform") val platform: String,
    @SerializedName("sessionDate") val sessionDate: Long,
    @SerializedName("sessionName") val sessionName: String,
    @SerializedName("sessionRecordingUrl") val sessionRecordingUrl: String,
    @SerializedName("sessionType") val sessionType: String,
    @SerializedName("startDateTime") val startDateTime: Long,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("status") val status: String,
    @SerializedName("subject") val subject: Subject,
    @SerializedName("subjectId") val subjectId: String,
    @SerializedName("teacherId") val teacherId: String,
    @SerializedName("teacherUrl") val teacherUrl: String,
    @SerializedName("topicName") val topicName: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String,
    @SerializedName("url") val url: String,
    @SerializedName("userDetail") val userDetail: UserDetail,
    @SerializedName("webExUserId") val webExUserId: String,
    @SerializedName("webexMeetingId") val webexMeetingId: String,
    @SerializedName("webexSessionKey") val webexSessionKey: String,
    @SerializedName("webexSessionPass") val webexSessionPass: String,
    @SerializedName("webexUser") val webexUser: String,
    @SerializedName("zoomUser") val zoomUser: ZoomUser,
    @SerializedName("zoomUserId") val zoomUserId: String
)

data class Batch(
    @SerializedName("additionalCourse") val additionalCourse: String,
    @SerializedName("additionalCourseId") val additionalCourseId: String,
    @SerializedName("batchEndDate") val batchEndDate: String,
    @SerializedName("batchName") val batchName: String,
    @SerializedName("batchSize") val batchSize: String,
    @SerializedName("batchStartDate") val batchStartDate: String,
    @SerializedName("coachingCenterBranchId") val coachingCenterBranchId: String,
    @SerializedName("coachingCenterId") val coachingCenterId: String,
    @SerializedName("coachingCentre") val coachingCentre: CoachingCentre,
    @SerializedName("coachingCentreBranch") val coachingCentreBranch: CoachingCentreBranch,
    @SerializedName("course") val course: Course,
    @SerializedName("courseId") val courseId: String,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("description") val description: String,
    @SerializedName("endTiming") val endTiming: String,
    @SerializedName("id") val id: String,
    @SerializedName("startTiming") val startTiming: String,
    @SerializedName("status") val status: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String
)

data class Branch(
    @SerializedName("address1") val address1: String,
    @SerializedName("address2") val address2: String,
    @SerializedName("branchName") val branchName: String,
    @SerializedName("city") val city: String,
    @SerializedName("coachingCenterId") val coachingCenterId: String,
    @SerializedName("country") val country: String,
    @SerializedName("courseIds") val courseIds: String,
    @SerializedName("courseList") val courseList: String,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("email") val email: String,
    @SerializedName("id") val id: String,
    @SerializedName("isMainBranch") val isMainBranch: String,
    @SerializedName("mobileNumber") val mobileNumber: String,
    @SerializedName("questionLimit") val questionLimit: String,
    @SerializedName("state") val state: String,
    @SerializedName("status") val status: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String,
    @SerializedName("webexUserIds") val webexUserIds: String,
    @SerializedName("webexUsers") val webexUsers: String,
    @SerializedName("zipCode") val zipCode: String
)

data class CoachingCentreXX(
    @SerializedName("address1") val address1: String,
    @SerializedName("address2") val address2: String,
    @SerializedName("city") val city: String,
    @SerializedName("coachingCenterCode") val coachingCenterCode: String,
    @SerializedName("coachingCentreName") val coachingCentreName: String,
    @SerializedName("country") val country: String,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("email") val email: String,
    @SerializedName("expiryOn") val expiryOn: String,
    @SerializedName("id") val id: String,
    @SerializedName("logoUrl") val logoUrl: String,
    @SerializedName("mobileNumber") val mobileNumber: String,
    @SerializedName("questionLimit") val questionLimit: String,
    @SerializedName("state") val state: String,
    @SerializedName("status") val status: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String,
    @SerializedName("zipCode") val zipCode: String
)

data class CourseX(
    @SerializedName("coachingCentre") val coachingCentre: CoachingCentreXXX,
    @SerializedName("coachingCentreId") val coachingCentreId: String,
    @SerializedName("courseName") val courseName: String,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: String,
    @SerializedName("parentId") val parentId: String,
    @SerializedName("parentName") val parentName: String,
    @SerializedName("status") val status: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String
)

data class Subject(
    @SerializedName("coachingCentre") val coachingCentre: String,
    @SerializedName("coachingCentreId") val coachingCentreId: String,
    @SerializedName("courseName") val courseName: String,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: String,
    @SerializedName("parentId") val parentId: String,
    @SerializedName("parentName") val parentName: String,
    @SerializedName("status") val status: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String
)

data class UserDetail(
    @SerializedName("address1") val address1: String,
    @SerializedName("address2") val address2: String,
    @SerializedName("batchIds") val batchIds: String,
    @SerializedName("batchList") val batchList: String,
    @SerializedName("branchIds") val branchIds: String,
    @SerializedName("branchList") val branchList: String,
    @SerializedName("city") val city: String,
    @SerializedName("coachingCenterId") val coachingCenterId: String,
    @SerializedName("coachingCentre") val coachingCentre: CoachingCentreXXXX,
    @SerializedName("country") val country: String,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("dob") val dob: String,
    @SerializedName("email") val email: String,
    @SerializedName("enrollmentNumber") val enrollmentNumber: String,
    @SerializedName("fatherName") val fatherName: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("id") val id: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("mobileNumber") val mobileNumber: String,
    @SerializedName("profileImagePath") val profileImagePath: String,
    @SerializedName("qualification") val qualification: String,
    @SerializedName("salutation") val salutation: String,
    @SerializedName("shortDiscription") val shortDiscription: String,
    @SerializedName("state") val state: String,
    @SerializedName("status") val status: String,
    @SerializedName("studentAccess") val studentAccess: Boolean,
    @SerializedName("subject") val subject: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String,
    @SerializedName("uploadFileId") val uploadFileId: String,
    @SerializedName("user") val user: User,
    @SerializedName("userId") val userId: String,
    @SerializedName("userType") val userType: String,
    @SerializedName("yearOfExperience") val yearOfExperience: String,
    @SerializedName("zipCode") val zipCode: String
)

data class ZoomUser(
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("id") val id: String,
    @SerializedName("status") val status: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String,
    @SerializedName("zoomUser") val zoomUser: String
)

data class CoachingCentre(
    @SerializedName("address1") val address1: String,
    @SerializedName("address2") val address2: String,
    @SerializedName("city") val city: String,
    @SerializedName("coachingCenterCode") val coachingCenterCode: String,
    @SerializedName("coachingCentreName") val coachingCentreName: String,
    @SerializedName("country") val country: String,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("email") val email: String,
    @SerializedName("expiryOn") val expiryOn: String,
    @SerializedName("id") val id: String,
    @SerializedName("logoUrl") val logoUrl: String,
    @SerializedName("mobileNumber") val mobileNumber: String,
    @SerializedName("questionLimit") val questionLimit: String,
    @SerializedName("state") val state: String,
    @SerializedName("status") val status: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String,
    @SerializedName("zipCode") val zipCode: String
)

data class CoachingCentreBranch(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("coachingCenterId")
    var coachingCenterId: String? = null,

    @SerializedName("branchName")
    var branchName: String? = null,

    @SerializedName("isMainBranch")
    var isMainBranch: String? = null,

    @SerializedName("status")
    var status: String? = null
)

data class Course(
    @SerializedName("coachingCentre") val coachingCentre: CoachingCentreX,
    @SerializedName("coachingCentreId") val coachingCentreId: String,
    @SerializedName("courseName") val courseName: String,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: String,
    @SerializedName("parentId") val parentId: String,
    @SerializedName("parentName") val parentName: String,
    @SerializedName("status") val status: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String
)

data class CoachingCentreX(
    @SerializedName("address1") val address1: String,
    @SerializedName("address2") val address2: String,
    @SerializedName("city") val city: String,
    @SerializedName("coachingCenterCode") val coachingCenterCode: String,
    @SerializedName("coachingCentreName") val coachingCentreName: String,
    @SerializedName("country") val country: String,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("email") val email: String,
    @SerializedName("expiryOn") val expiryOn: String,
    @SerializedName("id") val id: String,
    @SerializedName("logoUrl") val logoUrl: String,
    @SerializedName("mobileNumber") val mobileNumber: String,
    @SerializedName("questionLimit") val questionLimit: String,
    @SerializedName("state") val state: String,
    @SerializedName("status") val status: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String,
    @SerializedName("zipCode") val zipCode: String
)

data class CoachingCentreXXX(
    @SerializedName("address1") val address1: String,
    @SerializedName("address2") val address2: String,
    @SerializedName("city") val city: String,
    @SerializedName("coachingCenterCode") val coachingCenterCode: String,
    @SerializedName("coachingCentreName") val coachingCentreName: String,
    @SerializedName("country") val country: String,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("email") val email: String,
    @SerializedName("expiryOn") val expiryOn: String,
    @SerializedName("id") val id: String,
    @SerializedName("logoUrl") val logoUrl: String,
    @SerializedName("mobileNumber") val mobileNumber: String,
    @SerializedName("questionLimit") val questionLimit: String,
    @SerializedName("state") val state: String,
    @SerializedName("status") val status: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String,
    @SerializedName("zipCode") val zipCode: String
)

data class CoachingCentreXXXX(
    @SerializedName("address1") val address1: String,
    @SerializedName("address2") val address2: String,
    @SerializedName("city") val city: String,
    @SerializedName("coachingCenterCode") val coachingCenterCode: String,
    @SerializedName("coachingCentreName") val coachingCentreName: String,
    @SerializedName("country") val country: String,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("email") val email: String,
    @SerializedName("expiryOn") val expiryOn: String,
    @SerializedName("id") val id: String,
    @SerializedName("logoUrl") val logoUrl: String,
    @SerializedName("mobileNumber") val mobileNumber: String,
    @SerializedName("questionLimit") val questionLimit: String,
    @SerializedName("state") val state: String,
    @SerializedName("status") val status: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String,
    @SerializedName("zipCode") val zipCode: String
)

data class User(
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("createdBy") val createdBy: String,
    @SerializedName("id") val id: String,
    @SerializedName("loginDevice") val loginDevice: String,
    @SerializedName("newPassword") val newPassword: String,
    @SerializedName("password") val password: String,
    @SerializedName("status") val status: String,
    @SerializedName("updatedAt") val updatedAt: Long,
    @SerializedName("updatedBy") val updatedBy: String,
    @SerializedName("userName") val userName: String
)

class TestResultsData {
    @SerializedName("testName") var testName: String? = null
    @SerializedName("testType") var testType: String? = null
    @SerializedName("score") var score: Int? = null
    @SerializedName("highestScore") var highestScore: Int? = null
    @SerializedName("rankInTest") var rankInTest: Int? = null
    @SerializedName("attempt") var attempt: String? = null
    @SerializedName("testSubmissionCreatedDate") var testSubmissionCreatedDate: Long? = null
    @SerializedName("testSubmissionUpdatedDate") var testSubmissionUpdatedDate: Long? = null
    @SerializedName("testPaperId") var testPaperId: String? = null
}