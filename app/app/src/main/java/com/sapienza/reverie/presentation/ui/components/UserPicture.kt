package com.sapienza.reverie.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sapienza.reverie.R
import com.sapienza.reverie.properties.ApiProperties

@Composable
fun UserPicture(profilePicture: String?, modifier: Modifier = Modifier.size(56.dp)) {

    val buttonSize = 56.dp
    ElevatedButton(
        onClick = {},
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,

            ),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 5.dp),
        border = BorderStroke(width = 0.1.dp, color = Color.Black),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier


    ) {

        var imageUrl = ""
        if (!profilePicture.isNullOrBlank()) {
            if (profilePicture.startsWith("google_url:")) {
                imageUrl = profilePicture.removePrefix("google_url:")
            } else {
                imageUrl = ApiProperties.API_IMAGES_BASE_PATH + profilePicture
            }
        }
        Log.e("UserPicture : ${profilePicture}", "Google URL: $imageUrl")
        AsyncImage(
            model = imageUrl,
            contentDescription = "User profile picture",
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            error = painterResource(id = R.drawable.saint_rick)
        )
    }

}

@Composable
@Preview(showBackground = true)
fun UserPicturePreview() {
    UserPicture(profilePicture = ApiProperties.API_FOO_IMAGE_PATH)
}

//