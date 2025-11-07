package com.sapienza.reverie.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sapienza.reverie.R

@Composable
fun CharmComment(){

    ElevatedCard(
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(size = 12.dp)) {
        Column (Modifier.padding(12.dp)){
            //ROW 1
            Row (verticalAlignment = Alignment.CenterVertically) {
                UserPicture(imageUrl =R.drawable.saint_vale)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Saint Vale", fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
            }

            //ROW 2
            Row ( modifier = Modifier.padding(top = 8.dp)){
                Spacer(modifier = Modifier.width(10.dp))
                Text("Great charm! Lorem ipsum dolor sit amet, consectetur adipiscing elit...")
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun CharmCommentPreview(){
    Box(Modifier.padding(30.dp)){

    CharmComment()
    }
}

//