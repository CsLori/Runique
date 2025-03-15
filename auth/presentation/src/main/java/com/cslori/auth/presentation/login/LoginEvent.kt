package com.cslori.auth.presentation.login

import com.cslori.presentation.ui.UiText

sealed interface LoginEvent {
    data class Error(val error: UiText) : LoginEvent
    data object LoginSuccess : LoginEvent
}