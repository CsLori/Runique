package com.cslori.auth.presentation.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cslori.auth.presentation.R
import com.cslori.core.presentation.designsystem.LogoIcon
import com.cslori.core.presentation.designsystem.RuniqueTheme
import com.cslori.core.presentation.designsystem.component.GradientBackground
import com.cslori.core.presentation.designsystem.component.RuniqueActionButton
import com.cslori.core.presentation.designsystem.component.RuniqueOutlinedActionButton

@Composable
fun IntroScreenRoot(
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    IntroScreen(onAction = { action ->
        when (action) {
            IntroAction.OnSignUpClick -> onSignUpClick()
            IntroAction.OnSignInClick -> onSignInClick()
        }
    })
}

@Composable
private fun IntroScreen(onAction: (IntroAction) -> Unit) {

    GradientBackground {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), contentAlignment = Alignment.Center
        ) {
            RuniqueLogoVertical()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 48.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome_to_runique),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.track_your_runs),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(32.dp))
            RuniqueOutlinedActionButton(
                text = stringResource(R.string.sign_in),
                modifier = Modifier.fillMaxWidth(),
                isLoading = false,
                onClick = { onAction(IntroAction.OnSignInClick) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            RuniqueActionButton(
                text = stringResource(R.string.sign_up),
                modifier = Modifier.fillMaxWidth(),
                isLoading = false,
                onClick = { onAction(IntroAction.OnSignUpClick) }
            )
        }
    }
}

@Composable
private fun RuniqueLogoVertical(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = LogoIcon,
            contentDescription = "Logo",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.runique),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
private fun IntroScreenPreview() {
    RuniqueTheme {
        IntroScreen(onAction = {})
    }
}