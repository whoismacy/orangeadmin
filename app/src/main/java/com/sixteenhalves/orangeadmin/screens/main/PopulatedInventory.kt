package com.sixteenhalves.orangeadmin.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sixteenhalves.orangeadmin.R
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

data class InventoryProduct(
    val id: String,
    val name: String,
    val category: String,
    val stock: Int,
    val price: String,
    val isLowStock: Boolean = false,
)

object MockInventoryData {
    val items =
        listOf(
            InventoryProduct("1", "Navel Oranges", "Fresh Produce", 12, "$1.20", isLowStock = true),
            InventoryProduct("2", "Orange Juice (1L)", "Beverages", 342, "$4.50"),
            InventoryProduct("3", "Grapefruits", "Fresh Produce", 5, "$1.50", isLowStock = true),
            InventoryProduct("4", "Tangerines", "Fresh Produce", 2, "$0.80", isLowStock = true),
            InventoryProduct("5", "Apple - Fuji", "Fresh Produce", 150, "$1.00"),
            InventoryProduct("6", "Banana", "Fresh Produce", 85, "$0.50"),
            InventoryProduct("7", "Citrus Peeler", "Kitchenware", 42, "$5.00"),
            InventoryProduct("8", "Organic Lemonade", "Beverages", 120, "$3.50"),
        )
}

@Composable
fun PopulatedInventory(
    onNavigateAddNewItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    val products = MockInventoryData.items

    LazyColumn(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            AnimatedInventoryItem(index = 0) {
                InventoryHeader(onNavigateAddNewItem = onNavigateAddNewItem)
            }
        }

        item {
            AnimatedInventoryItem(index = 1) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    InventoryOverviewCard(
                        title = "Total Items",
                        value = "1,245",
                        subtitle = "In Stock",
                        modifier = Modifier.weight(1f),
                    )
                    InventoryOverviewCard(
                        title = "Low Stock",
                        value = "3",
                        subtitle = "Needs Attention",
                        isAlert = true,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        item {
            AnimatedInventoryItem(index = 2) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "All Products",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        itemsIndexed(products) { index, product ->
            // Shift the index by 3 so the staggered animation flows smoothly after the header items
            AnimatedInventoryItem(index = index + 3) {
                ProductCard(product = product)
            }
        }
    }
}

@Composable
private fun AnimatedInventoryItem(
    index: Int,
    content: @Composable () -> Unit,
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay((index * 80L).milliseconds) // Slightly faster stagger for lists
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter =
            fadeIn(animationSpec = tween(500, easing = FastOutSlowInEasing)) +
                slideInVertically(
                    initialOffsetY = { it / 4 },
                    animationSpec = tween(500, easing = FastOutSlowInEasing),
                ),
    ) {
        content()
    }
}

@Composable
private fun InventoryHeader(onNavigateAddNewItem: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Inventory",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Manage your products, pricing, and stock levels.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        
        androidx.compose.material3.IconButton(
            onClick = onNavigateAddNewItem,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                contentDescription = "Add New Item",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun InventoryOverviewCard(
    title: String,
    value: String,
    subtitle: String,
    isAlert: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val containerColor = if (isAlert) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceContainerLow
    val textColor = if (isAlert) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurface
    val labelColor =
        if (isAlert) {
            MaterialTheme.colorScheme.onErrorContainer.copy(
                alpha = 0.8f,
            )
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                color = textColor,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = textColor,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = labelColor,
            )
        }
    }
}

@Composable
private fun ProductCard(product: InventoryProduct) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(
                                if (product.isLowStock) {
                                    MaterialTheme
                                        .colorScheme.errorContainer
                                } else {
                                    MaterialTheme.colorScheme.surface
                                },
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_inventory_2_24),
                        contentDescription = null,
                        tint = if (product.isLowStock) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp),
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = product.category,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${product.stock}",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                    color = if (product.isLowStock) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = if (product.isLowStock) "Low Stock" else "In Stock",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = if (product.isLowStock) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
