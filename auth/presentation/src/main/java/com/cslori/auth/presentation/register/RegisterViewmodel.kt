package com.cslori.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewmodel : ViewModel() {
    var state by mutableStateOf(RegisterState())
    private set

    fun onAction(action: RegisterAction) {

    }
}