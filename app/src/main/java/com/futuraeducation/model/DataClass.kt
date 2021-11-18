package com.futuraeducation.model

enum class FileType {
    PDF, IMAGE
}

enum class QuestionType {
    ATTEMPT, NOT_ATTEMPT, MARK_FOR_REVIEW, NOT_VISITED
}

data class UpcomingLiveItem(
    var subject: String? = null,
    var imageID: Int = 0
)

data class CompletedLiveItem(
    var subject: String? = null,
    var date: String? = null,
    var lesson: String? = null,
    var color: Int = 0
)

data class SubTopicItem(
    var subject: String? = null,
)

data class StudyItem(
    var subject: String? = null,
    var lesson: String? = null,
    var count: String? = null,
    var imageID: Int = 0,
    var progress: Int = 0,
    var color: Int = 0,
)

data class ScheduledTestItem(
    var testName: String? = null,
    var date: String? = null,
    var mark: String? = null,
    var duration: String? = null,
    var color: Int = 0,
)

data class FileNameItem(
    var fileName: String? = null,
    var fileType: FileType? = null,
)

data class ClarifiedDoubtItem(
    var title: String? = null,
    var subTitle: String? = null,
    var date: String? = null,
    var color: Int = 0,
)

data class PracticeSubjectItem(
    var subject: String? = null,
    var progressValue: Double = 0.0,
    var practiceTopicItem: ArrayList<PracticeTopicItem>,
)

data class PracticeTopicItem(
    var topic: String? = null,
    var isSelected: Boolean = false,
)

data class QuestionNumberItem(
    var questionNumber: Int = 0,
    var questionType: QuestionType
)

data class PracticeSubjects(
    var subjectPractice: String? = null,
    var subjectPracticeMarks: String? = null
)

data class QuestionItem(
    var question: String? = null,
    var chooseList: ArrayList<AnswerChooseItem>
)

data class AnswerChooseItem(
    var answer: String? = null,
    var isSelected: Boolean = false,
)