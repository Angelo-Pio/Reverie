package com.sapienza.reverie.presentation.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


enum class CommentType{
    IMAGE,
    NORMAL
}

@Composable
fun CommentList(fooSaints: List<Int>, modifier: Modifier = Modifier, commentType : CommentType = CommentType.NORMAL){

    LazyColumn {
        items(fooSaints) { saint ->
            if(commentType.equals(CommentType.IMAGE)){
            CharmCommentWithCharm(saint,"Saint $saint", charmImage = saint)

            }else{
                CharmComment(userImage = saint, username = "Saint $saint")
            }
        }
    }

}