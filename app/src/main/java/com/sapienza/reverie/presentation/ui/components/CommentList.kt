package com.sapienza.reverie.presentation.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun CommentList(fooSaints: List<Int>, modifier: Modifier = Modifier){

    LazyColumn {
        items(fooSaints) { saint ->
            CharmCommentWithCharm(saint,"Saint $saint", charmImage = saint)
        }
    }

}