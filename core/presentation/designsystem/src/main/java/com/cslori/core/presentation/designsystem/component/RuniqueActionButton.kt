package com.cslori.core.presentation.designsystem.component

import android.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cslori.core.presentation.designsystem.RuniqueBlack
import com.cslori.core.presentation.designsystem.RuniqueGray
import com.cslori.core.presentation.designsystem.RuniqueTheme

@Composable
fun RuniqueActionButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick, enabled = enabled, colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = RuniqueGray,
            disabledContentColor = RuniqueBlack

        ), shape = RoundedCornerShape(100f),
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(
                        if (isLoading) 1f else 0f
                    ),
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = text, modifier = Modifier.alpha(
                    if (isLoading) 0f else 1f
                ), fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun RuniqueOutlinedActionButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick, enabled = enabled, colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,

            ), border = BorderStroke(
            width = 0.3.dp, color = MaterialTheme.colorScheme.onBackground
        ), shape = RoundedCornerShape(100f),
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(
                        if (isLoading) 1f else 0f
                    ),
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = text, modifier = Modifier.alpha(
                    if (isLoading) 0f else 1f
                ), fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
private fun RuniqueActionButtonEnabledPreview() {
    RuniqueTheme {
        RuniqueActionButton(text = "Sign Up", isLoading = false, onClick = {}, enabled = true)

    }
}

@Preview
@Composable
private fun RuniqueActionButtonDisabledPreview() {
    RuniqueTheme {
        RuniqueActionButton(text = "Sign Up", isLoading = false, onClick = {}, enabled = false)

    }
}

@Preview
@Composable
private fun RuniqueOutlinedButtonEnabledPreview() {
    RuniqueTheme {
        RuniqueOutlinedActionButton(
            text = "Sign Up",
            isLoading = false,
            onClick = {},
            enabled = true
        )
    }
}