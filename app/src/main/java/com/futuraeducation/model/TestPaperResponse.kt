package com.futuraeducation.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TestPaperResponse(
    @SerialName("quesionList")
    val quesionList: List<Quesion>,
    @SerialName("sectionList")
    val sectionList: List<Section>
)

@Entity(tableName = "question_list")
@Serializable
data class Quesion(
    @SerialName("answer")
    var answer: String?,
    @SerialName("answeredMark")
    var answeredMark: Int,
    @PrimaryKey
    @SerialName("id")
    var id: String,
    @SerialName("optionA")
    var optionA: String?,
    @SerialName("optionB")
    var optionB: String?,
    @SerialName("optionC")
    var optionC: String?,
    @SerialName("optionD")
    var optionD: String?,
    @SerialName("position")
    var position: Int,
    @SerialName("isAnswered")
    var isAnswered: Boolean = false,
    @SerialName("questionContent")
    var questionContent: String?,
    @SerialName("questionType")
    var questionType: String?,
    @SerialName("unAnsweredMark")
    var unAnsweredMark: Int,
    @SerialName("wrongMark")
    var wrongMark: Int,
    @SerialName("timeSpent")
    var timeSpent: Long = 0,
    @SerialName("testPaperId")
    var testPaperId: String
)

@Entity(tableName = "completed_test")
@Serializable
class CompletedTest {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int = 0

    @SerialName("testPaperId")
    @ColumnInfo(name = "testPaperId")
    var testPaperId: String?= null

    @SerialName("attempt")
    @ColumnInfo(name = "attempt")
    var attempt: String? = null

    @SerialName("studentId")
    @ColumnInfo(name = "studentId")
    var studentId: String? = null

    @SerialName("testDurationTime")
    @ColumnInfo(name = "testDurationTime")
    var testDurationTime: Int = 0

    @SerialName("questionAnswerList")
    @ColumnInfo(name = "questionAnswerList")
    var questionAnswerList: String? = null

    @SerialName("status")
    @ColumnInfo(name = "status")
    var status: Int = 0

}


@Serializable
data class Section(
    @SerialName("sectionName")
    val sectionName: String,
    @SerialName("sectionQuesionList")
    val sectionQuesionList: List<SectionQuesion>
)

@Serializable
data class SectionQuesion(
    @SerialName("answer")
    val answer: String,
    @SerialName("answeredMark")
    val answeredMark: Int,
    @SerialName("id")
    val id: String,
    @SerialName("optionA")
    val optionA: String,
    @SerialName("optionB")
    val optionB: String,
    @SerialName("optionC")
    val optionC: String,
    @SerialName("optionD")
    val optionD: String,
    @SerialName("position")
    val position: Int,
    @SerialName("questionContent")
    val questionContent: String,
    @SerialName("questionType")
    val questionType: String,
    @SerialName("unAnsweredMark")
    val unAnsweredMark: Int,
    @SerialName("wrongMark")
    val wrongMark: Int
)

@Serializable
data class TestPaperForStudentResponse(
    @SerialName("data")
    val `data`: Data1
)

@Serializable
data class Data1(
    @SerialName("attemptedCount")
    val attemptedCount: Int,
    @SerialName("sectionList")
    val sectionList: List<Section1>,
    @SerialName("testPaper")
    val testPaper: TestPaper
)

@Serializable
data class Section1(
    @SerialName("questionCount")
    val questionCount: Int,
    @SerialName("sectionName")
    val sectionName: String
)

@Serializable
data class TestPaper(
    @SerialName("attempts")
    val attempts: Int,
    @SerialName("chapter")
    val chapter: String,
    @SerialName("chapterId")
    val chapterId: String,
    @SerialName("completionMessage")
    val completionMessage: String,
    @SerialName("correctMark")
    val correctMark: Int,
    @SerialName("createdAt")
    val createdAt: Long,
    @SerialName("createdBy")
    val createdBy: String,
    @SerialName("duration")
    val duration: Int,
    @SerialName("id")
    val id: String,
    @SerialName("instructions")
    val instructions: String,
    @SerialName("isHideAnsInResult")
    val isHideAnsInResult: Boolean,
    @SerialName("isJumbling")
    val isJumbling: Boolean,
    @SerialName("isPauseAllow")
    val isPauseAllow: Boolean,
    @SerialName("isSolutionRequired")
    val isSolutionRequired: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("questionCount")
    val questionCount: Int,
    @SerialName("status")
    val status: String,
    @SerialName("testCode")
    val testCode: String,
    @SerialName("testType")
    val testType: String,
    @SerialName("timeLeft")
    val timeLeft: Long,
    @SerialName("unasweredMark")
    val unasweredMark: Int,
    @SerialName("updatedAt")
    val updatedAt: Long,
    @SerialName("updatedBy")
    val updatedBy: Long,
    @SerialName("wrongMark")
    val wrongMark: Int
)

@Serializable
data class LeaderboardItem(
    @SerialName("name")
    val name: String,
    @SerialName("average")
    val average: String
)