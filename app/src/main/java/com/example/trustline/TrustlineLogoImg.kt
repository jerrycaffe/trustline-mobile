package com.example.trustline

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource

@Composable
fun TrustLineLogoImg(@ColorRes color: Int = R.color.primary){
    Image(
        painter = painterResource(id = R.drawable.trustline_logo),
        colorFilter = ColorFilter.tint(colorResource(id = color)),
        contentDescription = "Trustline logo"
    )
}