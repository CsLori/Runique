@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.cslori.run.presentation.run_overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cslori.core.presentation.designsystem.AnalyticsIcon
import com.cslori.core.presentation.designsystem.LogoIcon
import com.cslori.core.presentation.designsystem.LogoutIcon
import com.cslori.core.presentation.designsystem.RunIcon
import com.cslori.core.presentation.designsystem.RuniqueTheme
import com.cslori.core.presentation.designsystem.component.RuniqueFloatingActionButton
import com.cslori.core.presentation.designsystem.component.RuniqueScaffold
import com.cslori.core.presentation.designsystem.component.RuniqueToolbar
import com.cslori.core.presentation.designsystem.component.util.DropDownItem
import com.cslori.run.presentation.R
import com.cslori.run.presentation.run_overview.components.RunListItem
import com.cslori.run.presentation.run_overview.model.RunOverviewState
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverViewScreenRoot(
    onStartRunClick: () -> Unit,
    onLogoutClick: () -> Unit,
    viewModel: RunOverViewViewModel = koinViewModel()
) {
    RunOverViewScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is RunOverViewAction.OnStartClick -> onStartRunClick()
                is RunOverViewAction.OnLogoutClick -> onLogoutClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun RunOverViewScreen(
    state: RunOverviewState,
    onAction: (RunOverViewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )

    RuniqueScaffold(
        topAppBar = {
            RuniqueToolbar(
                showBackButton = false,
                title = stringResource(R.string.runique),
                scrollBehaviour = scrollBehavior,
                menuItems = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(R.string.analytics),
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(R.string.logout),
                    )
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(RunOverViewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverViewAction.OnLogoutClick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            )


        },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            RuniqueFloatingActionButton(
                icon = RunIcon,
                onClick = { onAction(RunOverViewAction.OnStartClick) }
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .padding(horizontal = 16.dp),
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = state.runs,
                    key = { it.id }
                ) {
                    RunListItem(
                        runUi = it,
                        onDeleteClick = { onAction(RunOverViewAction.DeleteRun(it)) },
                        modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun RunOverViewScreenRootPreview() {
    RuniqueTheme {
        RunOverViewScreen(
            state = RunOverviewState(),
            onAction = {}
        )
    }
}