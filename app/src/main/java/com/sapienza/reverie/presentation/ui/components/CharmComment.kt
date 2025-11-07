package com.sapienza.reverie.presentation.ui.components

import android.media.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
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
fun CharmComment(userImage: Int, username : String){

    ElevatedCard(
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(size = 12.dp)) {
        Column (Modifier.padding(12.dp)){
            //ROW 1
            Row (verticalAlignment = Alignment.CenterVertically) {
                UserPicture(imageUrl =userImage)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = username, fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
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

    val users = listOf(R.drawable.saint_rick, R.drawable.saint_vale, R.drawable.saint_fede, R.drawable.saint_franca, R.drawable.saint_miriam) // Added more items to show scrolling



    // 3. Use LazyColumn
    LazyColumn(
        // Add some padding around the whole list
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        // Add vertical spacing between items
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 4. Use the items extension function
        items(users) { user ->
            CharmComment(userImage = user, username = user.toString())
        }
    }



}

//