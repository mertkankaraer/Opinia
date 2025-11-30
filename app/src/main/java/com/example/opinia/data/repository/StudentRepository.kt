package com.example.opinia.data.repository

import android.util.Log
import com.example.opinia.data.model.Student
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StudentRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    private val collectionName = "students"
    private val TAG = "StudentRepository"

    suspend fun createOrUpdateStudent(student: Student): Result<Unit> {
        return try {
            firestore.collection(collectionName).document(student.studentId).set(student).await()
            Log.d(TAG, "Student created or updated successfully")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error creating or updating student", e)
            Result.failure(e)
        }
    }

    suspend fun getStudentById(studentId: String): Result<Student?> {
        return try {
            val documentSnapshot = firestore.collection(collectionName).document(studentId).get().await()
            if (documentSnapshot.exists()) {
                val student = documentSnapshot.toObject(Student::class.java)
                Log.d(TAG, "Student retrieved successfully")
                Result.success(student)
            } else {
                Log.d(TAG, "Student not found")
                Result.success(null)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving student", e)
            Result.failure(e)
        }
    }

    suspend fun updateProfileAvatar(studentId: String, avatarKey: String): Result<Unit> {
        return try {
            firestore.collection(collectionName).document(studentId).update("studentProfileAvatar", avatarKey).await()
            Log.d(TAG, "Profile avatar updated successfully")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating profile avatar", e)
            Result.failure(e)
        }
    }

    suspend fun enrollStudentToCourse(studentId: String, courseId: String): Result<Unit> {
        return try {
            firestore.collection(collectionName).document(studentId).update("enrolledCourseIds", FieldValue.arrayUnion(courseId)).await()
            Log.d(TAG, "Student enrolled to course successfully")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error enrolling student to course", e)
            Result.failure(e)
        }
    }

    suspend fun dropStudentFromCourse(studentId: String, courseId: String): Result<Unit> {
        return try {
            firestore.collection(collectionName).document(studentId).update("enrolledCourseIds", FieldValue.arrayRemove(courseId)).await()
            Log.d(TAG, "Student dropped from course successfully")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error dropping student from course", e)
            Result.failure(e)
        }
    }

    suspend fun saveCommentReview(studentId: String, commentReviewId: String): Result<Unit> {
        return try {
            firestore.collection(collectionName).document(studentId).update("savedCommentReviewIds", FieldValue.arrayUnion(commentReviewId)).await()
            Log.d(TAG, "Comment review saved successfully")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error saving comment review", e)
            Result.failure(e)
        }
    }

    suspend fun unsaveCommentReview(studentId: String, commentReviewId: String): Result<Unit> {
        return try {
            firestore.collection(collectionName).document(studentId).update("savedCommentReviewIds", FieldValue.arrayRemove(commentReviewId)).await()
            Log.d(TAG, "Comment review unsaved successfully")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error saving comment review", e)
            Result.failure(e)
        }
    }

}