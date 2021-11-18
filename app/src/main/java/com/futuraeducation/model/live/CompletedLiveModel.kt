package com.futuraeducation.model.live

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CompletedLiveModel {
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("pageSize")
    @Expose
    var pageSize: Int? = null

    @SerializedName("totalCount")
    @Expose
    var totalCount: Int? = null

    @SerializedName("totalPages")
    @Expose
    var totalPages: Int? = null
}

class Datum {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("sessionName")
    @Expose
    var sessionName: String? = null

    @SerializedName("sessionType")
    @Expose
    var sessionType: String? = null

    @SerializedName("coachingCentre")
    @Expose
    var coachingCentre: CoachingCentre? = null

    @SerializedName("coachingCentreId")
    @Expose
    var coachingCentreId: String? = null

    @SerializedName("course")
    @Expose
    var course: Course? = null

    @SerializedName("courseId")
    @Expose
    var courseId: String? = null

    @SerializedName("subject")
    @Expose
    var subject: Subject? = null

    @SerializedName("subjectId")
    @Expose
    var subjectId: String? = null

    @SerializedName("topicName")
    @Expose
    var topicName: String? = null

    @SerializedName("teacherId")
    @Expose
    var teacherId: String? = null

    @SerializedName("sessionDate")
    @Expose
    var sessionDate: Long? = null

    @SerializedName("startTime")
    @Expose
    var startTime: String? = null

    @SerializedName("endTime")
    @Expose
    var endTime: String? = null

    @SerializedName("platform")
    @Expose
    var platform: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("teacherUrl")
    @Expose
    var teacherUrl: String? = null

    @SerializedName("startDateTime")
    @Expose
    var startDateTime: Long? = null

    @SerializedName("endDateTime")
    @Expose
    var endDateTime: Long? = null

    @SerializedName("zoomUser")
    @Expose
    var zoomUser: ZoomUser? = null

    @SerializedName("zoomUserId")
    @Expose
    var zoomUserId: String? = null

    @SerializedName("webexUser")
    @Expose
    var webexUser: Any? = null

    @SerializedName("webExUserId")
    @Expose
    var webExUserId: Any? = null

    @SerializedName("webexSessionKey")
    @Expose
    var webexSessionKey: Any? = null

    @SerializedName("webexSessionPass")
    @Expose
    var webexSessionPass: Any? = null

    @SerializedName("webexMeetingId")
    @Expose
    var webexMeetingId: Any? = null

    @SerializedName("sessionRecordingUrl")
    @Expose
    var sessionRecordingUrl: Any? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: Long? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: Long? = null

    @SerializedName("createdBy")
    @Expose
    var createdBy: Any? = null

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: Any? = null

    @SerializedName("batchIds")
    @Expose
    var batchIds: List<String>? = null

    @SerializedName("batchList")
    @Expose
    var batchList: List<Batch>? = null

    @SerializedName("branchIds")
    @Expose
    var branchIds: List<String>? = null

    @SerializedName("branchList")
    @Expose
    var branchList: List<Branch>? = null

    @SerializedName("userDetail")
    @Expose
    var userDetail: UserDetail? = null

    @SerializedName("password")
    @Expose
    var password: Any? = null
}

class Subject {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("courseName")
    @Expose
    var courseName: String? = null

    @SerializedName("parentId")
    @Expose
    var parentId: String? = null

    @SerializedName("parentName")
    @Expose
    var parentName: Any? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("coachingCentre")
    @Expose
    var coachingCentre: Any? = null

    @SerializedName("coachingCentreId")
    @Expose
    var coachingCentreId: Any? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: Long? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: Long? = null

    @SerializedName("createdBy")
    @Expose
    var createdBy: Any? = null

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: Any? = null

    @SerializedName("type")
    @Expose
    var type: String? = null
}

class User {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("userName")
    @Expose
    var userName: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("newPassword")
    @Expose
    var newPassword: Any? = null

    @SerializedName("loginDevice")
    @Expose
    var loginDevice: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: Long? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: Long? = null

    @SerializedName("createdBy")
    @Expose
    var createdBy: String? = null

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: Any? = null

    @SerializedName("passwordUpdated")
    @Expose
    var passwordUpdated: String? = null
}


class UserDetail {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("uploadFileId")
    @Expose
    var uploadFileId: Any? = null

    @SerializedName("userId")
    @Expose
    var userId: String? = null

    @SerializedName("salutation")
    @Expose
    var salutation: String? = null

    @SerializedName("firstName")
    @Expose
    var firstName: String? = null

    @SerializedName("lastName")
    @Expose
    var lastName: String? = null

    @SerializedName("mobileNumber")
    @Expose
    var mobileNumber: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("dob")
    @Expose
    var dob: String? = null

    @SerializedName("fatherName")
    @Expose
    var fatherName: String? = null

    @SerializedName("qualification")
    @Expose
    var qualification: String? = null

    @SerializedName("yearOfExperience")
    @Expose
    var yearOfExperience: String? = null

    @SerializedName("userType")
    @Expose
    var userType: String? = null

    @SerializedName("address1")
    @Expose
    var address1: String? = null

    @SerializedName("address2")
    @Expose
    var address2: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("zipCode")
    @Expose
    var zipCode: String? = null

    @SerializedName("enrollmentNumber")
    @Expose
    var enrollmentNumber: String? = null

    @SerializedName("profileImagePath")
    @Expose
    var profileImagePath: Any? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("coachingCentre")
    @Expose
    var coachingCentre: CoachingCentre? = null

    @SerializedName("coachingCenterId")
    @Expose
    var coachingCenterId: String? = null

    @SerializedName("shortDiscription")
    @Expose
    var shortDiscription: Any? = null

    @SerializedName("studentAccess")
    @Expose
    var studentAccess: Boolean? = null

