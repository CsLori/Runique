package com.cslori.core.presentation.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cslori.core.presentation.designsystem.RuniqueTheme

@Composable
fun RuniqueScaffold(
    withGradient: Boolean = true,
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topAppBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.Center,
    ) { innerPadding ->
        if (withGradient) {
            GradientBackground {
                content(innerPadding)
            }

        } else {
            content(innerPadding)

        }
    }
}

@Preview
@Composable
private fun RuniqueScaffoldPreview() {
    RuniqueTheme {
        RuniqueScaffold(
            topAppBar = {},
            floatingActionButton = {},
            content = {}
        )
    }
}