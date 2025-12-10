package com.example.opinia.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opinia.data.model.CommentReview
import com.example.opinia.data.model.Course
import com.example.opinia.data.model.Instructor
import com.example.opinia.data.repository.AvatarProvider
import com.example.opinia.data.repository.CommentReviewRepository
import com.example.opinia.data.repository.CourseRepository
import com.example.opinia.data.repository.InstructorRepository
import com.example.opinia.data.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Ekranda gösterilecek verilerin durumu
data class DashboardUiState(
    val isLoading: Boolean = true,
    val studentName: String = "",
    val studentSurname: String = "",
    val avatarResId: Int? = null,
    val enrolledCourses: List<Course> = emptyList(),
    // "Featured" (Mor Kart) için veriler:
    val featuredCourse: Course? = null,
    val featuredComment: CommentReview? = null,
    val featuredRatingAverage: Double = 0.0,
    val searchResultsCourses: List<Course> = emptyList(),
    val searchResultsInstructors: List<Instructor> = emptyList(),
    val isSearching: Boolean = false,
    val searchText: String = ""
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val courseRepository: CourseRepository,
    private val instructorRepository: InstructorRepository,
    private val commentReviewRepository: CommentReviewRepository,
    private val avatarProvider: AvatarProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // 1. Öğrenci Profilini Çek
            val studentResult = studentRepository.getStudentProfile()
            val student = studentResult.getOrNull()

            if (student != null) {
                val avatarId = avatarProvider.getAvatarResId(student.studentProfileAvatar)

                // 2. Kayıtlı Dersleri Çek
                val enrolledIdsResult = studentRepository.getEnrolledCoursesIds(student.studentId)
                val enrolledIds = enrolledIdsResult.getOrNull() ?: emptyList()

                val coursesResult = courseRepository.getCoursesByIds(enrolledIds)
                val courses = coursesResult.getOrNull() ?: emptyList()

                // 3. Featured Kart için Veri Hazırla
                var featuredCourse: Course? = null
                var featuredComment: CommentReview? = null
                var averageRating = 0.0

                if (courses.isNotEmpty()) {
                    featuredCourse = courses.first() // Listeden ilk dersi al
                    val commentsResult = commentReviewRepository.getCommentsByCourseId(featuredCourse.courseId)
                    val comments = commentsResult.getOrNull() ?: emptyList()

                    if (comments.isNotEmpty()) {
                        featuredComment = comments.first() // En güncel yorum
                        averageRating = comments.map { it.rating }.average()
                    }
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    studentName = student.studentName,
                    studentSurname = student.studentSurname,
                    avatarResId = avatarId,
                    enrolledCourses = courses,
                    featuredCourse = featuredCourse,
                    featuredComment = featuredComment,
                    featuredRatingAverage = averageRating
                )
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchText = query, isSearching = query.isNotEmpty())

        if (query.isNotEmpty()) {
            viewModelScope.launch {
                // Ders Arama
                val coursesResult = courseRepository.searchCourses(query)
                val courses = coursesResult.getOrNull() ?: emptyList()

                // Eğitmen Arama
                val instructorsResult = instructorRepository.searchInstructors(query)
                val instructors = instructorsResult.getOrNull() ?: emptyList()

                _uiState.value = _uiState.value.copy(
                    searchResultsCourses = courses,
                    searchResultsInstructors = instructors
                )
            }
        }
    }
}