package com.example.opinia.data.model

import com.google.firebase.firestore.DocumentId

data class Student(
    @DocumentId
    val studentID: String = "", // Firebase authentication id'si ile aynı olacak
    val studentEmail: String = "",
    val studentName: String = "",
    val studentSurname: String = "",
    val studentYear: String = "",
    val facultyID: String = "",
    val departmentID: String = "",
    val studentProfileImageUrl: String = "",

    val enrolledCourseIds: List<String> = emptyList()
)