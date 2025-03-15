package com.cslori.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cslori.auth.domain.UserDataValidator
import com.cslori.auth.domain.repository.AuthRepository
import com.cslori.auth.presentation.R
import com.cslori.core.domain.util.DataError
import com.cslori.core.domain.util.Result
import com.cslori.presentation.ui.UiText
import com.cslori.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    userDataValidator: UserDataValidator
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private var _eventChannel = Channel<LoginEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        combine(
            snapshotFlow { state.email.text },
            snapshotFlow { state.password.text }) { email, password ->
            state = state.copy(
                canLogin = userDataValidator.isValidEmail(
                    email = email.toString().trim()
                ) && password.toString().isNotEmpty()
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }

            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(
                isLoggingIn = true
            )

            val result = authRepository.login(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        _eventChannel.send(
                            LoginEvent.Error(
                                UiText.StringResource(R.string.error_email_password_incorrect)
                            )
                        )
                    } else {
                        _eventChannel.send(LoginEvent.Error(result.error.asUiText()))
                    }
                }

                is Result.Success -> {
                    _eventChannel.send(LoginEvent.LoginSuccess)
                }
            }

            state = state.copy(
                isLoggingIn = false
            )
        }
    }
}