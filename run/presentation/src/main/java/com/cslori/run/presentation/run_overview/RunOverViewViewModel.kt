package com.cslori.run.presentation.run_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RunOverViewViewModel: ViewModel() {

    var state by mutableStateOf(RunOverViewState(""))
        private set

    fun onAction(action: RunOverViewAction) {

    }
}