package com.example.opinia.data.repository

import android.util.Log
import com.example.opinia.data.model.Instructor
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class InstructorRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    private val collectionName = "instructors"
    private val TAG = "InstructorRepository"

    suspend fun getInstructorById(instructorId: String): Result<Instructor?> {
        return try {
            val documentSnapshot = firestore.collection(collectionName).document(instructorId).get().await()
            if (documentSnapshot.exists()) {
                val instructor = documentSnapshot.toObject(Instructor::class.java)
                Log.d(TAG, "Instructor retrieved successfully")
                Result.success(instructor)
            } else {
                Log.d(TAG, "Instructor not found")
                Result.success(null)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving instructor", e)
            Result.failure(e)
        }
    }

    suspend fun getAllInstructors(): Result<List<Instructor>> {
        return try {
            val snapshot = firestore.collection(collectionName).get().await()
            val instructors = snapshot.toObjects(Instructor::class.java)
            val sortedInstructor = instructors.sortedBy { it.instructorName }
            Log.d(TAG, "All instructors retrieved")
            Result.success(sortedInstructor)
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving all instructors", e)
            Result.failure(e)
        }
    }

    //course ta bulunan id listesi için kullanılır
    suspend fun getInstructorsByIds(instructorIds: List<String>): Result<List<Instructor>> {
        if (instructorIds.isEmpty()) {
            return Result.success(emptyList())
        }
        return try {
            val snapshot = firestore.collection(collectionName).whereIn(FieldPath.documentId(), instructorIds).get().await() //maks 30 id
            val instructors = snapshot.toObjects(Instructor::class.java)
            val sortedInstructor = instructors.sortedBy { it.instructorName }
            Log.d(TAG, "Instructors retrieved by IDs")
            Result.success(sortedInstructor)
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving instructors by IDs", e)
            Result.failure(e)
        }
    }

    suspend fun searchInstructors(query: String): Result<List<Instructor>> {
        return try {
            val snapshot = firestore.collection(collectionName).startAt(query).endAt(query + "\uf8ff").get().await()
            val instructors = snapshot.toObjects(Instructor::class.java)
            val sortedInstructor = instructors.sortedBy { it.instructorName }
            Log.d(TAG, "Instructors searched successfully")
            Result.success(sortedInstructor)
        } catch (e: Exception) {
            Log.e(TAG, "Error searching instructors", e)
            Result.failure(e)
        }
    }

}
