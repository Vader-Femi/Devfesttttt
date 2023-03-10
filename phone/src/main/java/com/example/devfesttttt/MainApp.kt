package com.example.devfesttttt

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1

@Composable
fun MainApp(
    image: Bitmap?,
    isCameraSupported: Boolean,
    onTakePhotoClick: () -> Unit,
    onSendPhotoClick: () -> Unit,
    onSendEmail: KSuspendFunction1<String, Unit>,
    onSendName: KSuspendFunction1<String, Unit>,
    onStartWearableActivityClick: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(modifier = Modifier.size(200.dp)) {
                if (image == null) {
                    Image(
                        painterResource(id = R.drawable.ic_content_picture),
                        contentDescription = stringResource(
                            id = R.string.photo_placeholder
                        ),
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        image.asImageBitmap(),
                        contentDescription = stringResource(
                            id = R.string.captured_photo
                        ),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Divider()
        }
        item {
            Button(
                onClick = onTakePhotoClick,
                enabled = isCameraSupported
            ) {
                Text(stringResource(id = R.string.take_photo))
            }
            Divider()
        }
        item {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(id = R.string.email)) },
                maxLines = 2,
                leadingIcon = {
                    Icon(Icons.Filled.Email, "Email Icon")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    imeAction = ImeAction.Next
                )
            )
            Divider()
        }
        item {
            Button(
                onClick = {
                    coroutineScope.launch {
                        onSendEmail(email)
                    }
                },
                enabled = email != ""
            ) {
                Text(stringResource(id = R.string.send_email))
            }
            Divider()
        }
        item {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(id = R.string.name)) },
                maxLines = 2,
                leadingIcon = {
                    Icon(Icons.Filled.Person, "Name Icon")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                )
            )
            Divider()
        }
        item {
            Button(
                onClick = {
                    coroutineScope.launch {
                        onSendName(name)
                    }
                },
                enabled = name != ""
            ) {
                Text(stringResource(id = R.string.send_name))
            }
            Divider()
        }
        item {
            Button(
                onClick = onSendPhotoClick,
                enabled = image != null
            ) {
                Text(stringResource(id = R.string.send_photo))
            }
            Divider()
        }
        item {
            Button(onClick = onStartWearableActivityClick) {
                Text(stringResource(id = R.string.start_wearable_activity))
            }
            Divider()
        }
    }
}
