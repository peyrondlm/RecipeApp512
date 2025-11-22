package org.example.project.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import recipeapp512.composeapp.generated.resources.Res
import recipeapp512.composeapp.generated.resources.StackSansHeadline_Bold
import recipeapp512.composeapp.generated.resources.StackSansHeadline_ExtraLight
import recipeapp512.composeapp.generated.resources.StackSansHeadline_Light
import recipeapp512.composeapp.generated.resources.StackSansHeadline_Medium
import recipeapp512.composeapp.generated.resources.StackSansHeadline_Regular
import recipeapp512.composeapp.generated.resources.StackSansHeadline_SemiBold

@Composable
fun stackSansFontFamily() = FontFamily(
    Font(Res.font.StackSansHeadline_ExtraLight, weight = FontWeight.ExtraLight),
    Font(Res.font.StackSansHeadline_Light, weight = FontWeight.Light),
    Font(Res.font.StackSansHeadline_Medium, weight = FontWeight.Medium),
    Font(Res.font.StackSansHeadline_Regular, weight = FontWeight.Normal),
    Font(Res.font.StackSansHeadline_SemiBold, weight = FontWeight.SemiBold),
    Font(Res.font.StackSansHeadline_Bold, weight = FontWeight.Bold)
)

@Composable
fun stackSansTypography() = Typography().run{
    val fontFamily = stackSansFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}
