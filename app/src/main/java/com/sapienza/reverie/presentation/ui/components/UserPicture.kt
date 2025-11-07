package com.sapienza.reverie.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapienza.reverie.R

@Composable
fun UserPicture(imageUrl: Int) {

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
            modifier = Modifier.size(buttonSize)


        ) {
            Image(
                painter = painterResource(id = imageUrl),
                contentDescription = "Reverie Charm", // Provide a meaningful description
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter

            )
        }

}

@Composable
@Preview(showBackground = true)
fun UserPicturePreview(){
    UserPicture(imageUrl = R.drawable.saint_rick)
}

//