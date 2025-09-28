package ru.urfu.droidpractice1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.urfu.droidpractice1.content.MainActivityScreen

object LikeDislikeManager {
    var likeCount = 0
    var dislikeCount = 0
}

@Composable
fun LikeDislikeCounter() {
    val likeCounter = rememberSaveable { mutableStateOf(LikeDislikeManager.likeCount) }
    val dislikeCounter = rememberSaveable { mutableStateOf(LikeDislikeManager.dislikeCount) }

    Row (
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            IconButton(
                onClick = {
                    likeCounter.value++
                    LikeDislikeManager.likeCount = likeCounter.value
                    },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    //painter = painterResource(id = android.R.drawable.btn_star_big_on),
                    imageVector = Icons.Default.Check,
                    contentDescription = "Лайк",
                    modifier = Modifier.size(26.dp),
                    tint = Color(0xFF4CAF50)
                )
            }
            Text(
                text = "${likeCounter.value}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )
        }
        //VerticalDivider(modifier = Modifier.height(50.dp).padding(14.dp, 0.dp, 14.dp, 0.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ){
            IconButton(
                onClick = {
                    dislikeCounter.value++
                    LikeDislikeManager.dislikeCount = dislikeCounter.value
                    },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    //painter = painterResource(id = android.R.drawable.btn_star_big_off),
                    imageVector = Icons.Default.Close,
                    contentDescription = "Дизлайк",
                    modifier = Modifier.size(26.dp),
                    tint = Color(0xFFF44336)
                )
            }
            Text(
                text = "${dislikeCounter.value}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    LikeDislikeCounter()
}