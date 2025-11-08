package com.sapienza.reverie.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sapienza.reverie.R // Assuming your images are in res/drawable

@Composable
fun Charm(
    modifier: Modifier = Modifier,
    imageUrl: Int  // Using Int for drawable resource ID for now
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),


    ) {
        // The Box is used to contain the image and apply the aspect ratio
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f) // Here is the key: 3:2 aspect ratio
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = imageUrl),
                contentDescription = "Reverie Charm", // Provide a meaningful description
                modifier = Modifier.matchParentSize(), // Make the image fill the Box
                contentScale = ContentScale.Crop // Crop the image to fill the space without distortion

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharmPreview() {



        // You'll need to add a placeholder image to your res/drawable folder
        // For now, let's assume you have one named 'placeholder_image'
        // If not, the preview might fail.
    Box(modifier = Modifier.padding(vertical = 32.dp, horizontal = 32.dp)) {
        Charm(imageUrl = R.drawable.ic_launcher_background)
    }


}
