package com.sapienza.reverie.presentation.ui.components

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sapienza.reverie.R
import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.properties.ApiProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Charm(
    modifier: Modifier = Modifier, // Using Int for drawable resource ID for now
    onClick: () -> Unit = {},
    charmModel: CharmModel,
    width : Int? = null
) {
    val charmShape = RoundedCornerShape(6.dp)
    ElevatedCard(
        modifier = modifier,
        shape = charmShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)


        ) {

        var boxModifier : Modifier = Modifier
            .aspectRatio(2f / 3f)
            .clip(charmShape)

        if (width != null){
            boxModifier = boxModifier.width(width.dp)
        }else
        {
            boxModifier = boxModifier.fillMaxWidth()
        }

        Box(
            modifier = boxModifier
        ) {

            val imageUrl = charmModel.pictureUrl?.let { picture ->
                ApiProperties.API_IMAGES_BASE_PATH + picture
            }

            val imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .placeholder(ColorDrawable(android.graphics.Color.TRANSPARENT)) // Use a transparent placeholder
                .error(R.drawable.saint_rick) // Your error drawable
                .crossfade(true) // Optional: for a smooth transition
                .build()
            AsyncImage(
                model = imageRequest, // provide empty string if null
                contentDescription = charmModel.description ?: "Charm image",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,


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
        //Charm(imageUrl = R.drawable.ic_launcher_background)
    }


}
