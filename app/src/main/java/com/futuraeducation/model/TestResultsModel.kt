package com.futuraeducation.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "ResultReview")
@Parcelize
data class TestResultsModel(
    @PrimaryKey
    @ColumnInfo(name = "sectionsData")
    @SerializedName("sectionsData")
    var sectionsData: List<SectionsDatum?>,

    @ColumnInfo(name = "totalTestAttempted")
    @SerializedName("totalTestAttempted")
    var totalTestAttempted: Int? = null,

    @ColumnInfo(name = "totalCorrectMarks")
    @SerializedName("totalCorrectMarks")
    var totalCorrectMarks: Int? = null,

    @ColumnInfo(name = "totalCorrectAnsweredQuestion")
    @SerializedName("totalCorrectAnsweredQuestion")
    var totalCorrectAnsweredQuestion: Int? = null,

    @ColumnInfo(name = "totalWrongMarks")
    @SerializedName("totalWrongMarks")
    var totalWrongMarks: Int? = null,

    @ColumnInfo(name = "totalUnattemptedMarks")
    @SerializedName("totalUnattemptedMarks")
    var totalUnattemptedMarks: Int? = null,

    @ColumnInfo(name = "totalMarks")
    @SerializedName("totalMarks")
    var totalMarks: Int? = null,

    @ColumnInfo(name = "totalAttemptedQuestions")
    @SerializedName("totalAttemptedQuestions")
    var totalAttemptedQuestions: Int? = null,

    @ColumnInfo(name = "totalWrongAttemptedQuestions")
    @SerializedName("totalWrongAttemptedQuestions")
    var totalWrongAttemptedQuestions: Int? = null,

    @ColumnInfo(name = "totalUnAttemptedQuestons")
    @SerializedName("totalUnAttemptedQuestons")
    var totalUnAttemptedQuestons: Int? = null,

    @ColumnInfo(name = "totalObtainedMarks")
    @SerializedName("totalObtainedMarks")
    var totalObtainedMarks: Int? = null,

    @ColumnInfo(name = "totalQuestions")
    @SerializedName("totalQuestions")
    var totalQuestions: Int? = null,

    @ColumnInfo(name = "accuracy")
    @SerializedName("accuracy")
    var accuracy: String? = null,

    @ColumnInfo(name = "currentRank")
    @SerializedName("currentRank")
    var currentRank: Int? = null,

    @ColumnInfo(name = "totalRank")
    @SerializedName("totalRank")
    var totalRank: Int? = null,

    @ColumnInfo(name = "listTopRankers")
    @SerializedName("listTopRankers")
    var listTopRankers: List<ListTopRanker?>? = null,

    @ColumnInfo(name = "pausedAt")
    @SerializedName("pausedAt")
    var pausedAt: String? = null,

    @ColumnInfo(name = "totalConsumeTime")
    @SerializedName("totalConsumeTime")
    var totalConsumeTime: Int? = null,

    var testPaperId: String? = null,

    @ColumnInfo(name = "testConsumeTimePercentage")
    @SerializedName("testConsumeTimePercentage")
    var testConsumeTimePercentage: String? = null,

    @ColumnInfo(name = "totalTimeTakenByTopper")
    @SerializedName("totalTimeTakenByTopper")
    var totalTimeTakenByTopper: Int? = null,

    @ColumnInfo(name = "avgTimePerQuesByTopper")
    @SerializedName("avgTimePerQuesByTopper")
    var avgTimePerQuesByTopper: Int? = null

) : Parcelable

@Parcelize
data class SectionsDatum(
    @SerializedName("sectionName")
    var sectionName: String,

    @SerializedName("sectionQuestion")
    var sectionQuestion: List<SectionQuestion?>? = null,
) : Parcelable

@Parcelize
data class SectionQuestion(
    @SerializedName("id")
    var id: String,

    @SerializedName("position")
    var position: Int? = null,

    @SerializedName("question")
    var question: String? = null,

    @SerializedName("optionA")
    var optionA: String? = null,

    @SerializedName("optionB")
    var optionB: String? = null,

    @SerializedName("optionC")
    var optionC: String? = null,

    @SerializedName("optionD")
    var optionD: String? = null,

    @SerializedName("correctMarks")
    var correctMarks: Int? = null,

    @SerializedName("unAnsweredMarks")
    var unAnsweredMarks: Int? = null,

    @SerializedName("wrongMarks")
    var wrongMarks: Int? = null,

    @SerializedName("difficultyLevel")
    var difficultyLevel: String? = null,

    @SerializedName("correctAnswer")
    var correctAnswer: String? = null,

    @SerializedName("submittedAnswered")
    var submittedAnswered: String? = null,

    @SerializedName("explanation")
    var explanation: String? = null,

    @SerializedName("timeSpent")
    var timeSpent: String? = null,

    @SerializedName("timeSpentByTopper")
    var timeSpentByTopper: String? = null
) : Parcelable

@Parcelize
data class ListTopRanker(
    @SerializedName("studentName")
    var studentName: String,

    @SerializedName("rank")
    var rank: Int? = null,

    @SerializedName("obtainedMarks")
    var obtainedMarks: Int? = null,

    @SerializedName("totalMarks")
    var totalMarks: Int? = null,
) :Parcelable


class SectionsDatumConverter {

    @TypeConverter
    fun listToJson(value: List<SectionsDatum>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<SectionsDatum>::class.java).toList()
}

class SectionQuestionConverter {

    @TypeConverter
    fun listToJson(value: List<SectionQuestion>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<SectionQuestion>::class.java).toList()
}


class ListTopRankerConverter {

    @TypeConverter
    fun listToJson(value: List<ListTopRanker>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<ListTopRanker>::class.java).toList()
}

