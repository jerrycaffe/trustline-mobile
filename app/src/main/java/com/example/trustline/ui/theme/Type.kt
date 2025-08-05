package com.example.trustline.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.trustline.R

// Set of Material typography styles to start with


val Marckscript = FontFamily(Font(R.font.marckscript_regular))
val MontserratBold = FontFamily(
    Font(R.font.montserrat_bold)
)

val MontserratSemiBold = FontFamily(
    Font(R.font.montserrat_semi_bold)
)

val MontserratMedium = FontFamily(
    Font(R.font.montserrat_medium)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontSize = 40.sp,
        fontFamily = Marckscript,
        fontWeight = FontWeight.W400,
        lineHeight = 50.sp,
    ),
    bodyLarge = TextStyle(
        fontSize = 14.sp,
        fontFamily = MontserratMedium,
        fontWeight = FontWeight.W500,
        lineHeight = 17.sp,
    ),

    titleMedium = TextStyle(
        fontSize = 18.sp,
        fontFamily = MontserratBold,
        lineHeight = 22.sp
    )
)
