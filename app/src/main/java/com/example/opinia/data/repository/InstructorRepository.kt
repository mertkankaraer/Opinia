package com.example.opinia.data.repository

import com.example.opinia.data.model.Instructor
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class InstructorRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun getInstructorDetails(instructorId: String): Instructor? {
        return try {
            val document = firestore.collection("instructors").document(instructorId).get().await()
            document.toObject(Instructor::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
