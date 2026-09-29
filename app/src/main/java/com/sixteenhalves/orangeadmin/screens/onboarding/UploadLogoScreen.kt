package com.sixteenhalves.orangeadmin.screens.onboarding

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sixteenhalves.orangeadmin.R
import com.sixteenhalves.orangeadmin.domain.EventManager
import com.sixteenhalves.orangeadmin.utils.compressImage
import com.sixteenhalves.orangeadmin.viewmodels.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@Composable
fun UploadLogoScreen(
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit = {},
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedFile by remember { mutableStateOf<Uri?>(null) }
    var isCompressing by remember { mutableStateOf(false) }

    val pickFileLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
        ) { uri ->
            if (uri == null) {
                EventManager.triggerEvent(EventManager.AppEvent.ShowEvent("No file Selected"))
                return@rememberLauncherForActivityResult
            }

            selectedFile = uri
            coroutineScope.launch {
                isCompressing = true
                handleImageUpload(context, uri, authViewModel)
                isCompressing = false
            }
        }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        HeaderSection()

        LogoUploadCircle(
            imageUri = selectedFile,
            onClick = { pickFileLauncher.launch(arrayOf("image/jpeg")) },
        )

        ActionSection(
            hasImage = selectedFile != null,
            isProcessing = isCompressing,
            onChangeLogo = { pickFileLauncher.launch(arrayOf("image/jpeg")) },
            onNext = onNext,
        )
    }
}

private suspend fun handleImageUpload(
    context: Context,
    uri: Uri,
    authViewModel: AuthViewModel,
) {
    withContext(Dispatchers.IO) {
        try {
            val tempRawFile = File(context.cacheDir, "raw_${System.currentTimeMillis()}.jpg")

            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(tempRawFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            val compressedFile = compressImage(context, tempRawFile)
            authViewModel.updateImage(compressedFile)

            if (tempRawFile.exists()) {
                val deleted = tempRawFile.delete()
                if (!deleted) {
                    tempRawFile.deleteOnExit()
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()

            withContext(Dispatchers.Main) {
                EventManager.triggerEvent(EventManager.AppEvent.ShowEvent("Failed to Upload Image!"))
            }
        }
    }
}

@Composable
private fun HeaderSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = "Add a logo",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Upload a high-resolution logo to personalize your store and invoices.",
            style =
                MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun LogoUploadCircle(
    imageUri: Uri?,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .size(220.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .border(
                    border =
                        BorderStroke(
                            width = 2.dp,
                            color =
                                if (imageUri != null) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.outlineVariant
                                },
                        ),
                    shape = CircleShape,
                ).clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Selected Logo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        } else {
            PlaceholderContent()
        }
    }
}

@Composable
private fun PlaceholderContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(64.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_cloud_upload_24),
                contentDescription = "Upload Icon",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(32.dp),
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Tap to upload logo",
            style =
                MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "JPG (max 5MB)",
            style =
                MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
        )
    }
}

@Composable
private fun ActionSection(
    hasImage: Boolean,
    isProcessing: Boolean,
    onChangeLogo: () -> Unit,
    onNext: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (hasImage) {
            OutlinedButton(
                onClick = onChangeLogo,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = !isProcessing,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_photo_camera_24),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Change Logo")
                }
            }
        }

        Button(
            onClick = onNext,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = !isProcessing,
        ) {
            if (isProcessing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp,
                )
            } else {
                Text(
                    text = if (hasImage) "Continue" else "Skip for now",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}
