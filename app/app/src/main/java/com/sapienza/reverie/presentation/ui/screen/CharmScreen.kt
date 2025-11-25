package com.sapienza.reverie.presentation.ui.screen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.presentation.viewmodel.CharmViewModel
import com.sapienza.reverie.presentation.ui.components.Charm
import com.sapienza.reverie.presentation.ui.components.CommentList
import com.sapienza.reverie.presentation.ui.components.CommentType
import com.sapienza.reverie.presentation.ui.components.ExpandingActionButtons
import com.sapienza.reverie.presentation.ui.components.TopBar
import com.sapienza.reverie.ui.theme.ReverieTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import com.sapienza.reverie.domain.model.UserModel
import com.sapienza.reverie.presentation.ui.components.CommentPopUp
import com.sapienza.reverie.presentation.ui.components.NavCharmBar
import com.sapienza.reverie.presentation.viewmodel.SessionViewModel
import com.sapienza.reverie.ui.theme.GradientButton
import com.sapienza.reverie.ui.theme.MagentaLogo30
import com.sapienza.reverie.ui.theme.MagentaLogo50

val GradientButtonPrimary= listOf(

    Color(0xFFEAF9FF),  // Light Blue
    Color(0xFFEAF9FF),  // Light Blue

    Color(0xFFD9F6FF),  // Light Blue


)
@Composable
fun CharmScreen(
    modifier: Modifier = Modifier,
    charmModel: CharmModel = CharmModel.foo(),
    onCollectionClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onQRShareClick: (Long) -> Unit = {}
) {
    var showCommentPopUp by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val sessionViewModel: SessionViewModel = viewModel()

    var isExpanded by remember { mutableStateOf(false) }
    val charmViewModel: CharmViewModel = viewModel()

    // Launch loadCharms once when the screen is shown
    LaunchedEffect(Unit) {
        charmViewModel.getComments(charmModel.id)
        charmViewModel.getCharmCreator(charmModel.id)
    }

    val user by sessionViewModel.user.collectAsState()
    val comments by charmViewModel.comments.collectAsState()
    val creator by charmViewModel.charmCreator.collectAsState()


    Scaffold(
        topBar = { TopBar(title = charmModel.description) },
        bottomBar = {
            user?.let { currentUser ->

                NavCharmBar(
                    onCollectionClick = onCollectionClick,
                    onHomeClick = onHomeClick,
                    onCommentClick = { showCommentPopUp = true }
                )
            }
        },
        floatingActionButton = { },
        floatingActionButtonPosition = FabPosition.End

    ) { innerPadding ->

        Column(
            modifier = modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize(0.7f)
                    .aspectRatio(2f / 3f), contentAlignment = Alignment.TopEnd
            ) {
                Charm(modifier = Modifier.padding(15.dp), charmModel = charmModel)
                if (creator?.id == user?.id) {
                    ExpandingActionButtons(
                        isExpanded = isExpanded,
                        onExpandToggle = { isExpanded = !isExpanded },
                        onQRShareClick = { onQRShareClick(charmModel.id) },
                        onLinkShareClick = {
                            val url =
                                "http://localhost:6001/reverie/api/charm/download/${charmModel.id}"

                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, url) // The content to be shared
                                type = "text/plain"
                            }

                            val shareIntent = Intent.createChooser(sendIntent, "Share Charm Link")

                            context.startActivity(shareIntent)
                        }
                    )
                }
                Box(
                    modifier = modifier
                        .align(Alignment.BottomEnd)
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(12.dp)) // Clip the Box to a circle
                        .background(Brush.horizontalGradient(colors = GradientButtonPrimary))

                ){

                Button(
                    onClick = {},
                    //elevation = ButtonDefaults.elevatedButtonElevation(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onPrimary,

                        )

                ) {
                    val username = if (creator?.id == user?.id) {
                        "You"
                    } else {
                        creator?.username
                            ?: "Unknown" // Use creator's username or "Unknown" as a fallback
                    }
                    Text("Made by ${username}")
                }
                }


            }


            HorizontalDivider(
                thickness = 2.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            CommentList(
                comments = comments,
                commentType = CommentType.NORMAL,
                commentsWithCharm = null,
                onCharmClick = {}
            )
        }


    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CommentPopUp(
            visible = showCommentPopUp,
            onConfirm = { commentText ->
                // Hide the pop-up
                showCommentPopUp = false
                // Call your ViewModel to post the comment
                charmViewModel.postComment(
                    charmId = charmModel.id,
                    userId = user!!.id,
                    text = commentText
                )

            },
            onCancel = {
                // Just hide the pop-up
                showCommentPopUp = false
            }
        )

    }


}

@Preview
@Composable
fun CharmScreenPreview() {
    ReverieTheme {
        CharmScreen()
    }
}
