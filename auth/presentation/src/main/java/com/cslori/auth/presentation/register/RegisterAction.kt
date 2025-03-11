package com.cslori.auth.presentation.register

sealed interface RegisterAction {
    data object OnPasswordVisibilityClick: RegisterAction
    data object OnLoginClick: RegisterAction
    data object OnRegisterClick: RegisterAction
}