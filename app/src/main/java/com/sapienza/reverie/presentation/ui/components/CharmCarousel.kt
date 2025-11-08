package com.sapienza.reverie.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sapienza.reverie.R

@OptIn(ExperimentalMaterial3Api::class) // 1. Opt-in to the experimental API
@Composable
fun CharmCarousel(modifier: Modifier =  Modifier.fillMaxWidth()) {
    // 2. Create a list of items to display.
    //    Here we use placeholder image IDs. Replace them with your actual drawable resources.
    val charmImageIds = listOf(
        R.drawable.saint_rick,
        R.drawable.ic_launcher_background, // Example placeholder
        R.drawable.saint_rick,
        R.drawable.ic_launcher_background,
        R.drawable.saint_rick
    )

    // 3. Remember the state of the carousel.
    val carouselState = rememberCarouselState {
        charmImageIds.size // The total number of items
    }

    HorizontalUncontainedCarousel(
        state = carouselState,
        modifier = modifier,
        itemSpacing = 6.dp,
        flingBehavior = CarouselDefaults.multiBrowseFlingBehavior(state = carouselState),
        itemWidth = 160.dp
    ) { index ->
        // 5. This is the content for each item.
        //    Here we place our 'Charm' composable.
        val imageId = charmImageIds[index]
        Charm(imageUrl = imageId)
    }
}

@Preview(showBackground = true)
@Composable
fun CharmCarouselPreview() {
    Scaffold() { innerpadding ->
        Column(
            modifier = Modifier.padding(innerpadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.padding(vertical = 32.dp)) {
                CharmCarousel()
            }
        }

    }
}
