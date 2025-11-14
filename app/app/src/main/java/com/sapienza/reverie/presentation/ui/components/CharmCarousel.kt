package com.sapienza.reverie.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapienza.reverie.R
import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.domain.repository.ApiClient

@OptIn(ExperimentalMaterial3Api::class) // 1. Opt-in to the experimental API
@Composable
fun CharmCarousel(
    modifier: Modifier = Modifier.fillMaxWidth(),
    charmList: List<CharmModel> = emptyList(),
    onCharmClick: (CharmModel) -> Unit = {}
) {

    val carouselState = rememberCarouselState{ charmList.size }


    HorizontalUncontainedCarousel(
        state = carouselState,
        modifier = modifier,
        itemSpacing = 12.dp,
        contentPadding = PaddingValues(horizontal = 4.dp),
        flingBehavior = CarouselDefaults.multiBrowseFlingBehavior(state = carouselState),
        itemWidth = 160.dp
    ) { index ->
        charmList.getOrNull(index)?.let { charm ->
            Charm(charmModel = charm, onClick = {onCharmClick(charm)}, width = 160)
        }

    }

}

@Preview(showBackground = true)
@Composable
fun CharmCarouselPreview() {

    val charmList = listOf(
        CharmModel.foo(), CharmModel.foo()

        )

    Scaffold() { innerpadding ->
        Column(
            modifier = Modifier.padding(innerpadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.padding(horizontal = 14.dp)) {
                CharmCarousel(charmList = charmList)
            }
        }

    }
}
