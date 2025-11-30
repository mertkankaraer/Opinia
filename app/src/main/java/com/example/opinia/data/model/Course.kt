package com.example.opinia.data.model

import com.google.firebase.firestore.DocumentId

data class Course(
    @DocumentId
    val courseID: String = "",
    val courseCode: String = "",
    val courseName: String = "",
    val courseDescription: String = "",
    val facultyID: String = "",
    val departmentID: String = "",
    val akts: Int = 0,
    val credits: Int = 0,

    val instructorIds: List<String> = emptyList()
)
