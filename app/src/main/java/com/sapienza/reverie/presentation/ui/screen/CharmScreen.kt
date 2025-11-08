package com.sapienza.reverie.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapienza.reverie.R
import com.sapienza.reverie.presentation.ui.components.ButtonType
import com.sapienza.reverie.presentation.ui.components.Charm
import com.sapienza.reverie.presentation.ui.components.CommentList
import com.sapienza.reverie.presentation.ui.components.CommentType
import com.sapienza.reverie.presentation.ui.components.NavBar
import com.sapienza.reverie.presentation.ui.components.ShareButton
import com.sapienza.reverie.presentation.ui.components.TopBar
import com.sapienza.reverie.ui.theme.ReverieTheme


@Composable
fun CharmScreen(modifier: Modifier = Modifier){

    val saints = listOf(R.drawable.saint_franca,R.drawable.saint_miriam)

    Scaffold(
        topBar = { TopBar(icon = Icons.Filled.SaveAs, title = "Charm Details") },
        bottomBar = { NavBar() },
        floatingActionButton = { },
        floatingActionButtonPosition = FabPosition.End

    ) { innerPadding ->

        Column(modifier =modifier.padding(innerPadding), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){

            Box(modifier = Modifier.padding(12.dp).weight(2f).aspectRatio(2f/3f), contentAlignment = Alignment.TopEnd){
                Charm(imageUrl = R.drawable.saint_franca, modifier = Modifier.padding(15.dp))
                ShareButton(type = ButtonType.SHARE, modifier = Modifier.size(45.dp), onClick = {
                    /*TODO animation for buttons*/
                })
            }
            HorizontalDivider(thickness = 2.dp, color = Color.LightGray, modifier = Modifier.padding(horizontal = 12.dp))
            CommentList(fooSaints = saints, commentType = CommentType.NORMAL)
        }

    }

}

@Preview
@Composable
fun CharmScreenPreview(){
    ReverieTheme {
        CharmScreen()
    }
}
