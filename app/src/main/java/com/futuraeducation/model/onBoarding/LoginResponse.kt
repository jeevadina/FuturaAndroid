package com.futuraeducation.model.onBoarding

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class LoginResponse(
    @SerializedName("data") var data: LoginData? = null,
)

data class LoginData(
    @SerializedName("role") var role: String? = null,
    @SerializedName("token") var token: String? = null,
    @SerializedName("validity") var validity: Any? = null ,
    @SerializedName("userDetail") var userDetail: UserDetails? = null,
)

data class UserDetails (
    @SerializedName("userDetailId")
    var userDetailId: String? = null,
    @SerializedName("usersId")
    var usersId: String? = null,
    @SerializedName("firstName")
    var firstName: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("userName")
    var userName: String? = null,
    @SerializedName("userType")
    var userType: String? = null,
    @SerializedName("coachingCentre")
    var coachingCentre: CoachingCentre? = null,
    @SerializedName("coachingCenterId")
    var coachingCenterId: String? = null,
    @SerializedName("studentAccess")
    var studentAccess: Boolean? = null,
    @SerializedName("subject")
    var subject: String? = null,
    @SerializedName("branchList")
    var branchList: List<branchItem>? = null,
    @SerializedName("batchList")
    var batchList: ArrayList<batchItem>? = null
)


data class branchItem(
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

data class CoachingCentre2(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("coachingCentreName")
    var coachingCentreName: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("logoUrl")
    var logoUrl: String? = null,
    @SerializedName("expiryOn")
    var expiryOn: Any? = null
)
data class CoachingCentre1 (
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("coachingCentreName")
    var coachingCentreName: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("logoUrl")
    var logoUrl: String? = null,
    @SerializedName("expiryOn")
    var expiryOn: Any? = null
)

data class CoachingCentre(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("coachingCentreName")
    var coachingCentreName: String? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("logoUrl")
    var logoUrl: String? = null,

    @SerializedName("expiryOn")
    var expiryOn: Any? = null
)

data class batchItem(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("batchName")
    var batchName: String? = null,
    @SerializedName("coachingCentre")
    var coachingCentre: CoachingCentre1? = null,
    @SerializedName("coachingCenterId")
    var coachingCenterId: String? = null,
    @SerializedName("course")
    var course: Course? = null,
    @SerializedName("courseId")
    var courseId: String? = null,
    @SerializedName("coachingCentreBranch")
    var coachingCentreBranch: CoachingCentreBranch1? = null,
    @SerializedName("coachingCenterBranchId")
    var coachingCenterBranchId: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("additionalCourseId")
    var additionalCourseId: String? = null
)

data class CoachingCentreBranch1(
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
    @SerializedName("id") var id: String? = null,
    @SerializedName("courseName") var courseName: String? = null,
    @SerializedName("parentId") var parentId: String? = null,
    @SerializedName("parentName") var parentName: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("coachingCentre") var coachingCentre: CoachingCentre2? = null,
    @SerializedName("coachingCentreId") var coachingCentreId: String? = null,
)

@Entity(tableName = "AverageBatch")
data class AverageBatchTests(
    @PrimaryKey(autoGenerate = true)
    var averageBatchID: Int = 0,
    @ColumnInfo(name = "studentAverage")
    @SerializedName("studentAverage") var studentAverage: Double,
    @ColumnInfo(name = "classAverage")
    @SerializedName("classAverage") var classAverage: Double? = null,
    @ColumnInfo(name = "rank")
    @SerializedName("rank") var rank: Int? = null,
    @ColumnInfo(name = "total_students")
    @SerializedName("total_students") var totalStudents: Int? = null,
    @ColumnInfo(name = "total_test")
    @SerializedName("total_test") var totalTest: Int? = null,
    @ColumnInfo(name = "student_test")
    @SerializedName("student_test") var studentTest: Int? = null,
    @ColumnInfo(name = "topperAverage")
    @SerializedName("topperAverage") var topperAverage: Double? = null,
)

data class UnAttempted(
    @SerializedName("MOCK_TEST") var mockTest: List<MockTest>? = null,
    @SerializedName("PRACTICE") var practice: List<String>? = null
)

data class MockTest(
    @SerializedName("publishDate") var publishDate: Long? = null,
    @SerializedName("publishTime") var publishTime: String? = null,
    @SerializedName("publishDateTime") var publishDateTime: Long? = null,
    @SerializedName("expiryDate") var expiryDate: String? = null,
    @SerializedName("expiryTime") var expiryTime: String? = null,
    @SerializedName("expiryDateTime") var expiryDateTime: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("duration") var duration: Int? = null,
    @SerializedName("questionCount") var questionCount: Int? = null,
    @SerializedName("totalAttempts") var totalAttempts: Int? = null,
    @SerializedName("testCode") var testCode: String? = null,
    @SerializedName("testType") var testType: String? = null,
    @SerializedName("studentAnswerId") var studentAnswerId: String? = null,
    @SerializedName("completeStatus") var completeStatus: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("studentId") var studentId: String? = null,
    @SerializedName("testPaperId") var testPaperId: String? = null,
    @SerializedName("correctMarks") var correctMarks: String? = null,
    @SerializedName("studentName") var studentName: String? = null,
    @SerializedName("totalDuration") var totalDuration: String? = null,
    @SerializedName("totalMarks") var totalMarks: String? = null,
)

data class AttemptedResponse(
    @SerializedName("MOCK_TEST")
    var mOCKTEST: MutableList<AttemptedTest>,
    @SerializedName("PRACTICE")
    var pRACTICE: MutableList<AttemptedTest>
)

@Parcelize
data class AttemptedTest (
    @SerializedName("completeStatus")
    val completeStatus: String?,
    @SerializedName("correctMarks")
    val correctMarks: Int = 1,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("expiryDate")
    val expiryDate: String?,
    @SerializedName("expiryDateTime")
    val expiryDateTime: String?,
    @SerializedName("expiryTime")
    val expiryTime: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("publishDate")
    val publishDate: Long,
    @SerializedName("publishDateTime")
    val publishDateTime: Long,
    @SerializedName("publishTime")
    val publishTime: String,
    @SerializedName("questionCount")
    val questionCount: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("studentAnswerId")
    val studentAnswerId: String,
    @SerializedName("studentId")
    val studentId: String,
    @SerializedName("studentName")
    val studentName: String?,
    @SerializedName("testCode")
    val testCode: String,
    @SerializedName("testPaperId")
    val testPaperId: String,
    @SerializedName("testType")
    val testType: String,
    @SerializedName("totalAttempts")
    val totalAttempts: Int,
    @SerializedName("totalDuration")
    val totalDuration: String?,
    @SerializedName("totalMarks")
    val totalMarks: String?
) : Parcelable


@Serializable
data class TestStatusResponse(
    @SerialName("data")
    val `data`: String
)