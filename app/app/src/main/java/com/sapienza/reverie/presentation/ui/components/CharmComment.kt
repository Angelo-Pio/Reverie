package com.sapienza.reverie.presentation.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.domain.model.CommentModel
import com.sapienza.reverie.domain.model.UserModel
import com.sapienza.reverie.presentation.viewmodel.SessionViewModel

@Composable
fun CharmComment(user: UserModel, commentModel: CommentModel) {

    val sessionViewModel: SessionViewModel = viewModel()
    val LoggedUser by sessionViewModel.user.collectAsState()
    var username = user.username
    LoggedUser?.let { u ->

        if (user.id == u.id) {
            username = "You"
        }

    }

    ElevatedCard(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(size = 12.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            //ROW 1

            Row(verticalAlignment = Alignment.CenterVertically) {
                UserPicture(profilePicture = user.profilePictureUrl, modifier = Modifier.size(40.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = username, fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
            }

            //ROW 2
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(commentModel.text)
            }
        }
    }
}

@Composable
fun CharmCommentWithCharm(
    userModel: UserModel,
    commentContent: String,
    charmModel: CharmModel,
    onCharmClick: (CharmModel) -> Unit
) {
    val sessionViewModel: SessionViewModel = viewModel()

    val loggedUser by sessionViewModel.user.collectAsState()
    var username = userModel.username
    loggedUser?.let { u ->
        if (userModel.id == u.id) {
            username = "You"
        }
    }
    ElevatedCard(
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(size = 6.dp)

    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            //ROW 1
            Column(modifier = Modifier.weight(1f)) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    UserPicture(
                        profilePicture = loggedUser?.profilePictureUrl,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = username, fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
                }

                //ROW 2
                Box(modifier = Modifier.padding(top = 8.dp)) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(commentContent)
                }

            }

            Charm(
                modifier = Modifier
                    .width(50.dp)
                    .aspectRatio(2f / 3f)
                    .padding(6.dp),
                charmModel = charmModel,
                onClick = { onCharmClick(charmModel) }
            )

        }
    }
}


@Composable
@Preview(showBackground = true)
fun CharmCommentPreview() {

    val users = listOf(
        UserModel.foo(),
        UserModel.foo(),
        UserModel.foo(),
        UserModel.foo(),
        UserModel.foo(),
        UserModel.foo(),
        UserModel.foo(),
    ) // Added more items to show scrolling


    // 3. Use LazyColumn
    LazyColumn(
        // Add some padding around the whole list
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        // Add vertical spacing between items
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 4. Use the items extension function
        items(users) { user ->
            CharmCommentWithCharm(
                userModel = user,
                commentContent = CommentModel.foo().text,
                charmModel = CharmModel.foo(),
                onCharmClick = {})
        }
    }


}

//