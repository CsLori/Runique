package com.cslori.auth.presentation.login

import com.cslori.presentation.ui.UiText

sealed interface LoginEvents {
    data class Error(val error: UiText) : LoginEvents
    data object LoginSuccess : LoginEvents
}