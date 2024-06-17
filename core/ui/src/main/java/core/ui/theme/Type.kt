package core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import core.ui.R

@Composable
fun getTypography() = Typography(
    titleLarge = TextStyle(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.6.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        color = colorResource(id = R.color.accent)
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.3.sp,
        color = colorResource(id = R.color.accent)
    )
)
