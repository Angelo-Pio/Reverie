package com.sapienza.reverie.ui.theme

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = CyanLogo30,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = CyanLogo30,
    onPrimary = Color.Black,
    secondary = Color(0xFFFAFAFA),
    tertiary = Color(0xFFFAFAFA),
    primaryContainer = Color(0xFFFAFAFA),
    onPrimaryContainer = Color(0xFFFAFAFA),
    background = Color(0xFFfafdff)







    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ReverieTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // CHANGE THIS TO false
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme // Now this will be used by default
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun ColorSchemePreview() {
    val colorScheme = MaterialTheme.colorScheme
    val colors = listOf(
        "primary" to colorScheme.primary,
        "onPrimary" to colorScheme.onPrimary,
        "primaryContainer" to colorScheme.primaryContainer,
        "onPrimaryContainer" to colorScheme.onPrimaryContainer,
        "secondary" to colorScheme.secondary,
        "onSecondary" to colorScheme.onSecondary,
        "secondaryContainer" to colorScheme.secondaryContainer,
        "onSecondaryContainer" to colorScheme.onSecondaryContainer,
        "tertiary" to colorScheme.tertiary,
        "onTertiary" to colorScheme.onTertiary,
        "tertiaryContainer" to colorScheme.tertiaryContainer,
        "onTertiaryContainer" to colorScheme.onTertiaryContainer,
        "background" to colorScheme.background,
        "onBackground" to colorScheme.onBackground,
        "surface" to colorScheme.surface,
        "onSurface" to colorScheme.onSurface,
        "surfaceVariant" to colorScheme.surfaceVariant,
        "onSurfaceVariant" to colorScheme.onSurfaceVariant,
        "error" to colorScheme.error,
        "onError" to colorScheme.onError,
        "outline" to colorScheme.outline
    )

    Column {
        colors.forEach { (name, color) ->
            ColorChip(name = name, color = color)
        }
    }
}

@Composable
private fun ColorChip(name: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = name, modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(
                    CircleShape)
                .background(color)
        )
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
fun TypographyPreview() {
    val typography = Typography // Use your defined Typography
    val textStyles = listOf(
        "headlineLarge" to typography.headlineLarge,
        "headlineMedium" to typography.headlineMedium,
        "headlineSmall" to typography.headlineSmall,
        "titleLarge" to typography.titleLarge,
        "titleMedium" to typography.titleMedium,
        "titleSmall" to typography.titleSmall,
        "bodyLarge" to typography.bodyLarge,
        "bodyMedium" to typography.bodyMedium,
        "bodySmall" to typography.bodySmall,
        "labelLarge" to typography.labelLarge,
        "labelMedium" to typography.labelMedium,
        "labelSmall" to typography.labelSmall
    )
    Column {
        textStyles.forEach { (name, style) ->
            Text(text = name, style = style)
        }
    }
}

@Composable
fun ComponentPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedButton(onClick = {}) {
            Text("Elevated Button")
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Text(
                "Card on Surface",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
@Composable
fun ThemePreview() {
    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text("Color Scheme", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            ColorSchemePreview()
        }
        item {
            Text("Typography", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            TypographyPreview()
        }
        item {
            Text("Components", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            ComponentPreview()
        }
    }
}
@Preview(name = "Light Theme", showBackground = true, widthDp = 360)
@Composable
fun LightThemePreview() {
    ReverieTheme(darkTheme = false, dynamicColor = false) {
        ThemePreview()
    }
}