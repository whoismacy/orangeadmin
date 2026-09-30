package com.sixteenhalves.orangeadmin.components.shared

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

import kotlin.time.Duration.Companion.milliseconds

private val DefaultLoadingStatements =
    listOf(
        "Loading, please wait...",
        "Setting things up...",
        "Almost there...",
    )

@Composable
fun LoadingSpinner(
    modifier: Modifier = Modifier,
    message: String? = null,
    statements: List<String> = DefaultLoadingStatements,
) {
    var statementIndex by remember { mutableIntStateOf(0) }

    if (message == null && statements.isNotEmpty()) {
        LaunchedEffect(statements) {
            while (true) {
                delay(2500.milliseconds)
                statementIndex = (statementIndex + 1) % statements.size
            }
        }
    }

    val currentText = message ?: statements.getOrElse(statementIndex) { "Loading..." }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        // Full screen blur background
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .blur(16.dp)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.75f)),
        )

        // Simplified full-screen loading content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp),
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                tonalElevation = 4.dp,
            ) {
                Box(
                    modifier = Modifier.padding(20.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4.dp,
                        strokeCap = StrokeCap.Round,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedContent(
                targetState = currentText,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "loading_statement_transition",
            ) { text ->
                Text(
                    text = text,
                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                        ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
