package com.sixteenhalves.orangeadmin.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sixteenhalves.orangeadmin.R

data class InventoryItem(
    val id: String,
    val name: String,
    val quantity: Int,
    val isLowStock: Boolean = false,
)

@Composable
fun BranchDetailScreen(
    branchId: String,
    onAddDevice: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    // Fetch branch using the mock repository
    val branch = MockBranchData.branches.find { it.id == branchId }
    val devicesCount = branch?.devicesCount ?: 0

    // Mock Inventory Data
    val fullInventory =
        listOf(
            InventoryItem("1", "Navel Oranges", 12, isLowStock = true),
            InventoryItem("2", "Grapefruits", 5, isLowStock = true),
            InventoryItem("3", "Tangerines", 2, isLowStock = true),
            InventoryItem("4", "Orange Juice (1L)", 342),
            InventoryItem("5", "Apple - Fuji", 150),
            InventoryItem("6", "Banana", 85),
            InventoryItem("7", "Citrus Peeler", 4),
        )

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 32.dp),
    ) {
        if (branch == null) {
            Text(
                text = "Branch not found",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colorScheme.error,
            )
            return
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = branch.name,
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = branch.location,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            StatusIndicator(isActive = branch.isActive)
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Metrics Section
        SectionHeader("Sales Overview")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MetricCard(
                title = "Today's Sales",
                value = branch.todaySales,
                modifier = Modifier.weight(1f),
            )
            MetricCard(
                title = "Total Devices",
                value = devicesCount.toString(),
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Inventory Insights Section
        SectionHeader("Inventory Insights")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            InsightCard(
                title = "Total Stock",
                value = "1,245",
                subtitle = "Units available",
                modifier = Modifier.weight(1f),
            )
            InsightCard(
                title = "Low Stock",
                value = "3",
                subtitle = "Items depleting",
                isAlert = true,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            InsightCard(
                title = "Top Seller",
                value = "Orange Juice",
                subtitle = "342 units sold",
                modifier = Modifier.weight(1f),
            )
            InsightCard(
                title = "Least Sold",
                value = "Citrus Peeler",
                subtitle = "4 units sold",
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Full Inventory Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SectionHeader("Inventory Management", paddingBottom = 0.dp)
            IconButton(
                onClick = { /* Add Item */ },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                    contentDescription = "Add Item",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        ) {
            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
                fullInventory.forEachIndexed { index, item ->
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f).padding(end = 16.dp)) {
                            Text(
                                text = item.name, 
                                fontWeight = FontWeight.ExtraBold, 
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            val textColor = if (item.isLowStock) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                            val fontWeight = if (item.isLowStock) FontWeight.Bold else FontWeight.Normal
                            Text(
                                text = "${item.quantity} units remaining",
                                color = textColor,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = fontWeight),
                            )
                        }
                        Button(
                            onClick = { /* Restock Action */ },
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            modifier = Modifier.height(40.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (item.isLowStock) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = if (item.isLowStock) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        ) {
                            Text("Restock", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                    if (index < fullInventory.size - 1) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Devices Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SectionHeader("POS Devices", paddingBottom = 0.dp)
            if (devicesCount > 0) {
                IconButton(
                    onClick = onAddDevice,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                        contentDescription = "Add Device",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (devicesCount == 0) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_storefront_24),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "No POS Devices",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "You haven't added any POS devices to this branch yet.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = onAddDevice,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text("Add POS Device", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
            ) {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
                    repeat(devicesCount) { index ->
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column {
                                Text(
                                    text = "Device ${index + 1}", 
                                    fontWeight = FontWeight.ExtraBold, 
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Serial: XYZ-${1000 + index}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            Text(
                                text = "Active",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            )
                        }
                        if (index < devicesCount - 1) {
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun SectionHeader(title: String, paddingBottom: Dp = 16.dp) {
    Text(
        text = title, 
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(bottom = paddingBottom)
    )
}

@Composable
private fun StatusIndicator(isActive: Boolean) {
    val color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
    val text = if (isActive) "Active" else "Inactive"
    
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
fun InsightCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    isAlert: Boolean = false,
) {
    val containerColor = if (isAlert) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceContainerLow
    val textColor = if (isAlert) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurface
    val labelColor = if (isAlert) MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = labelColor,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                color = textColor,
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = labelColor,
                )
            }
        }
    }
}
