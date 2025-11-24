package com.example.opinia.data.model

import com.google.firebase.firestore.DocumentId

data class CommentReview(
    @DocumentId
    val commentID: String = "",
    val courseID: String = "",
    val studentID: String = "",
    val rating: Int = 0,
    val comment: String = "",

    val timestamp: Long = System.currentTimeMillis() // yeni yazılan yorumu üstte göstermek için
)
