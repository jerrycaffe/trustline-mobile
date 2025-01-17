package com.example.trustline

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TrustlineTitle(@ColorRes textColor: Int = R.color.primary){
    Text(
        fontSize = 40.sp,
        color = colorResource(id = textColor),
        fontFamily = marckScriptFont,
        fontWeight = FontWeight.W400,
        lineHeight = 50.sp,
        text = "TrustLine"
    )
}