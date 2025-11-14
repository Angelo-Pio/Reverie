package com.sapienza.reverie.presentation.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.sapienza.reverie.R

fun ReverieFontFamily(weight: String): FontFamily {



    var fontFamily : FontFamily = FontFamily(Font(R.font.comfortaa, weight = FontWeight.Normal))


    when(weight){
        "Bold" ->    fontFamily = FontFamily(
            Font(R.font.comfortaa_bold, weight = FontWeight.Bold))
        "Normal" ->     fontFamily = FontFamily(Font(R.font.comfortaa, weight = FontWeight.Normal))
        "SemiBold" ->     fontFamily = FontFamily(Font(R.font.comfortaa_semibold, weight = FontWeight.SemiBold))

    }

    return fontFamily


}
