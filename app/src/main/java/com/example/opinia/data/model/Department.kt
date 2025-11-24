package com.example.opinia.data.model

import com.google.firebase.firestore.DocumentId

data class Department(
    @DocumentId
    val departmentID: String = "",
    val departmentName: String = "",
    val facultyId: String = "",
)
