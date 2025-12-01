package com.example.opinia.data.repository

import android.util.Log
import com.example.opinia.data.model.CommentReview
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CommentReviewRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    private val collectionName = "comments_reviews"
    private val TAG = "CommentReviewRepository"

    //yorum ve puan oluşturur ve ekler
    suspend fun createCommentReview(commentReview: CommentReview): Result<Unit> {
        return try {
            val docRef = if (commentReview.commentId.isEmpty()) {
                firestore.collection(collectionName).document()
            } else {
                firestore.collection(collectionName).document(commentReview.commentId)
            }

            val finalComment = commentReview.copy(commentId = docRef.id, timestamp = System.currentTimeMillis())

            docRef.set(finalComment).await()
            Log.d(TAG, "Comment review created successfully")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error creating comment review", e)
            Result.failure(e)
        }
    }

    //derslerin yorumlarını çeker
    suspend fun getCommentsByCourseId(courseId: String): Result<List<CommentReview>> {
        return try {
            val snapshot = firestore.collection(collectionName).whereEqualTo("courseId", courseId).get().await()
            val comments = snapshot.toObjects(CommentReview::class.java)
            val sortedComments = comments.sortedByDescending { it.timestamp }
            Log.d(TAG, "Comments retrieved and sorted successfully")
            Result.success(sortedComments)
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving comments", e)
            Result.failure(e)
        }
    }

    //öğrencinin yaptığı yorumları çeker
    suspend fun getCommentsByStudentId(studentId: String): Result<List<CommentReview>> {
        return try {
            val snapshot = firestore.collection(collectionName).whereEqualTo("studentId", studentId).get().await()
            val comments = snapshot.toObjects(CommentReview::class.java)
            val sortedComments = comments.sortedByDescending { it.timestamp }
            Log.d(TAG, "Comments retrieved and sorted successfully")
            Result.success(sortedComments)
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving comments", e)
            Result.failure(e)
        }
    }

    //(gerekli olursa) yorumu siler
    suspend fun deleteComment(commentId: String): Result<Unit> {
        return try {
            firestore.collection(collectionName).document(commentId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}