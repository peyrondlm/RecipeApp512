package org.example.project.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.example.project.ui.BackgroundColorDark
import org.example.project.ui.BackgroundColorLight
import org.example.project.ui.OutlineDark
import org.example.project.ui.OutlineLight
import org.example.project.ui.PrimaryColorDark
import org.example.project.ui.PrimaryColorLight
import org.example.project.ui.SecondaryColorDark
import org.example.project.ui.SecondaryColorLight
import org.example.project.ui.SurfaceColorDark
import org.example.project.ui.SurfaceColorLight
import org.example.project.ui.TextPrimaryDark
import org.example.project.ui.TextPrimaryLight
import org.example.project.ui.TextSecondaryDark
import org.example.project.ui.TextSecondaryLight

private val LightColors = lightColorScheme(
    primary = PrimaryColorLight,
    secondary = SecondaryColorLight,
    background = BackgroundColorLight,
    surface = SurfaceColorLight,
    onPrimary = Color.White,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = Color(0xFFFFE8C7),
    onSurfaceVariant = TextSecondaryLight,
    outline = OutlineLight
)

private val DarkColors = darkColorScheme(
    primary = PrimaryColorDark,
    secondary = SecondaryColorDark,
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    onPrimary = Color.Black,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = Color(0xFF2A2621),
    onSurfaceVariant = TextSecondaryDark,
    outline = OutlineDark
)

@Composable
fun RecipeTheme(
    content : @Composable () -> Unit
) {
    val colors = if(isSystemInDarkTheme()) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        content = content,
        typography = stackSansTypography()
    )
}