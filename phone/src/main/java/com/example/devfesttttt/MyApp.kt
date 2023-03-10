package com.example.devfesttttt

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyApp(
    email: String?,
    name: String?,
    image: Bitmap?,
    isSignedIn: Boolean,
    onSignIn: () -> Unit,
    onSendToWear: () -> Unit,
    onSignOut: () -> Unit,
    onStartAppOnWatch: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isSignedIn) {
            item {
                image?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .wrapContentSize(align = Alignment.Center),
                    )
                }
            }
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = "Welcome, $name",
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = "$email",
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }

            item {
                Button(onClick = onSendToWear) {
                    Text(text = "Send To Wear")
                }
            }

            item {
                Button(onClick = onSignOut) {
                    Text(text = "Sign Out")
                }
            }

        } else {
            item {
                Button(onClick = onSignIn) {
                    Text(text = "Sign in with Google")
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            Button(onClick = onStartAppOnWatch) {
                Text(text = "Start the app on Watch")
            }
        }
    }
}