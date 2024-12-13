package com.taoc.intersim_test.data.response

data class ChatResponse(
    val uuid: String? = null,

    val id_job: Int? = null,

    val generated_at: String? = null,

    val title: String? = null,

    val company: String? = null,

    val questions: List<QuestionsItem>? = null
)

data class QuestionsItem(
    val id: String? = null,

    val question: String? = null,

    val type: String? = null,

    val statement: String? = null
)
