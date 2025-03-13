package com.cslori.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cslori.auth.domain.UserDataValidator
import com.cslori.auth.presentation.R
import com.cslori.core.presentation.designsystem.CheckIcon
import com.cslori.core.presentation.designsystem.CrossIcon
import com.cslori.core.presentation.designsystem.EmailIcon
import com.cslori.core.presentation.designsystem.Poppins
import com.cslori.core.presentation.designsystem.RuniqueDarkRed
import com.cslori.core.presentation.designsystem.RuniqueGray
import com.cslori.core.presentation.designsystem.RuniqueGreen
import com.cslori.core.presentation.designsystem.RuniqueTheme
import com.cslori.core.presentation.designsystem.RuniqueWhite
import com.cslori.core.presentation.designsystem.component.GradientBackground
import com.cslori.core.presentation.designsystem.component.RuniqueActionButton
import com.cslori.core.presentation.designsystem.component.RuniquePasswordTextField
import com.cslori.core.presentation.designsystem.component.RuniqueTextField
import com.cslori.presentation.ui.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewmodel = koinViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvent(flow = viewModel.events) { event ->
        when (event) {
            is RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    R.string.registration_successful,
                    Toast.LENGTH_LONG
                ).show()
                onSuccessfulRegistration()
            }
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    RegisterScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {

    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.headlineMedium,
                color = RuniqueWhite
            )
//            val annotatedString = buildAnnotatedString {
//                withStyle(
//                    style = SpanStyle(
//                        fontFamily = Poppins,
//                        color = RuniqueGray
//                    )
//                ) {
//                    append(stringResource(R.string.already_have_an_account) + " ")
//                }
//                pushStringAnnotation(
//                    tag = "clickable_text",
//                    annotation = stringResource(R.string.login)
//                )
//                withStyle(
//                    style = SpanStyle(
//                        fontWeight = FontWeight.SemiBold,
//                        color = MaterialTheme.colorScheme.primary,
//                        fontFamily = Poppins,
//                    )
//                ) {
//                    append(stringResource(R.string.login))
//                }
//            }

            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    append(stringResource(R.string.already_have_an_account) + " ")
                }
                withLink(
                    link = LinkAnnotation
                        .Clickable(
                            tag = stringResource(R.string.login),
                            styles = TextLinkStyles(
                                style = SpanStyle(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = Poppins
                                )
                            ),
                            linkInteractionListener = {
                                onAction(RegisterAction.OnRegisterClick)
                            }
                        )
                ) {
                    append(stringResource(R.string.login))
                }
            }

            BasicText(annotatedString)
//            ClickableText(
//                text = annotatedString,
//                onClick = { offset ->
//                    annotatedString.getStringAnnotations(
//                        tag = "clickable_text",
//                        start = offset,
//                        end = offset
//                    ).firstOrNull()?.let {
//                        onAction(RegisterAction.OnLoginClick)
//                    }
//                })

            Spacer(Modifier.height(48.dp))
            RuniqueTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) CheckIcon else null,
                hint = stringResource(R.string.example_email),
                title = stringResource(R.string.email),
                additionalInfo = stringResource(R.string.must_be_a_valid_email),
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(16.dp))
            RuniquePasswordTextField(
                state = state.password,
                hint = stringResource(R.string.password),
                title = stringResource(R.string.password),
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnPasswordVisibilityClick)
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(16.dp))
            PasswordRequirement(
                text = stringResource(
                    R.string.at_least_x_characters,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ),
                isValid = state.passwordValidationState.hasMinLength,
            )
            Spacer(Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    R.string.at_least_one_number,
                ),
                isValid = state.passwordValidationState.hasNumber,
            )
            Spacer(Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    R.string.contains_lowercase_character,
                ),
                isValid = state.passwordValidationState.hasLowerCaseCharacter,
            )
            Spacer(Modifier.height(4.dp))
            PasswordRequirement(
                text = stringResource(
                    R.string.contains_uppercase_character,
                ),
                isValid = state.passwordValidationState.hasUpperCaseCharacter,
            )
            Spacer(Modifier.height(32.dp))
            RuniqueActionButton(
                text = stringResource(R.string.register),
                isLoading = state.isRegistering,
                enabled = state.canRegister,
                onClick = { onAction(RegisterAction.OnRegisterClick) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PasswordRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) CheckIcon else CrossIcon,
            contentDescription = null,
            tint = if (isValid) RuniqueGreen else RuniqueDarkRed
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    RuniqueTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}