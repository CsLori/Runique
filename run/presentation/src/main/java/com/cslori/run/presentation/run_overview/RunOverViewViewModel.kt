package com.cslori.run.presentation.run_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cslori.core.domain.run.RunRepository
import com.cslori.core.domain.run.SyncRunScheduler
import com.cslori.run.presentation.run_overview.mapper.toRunUi
import com.cslori.run.presentation.run_overview.model.RunOverviewState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class RunOverViewViewModel(
    private val runRepository: RunRepository,
    private val syncRunScheduler: SyncRunScheduler
) : ViewModel() {

    var state by mutableStateOf(RunOverviewState())
        private set

    init {
        viewModelScope.launch {
            syncRunScheduler.scheduleSync(
                type = SyncRunScheduler.SyncType.FetchRuns(interval = 30.minutes)
            )
        }
        runRepository.getRuns().onEach { runs ->
            val runsUi = runs.map { it.toRunUi() }
            state = state.copy(runs = runsUi)
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            runRepository.syncPendingRuns()
            runRepository.fetchRuns()
        }
    }

    fun onAction(action: RunOverViewAction) {
        when (action) {
            RunOverViewAction.OnLogoutClick -> Unit
            RunOverViewAction.OnStartClick -> Unit
            is RunOverViewAction.DeleteRun -> {
                viewModelScope.launch {
                    runRepository.deleteRun(action.runUi.id)
                }
            }

            else -> Unit
        }
    }
}