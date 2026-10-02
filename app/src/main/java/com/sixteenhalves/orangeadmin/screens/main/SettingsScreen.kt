package com.sixteenhalves.orangeadmin.screens.main

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sixteenhalves.orangeadmin.R
import com.sixteenhalves.orangeadmin.components.shared.SharedTextField

@Composable
fun SettingsScreen(
    onNavigateTheme: () -> Unit,
    onNavigateBiometrics: () -> Unit,
    onNavigateNotifications: () -> Unit,
    onNavigateAppDetails: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    // In a real app, this data would come from the ViewModel
    var userName by remember { mutableStateOf("Admin User") }
    val userEmail = "admin@company.com"
    var userAvatarUri by remember { mutableStateOf<Uri?>(null) }
    val fallbackAvatarUrl = "https://i.pravatar.cc/150?img=11"
    
    var showProfileSheet by remember { mutableStateOf(false) }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 32.dp),
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 24.dp),
        )

        AvatarCard(
            name = userName,
            email = userEmail,
            imageUrl = fallbackAvatarUrl,
            imageUri = userAvatarUri,
            onClick = { showProfileSheet = true }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            Column {
                SettingsListItem(
                    iconResId = R.drawable.baseline_visibility_24,
                    title = "Theme",
                    subtitle = "System Default",
                    onClick = onNavigateTheme,
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                SettingsListItem(
                    iconResId = R.drawable.baseline_visibility_off_24,
                    title = "Biometrics",
                    subtitle = "Fingerprint enabled",
                    onClick = onNavigateBiometrics,
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                SettingsListItem(
                    iconResId = R.drawable.baseline_cloud_upload_24,
                    title = "Notifications",
                    subtitle = "On",
                    onClick = onNavigateNotifications,
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                SettingsListItem(
                    iconResId = R.drawable.baseline_space_dashboard_24,
                    title = "App Details",
                    subtitle = "Version 1.0.0",
                    onClick = onNavigateAppDetails,
                )
            }
        }
        
        Spacer(modifier = Modifier.height(80.dp))
    }

    if (showProfileSheet) {
        EditProfileBottomSheet(
            currentName = userName,
            currentImageUri = userAvatarUri,
            fallbackImageUrl = fallbackAvatarUrl,
            onDismiss = { showProfileSheet = false },
            onSave = { newName, newImageUri ->
                userName = newName
                userAvatarUri = newImageUri
                showProfileSheet = false
            }
        )
    }
}

@Composable
private fun AvatarCard(
    name: String,
    email: String,
    imageUrl: String,
    imageUri: Uri?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    model = imageUri ?: imageUrl,
                    contentDescription = "Profile Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                )
            }
            
            Icon(
                painter = painterResource(id = R.drawable.outline_settings_24), // Proxy edit icon
                contentDescription = "Edit Profile",
                tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun SettingsListItem(
    iconResId: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        
        Spacer(modifier = Modifier.width(20.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title, 
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold), 
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle, 
                style = MaterialTheme.typography.bodyMedium, 
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileBottomSheet(
    currentName: String,
    currentImageUri: Uri?,
    fallbackImageUrl: String,
    onDismiss: () -> Unit,
    onSave: (String, Uri?) -> Unit,
) {
    var name by remember { mutableStateOf(currentName) }
    var imageUri by remember { mutableStateOf(currentImageUri) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                imageUri = uri
            }
        }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit Profile",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            // Avatar Edit Section
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUri ?: fallbackImageUrl,
                    contentDescription = "Profile Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Overlay to indicate editability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                        contentDescription = "Change Photo",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TextButton(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text("Change Photo", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Name Input
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Full Name",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                SharedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeHolderText = "Enter your full name",
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { onSave(name, imageUri) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = name.isNotBlank()
            ) {
                Text("Save Changes", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