    @SerializedName("subject")
    @Expose
    var subject: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: Long? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: Long? = null

    @SerializedName("user")
    @Expose
    var user: User? = null

    @SerializedName("createdBy")
    @Expose
    var createdBy: String? = null

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: Any? = null

    @SerializedName("branchIds")
    @Expose
    var branchIds: Any? = null

    @SerializedName("branchList")
    @Expose
    var branchList: Any? = null

    @SerializedName("batchIds")
    @Expose
    var batchIds: Any? = null

    @SerializedName("batchList")
    @Expose
    var batchList: Any? = null
}

class ZoomUser {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("zoomUser")
    @Expose
    var zoomUser: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("createdBy")
    @Expose
    var createdBy: Any? = null

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: Any? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: Long? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: Long? = null
}

class Batch {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("batchName")
    @Expose
    var batchName: String? = null

    @SerializedName("coachingCentre")
    @Expose
    var coachingCentre: CoachingCentre? = null

    @SerializedName("coachingCenterId")
    @Expose
    var coachingCenterId: String? = null

    @SerializedName("course")
    @Expose
    var course: Course? = null

    @SerializedName("courseId")
    @Expose
    var courseId: String? = null

    @SerializedName("coachingCentreBranch")
    @Expose
    var coachingCentreBranch: CoachingCentreBranch? = null

    @SerializedName("coachingCenterBranchId")
    @Expose
    var coachingCenterBranchId: String? = null

    @SerializedName("batchStartDate")
    @Expose
    var batchStartDate: String? = null

    @SerializedName("batchEndDate")
    @Expose
    var batchEndDate: String? = null

    @SerializedName("startTiming")
    @Expose
    var startTiming: String? = null

    @SerializedName("endTiming")
    @Expose
    var endTiming: String? = null

    @SerializedName("batchSize")
    @Expose
    var batchSize: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("additionalCourseId")
    @Expose
    var additionalCourseId: Any? = null

    @SerializedName("additionalCourse")
    @Expose
    var additionalCourse: Any? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: Long? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: Long? = null

    @SerializedName("createdBy")
    @Expose
    var createdBy: Any? = null

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: Any? = null
}

class Branch {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("coachingCenterId")
    @Expose
    var coachingCenterId: String? = null

    @SerializedName("branchName")
    @Expose
    var branchName: String? = null

    @SerializedName("address1")
    @Expose
    var address1: String? = null

    @SerializedName("address2")
    @Expose
    var address2: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("zipCode")
    @Expose
    var zipCode: String? = null

    @SerializedName("mobileNumber")
    @Expose
    var mobileNumber: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("isMainBranch")
    @Expose
    var isMainBranch: String? = null

    @SerializedName("questionLimit")
    @Expose
    var questionLimit: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: Long? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: Long? = null

    @SerializedName("createdBy")
    @Expose
    var createdBy: Any? = null

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: Any? = null

    @SerializedName("courseIds")
    @Expose
    var courseIds: Any? = null

    @SerializedName("courseList")
    @Expose
    var courseList: Any? = null

    @SerializedName("webexUserIds")
    @Expose
    var webexUserIds: Any? = null

    @SerializedName("webexUsers")
    @Expose
    var webexUsers: Any? = null
}

class CoachingCentre {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("coachingCentreName")
    @Expose
    var coachingCentreName: String? = null

    @SerializedName("mobileNumber")
    @Expose
    var mobileNumber: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("address1")
    @Expose
    var address1: String? = null

    @SerializedName("address2")
    @Expose
    var address2: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("zipCode")
    @Expose
    var zipCode: String? = null

    @SerializedName("expiryOn")
    @Expose
    var expiryOn: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("coachingCenterCode")
    @Expose
    var coachingCenterCode: String? = null

    @SerializedName("questionLimit")
    @Expose
    var questionLimit: String? = null

    @SerializedName("logoUrl")
    @Expose
    var logoUrl: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: Long? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: Long? = null

    @SerializedName("createdBy")
    @Expose
    var createdBy: Any? = null

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: Any? = null
}

class CoachingCentreBranch {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("coachingCenterId")
    @Expose
    var coachingCenterId: String? = null

    @SerializedName("branchName")
    @Expose
    var branchName: String? = null

    @SerializedName("address1")
    @Expose
    var address1: String? = null

    @SerializedName("address2")
    @Expose
    var address2: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("zipCode")
    @Expose
    var zipCode: String? = null

    @SerializedName("mobileNumber")
    @Expose
    var mobileNumber: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("isMainBranch")
    @Expose
    var isMainBranch: String? = null

    @SerializedName("questionLimit")
    @Expose
    var questionLimit: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: Long? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: Long? = null

    @SerializedName("createdBy")
    @Expose
    var createdBy: Any? = null

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: Any? = null

    @SerializedName("courseIds")
    @Expose
    var courseIds: Any? = null

    @SerializedName("courseList")
    @Expose
    var courseList: Any? = null

    @SerializedName("webexUserIds")
    @Expose
    var webexUserIds: Any? = null

    @SerializedName("webexUsers")
    @Expose
    var webexUsers: Any? = null
}

class Course {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("courseName")
    @Expose
    var courseName: String? = null

    @SerializedName("parentId")
    @Expose
    var parentId: Any? = null

    @SerializedName("parentName")
    @Expose
    var parentName: Any? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("coachingCentre")
    @Expose
    var coachingCentre: CoachingCentre? = null

    @SerializedName("coachingCentreId")
    @Expose
    var coachingCentreId: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: Long? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: Long? = null

    @SerializedName("createdBy")
    @Expose
    var createdBy: Any? = null

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: Any? = null

    @SerializedName("type")
    @Expose
    var type: String? = null
}


