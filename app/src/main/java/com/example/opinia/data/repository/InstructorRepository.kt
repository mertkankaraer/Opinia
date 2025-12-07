package com.example.opinia.data.repository

import android.util.Log
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
            val instructor = document.toObject(Instructor::class.java)

            // işlem başarılı olursa bu log çalışacak
            Log.d("InstructorRepository", "Success, fetched instructor with id=$instructorId: $instructor")

            instructor
        } catch (e: Exception) {
            // hata olursa bu log çalışacak
            // not: hata durumunda 'instructor' verisi oluşmadığı için logdan o kısmı çıkardım, yoksa uygulama çökerdi.
            Log.e("InstructorRepository", "Failure, error fetching instructor with id=$instructorId", e)
            null
        }
    }
}