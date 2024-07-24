package com.example.gemipost.ui.auth.userinfo

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.R
import com.example.gemipost.ui.auth.util.AuthError
import com.example.gemipost.utils.LocalDateTimeUtil.now
import com.example.gemipost.utils.LocalDateTimeUtil.toLocalDateTime
import com.example.gemipost.utils.LocalDateTimeUtil.toMillis
import com.example.gemipost.utils.LocalDateTimeUtil.toYYYYMMDD
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

@SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserInformationContent(
        paddingValues: PaddingValues,
        state: UserInformationUiState,
        onNameChange: (String) -> Unit = {},
        onProfileImageClicked: () -> Unit = {},
        onPhoneNumberChange: (String) -> Unit = {},
        onDateOfBirthChange: (LocalDateTime) -> Unit = {},
        onBioChange: (String) -> Unit = {},
        onContinueClicked: () -> Unit = {},
    ) {
        var isDateDialogOpen by remember { mutableStateOf(false) }
        var pickedDate by remember { mutableStateOf(LocalDateTime.now()) }
        val formattedDate by remember {
            derivedStateOf {
                pickedDate.toYYYYMMDD()
            }
        }
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            modifier = Modifier.fillMaxSize(),
        ) {
            if (state.error is AuthError.ServerError) {
                scope.launch {
                    snackbarHostState.showSnackbar((state.error as AuthError.ServerError).message)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .widthIn(max = 600.dp)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.complete_your_profile),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally),
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val imageModifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                        .clip(CircleShape)
                    if (state.pfpImageByteArray.isEmpty()) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            modifier = imageModifier,
                            tint = MaterialTheme.colorScheme.outline
                        )
                    } else {
//                        val bitmap = state.pfpImageByteArray.toImageBitmap()
//                        Image(
//                            bitmap = bitmap,
//                            contentDescription = null,
//                            modifier = imageModifier,
//                            contentScale = ContentScale.Crop
//                        )
                    }
                    IconButton(
                        onClick = { onProfileImageClicked() },
                        modifier = Modifier
                            .offset(x = 38.dp, y = 38.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                            .size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Create,
                            contentDescription = null,
                        )
                    }
                }
                OutlinedTextField(
                    value = state.name,
                    onValueChange = onNameChange,
                    label = { Text(text = stringResource(R.string.name)) },
                    isError = state.error is AuthError.FirstNameError,
                    supportingText = {
                        if (state.error is AuthError.FirstNameError) {
                            Text(
                                text = (state.error as AuthError.FirstNameError).toString(),
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp
                            )
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
                OutlinedTextField(
                    value = state.phoneNumber,
                    onValueChange = onPhoneNumberChange,
                    label = { Text(text = stringResource(R.string.phone_number)) },
                    isError = state.error is AuthError.PhoneNumberError,
                    supportingText = {
                        if (state.error is AuthError.PhoneNumberError) {
                            Text(
                                text = (state.error as AuthError.PhoneNumberError).toString(),
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.PhoneAndroid,
                            contentDescription = null,
                        )
                    }
                )
                Box {
                    OutlinedTextField(
                        value = formattedDate,
                        onValueChange = {},
                        label = { Text(text = stringResource(R.string.date_of_birth)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .clickable {
                                isDateDialogOpen = true
                            },

                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.CalendarMonth,
                                contentDescription = null,
                            )
                        },
                        isError = state.error is AuthError.BirthDateError,
                        supportingText = {
                            if (state.error is AuthError.BirthDateError) {
                                Text(
                                    text = (state.error as AuthError.BirthDateError).toString(),
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 12.sp
                                )
                            }
                        },
                        maxLines = 1,
                        readOnly = true,
                        enabled = false
                    )
                }
                OutlinedTextField(
                    value = state.bio,
                    onValueChange = onBioChange,
                    label = { Text(text = stringResource(R.string.about)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = null,
                        )
                    },
                    maxLines = 3,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                )
                Button(
                    onClick = onContinueClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(52.dp)
                ) {
                    Text(
                        text = stringResource(R.string.complete_profile),
                        fontSize = 18.sp,
                    )
                }
                if (isDateDialogOpen) {
                    val datePickerState = rememberDatePickerState(
                        initialSelectedDateMillis = LocalDateTime.now().toMillis()
                    )
                    val confirmEnabled = remember {
                        derivedStateOf { datePickerState.selectedDateMillis != null }
                    }
                    DatePickerDialog(
                        onDismissRequest = {
                            isDateDialogOpen = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    isDateDialogOpen = false
                                    val date = datePickerState.selectedDateMillis?.toLocalDateTime()
                                        ?: LocalDateTime.now()
                                    pickedDate = date
                                    onDateOfBirthChange(date)
                                },
                                enabled = confirmEnabled.value
                            ) {
                                Text(stringResource(R.string.select))
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    isDateDialogOpen = false
                                }
                            ) {
                                Text(stringResource(R.string.cancel))
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
            }
        }
    }

