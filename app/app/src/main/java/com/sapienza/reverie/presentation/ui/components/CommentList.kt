package com.sapienza.reverie.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.domain.model.CharmWithUserModel
import com.sapienza.reverie.domain.model.UserCommentModel


enum class CommentType {
    IMAGE,
    NORMAL
}

@Composable
fun CommentList(
    modifier: Modifier = Modifier,
    commentType: CommentType = CommentType.NORMAL,
    commentsWithCharm: List<CharmWithUserModel>?,
    comments: List<UserCommentModel>?,
    onCharmClick : (CharmModel) -> Unit
) {


    LazyColumn {
        if (commentsWithCharm != null && commentType == CommentType.IMAGE) {
            items(commentsWithCharm) { comment ->
                CharmCommentWithCharm(
                    comment.userModel,
                    comment.commentModel.text,
                    charmModel = comment.charmModel,
                    onCharmClick = { onCharmClick(comment.charmModel) }
                )
            }
        } else if (commentType.equals(CommentType.NORMAL) && comments != null) {
            items(comments) { comment ->
                CharmComment(comment.user, comment.comment)
            }
        } else {
            item {
                Text(text = "No comments yet")
            }

        }

    }

}


