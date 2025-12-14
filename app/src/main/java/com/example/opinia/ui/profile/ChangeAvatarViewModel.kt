package com.example.opinia.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opinia.data.repository.AvatarProvider
import com.example.opinia.data.repository.StudentRepository
import com.example.opinia.utils.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChangeAvatarUiState(
    val selectedAvatarId: String = "",
    val isLoading: Boolean = false
)

sealed class ChangeAvatarUiEvent {
    data object AvatarChangeSuccess : ChangeAvatarUiEvent()
    data class AvatarChangeError(val message: String) : ChangeAvatarUiEvent()
}

@HiltViewModel
class ChangeAvatarViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val avatarProvider: AvatarProvider,
    private val networkManager: NetworkManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChangeAvatarUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ChangeAvatarUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val allAvatars = avatarProvider.avatars //buradan avatarlara erişebiliriz

    init {
        loadCurrentAvatar()
    }

    private fun loadCurrentAvatar() {
        viewModelScope.launch {
            val result = studentRepository.getStudentProfile()
            if (result.isSuccess) {
                result.getOrNull()?.let { student ->
                    _uiState.update { it.copy(selectedAvatarId = student.studentProfileAvatar) }
                }
            }
        }
    }

    fun selectAvatar(avatarId: String) {
        _uiState.update { it.copy(selectedAvatarId = avatarId) }
    }

    fun onOKclicked() {
        viewModelScope.launch {
            if (!networkManager.isInternetAvailable()) {
                _uiEvent.send(ChangeAvatarUiEvent.AvatarChangeError("No internet connection"))
                return@launch
            }
            _uiState.update { it.copy(isLoading = true) }
            val result = studentRepository.updateProfileAvatar(_uiState.value.selectedAvatarId)
            _uiState.update { it.copy(isLoading = false) }
            if (result.isSuccess) {
                _uiEvent.send(ChangeAvatarUiEvent.AvatarChangeSuccess)
            } else {
                _uiEvent.send(ChangeAvatarUiEvent.AvatarChangeError("Unkown error"))
            }
        }
    }
}