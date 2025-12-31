package com.example.opinia.ui.onboarding_authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opinia.data.repository.AuthRepository
import com.example.opinia.utils.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ForgotPasswordUiState (
    val email: String = "",
    val isLoading: Boolean = false
)

sealed class ForgotPasswordUiEvent {
    data class ForgotPasswordSuccess(val message: String): ForgotPasswordUiEvent()
    data class ForgotPasswordError(val message: String): ForgotPasswordUiEvent()
}

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val authRepository: AuthRepository, private val networkManager: NetworkManager) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ForgotPasswordUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _isResetButtonEnabled = MutableStateFlow(true)
    val isResetButtonEnabled = _isResetButtonEnabled.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun validateStudentEmail(email: String): Boolean {
        if (email.isBlank()) {
            return false
        }
        else if (!email.endsWith("@std.yeditepe.edu.tr")) {
            return false
        }
        return true
    }

    suspend fun resetPasswordUseCase(email: String): Result<Unit> {
        if (email.isBlank()) {
            return Result.failure(Exception("Please enter email"))
        }
        if (!networkManager.isInternetAvailable()) {
            return Result.failure(Exception("No internet connection"))
        }
        if (!validateStudentEmail(email)) {
            return Result.failure(Exception("Please enter a valid student email"))
        }
        return authRepository.resetPassword(email)
    }

    fun onResetPasswordClicked() {
        //spam engelleme
        if (!_isResetButtonEnabled.value) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            launch {
                _isResetButtonEnabled.value = false
                delay(15_000) //15 saniye bekle
                _isResetButtonEnabled.value = true
            }
            val result = resetPasswordUseCase(uiState.value.email)
            if (result.isSuccess) {
                _uiEvent.send(ForgotPasswordUiEvent.ForgotPasswordSuccess("Password reset email sent"))
            }
            else {
                _uiEvent.send(ForgotPasswordUiEvent.ForgotPasswordError(result.exceptionOrNull()?.message ?: "Unknown error"))
            }
            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

}