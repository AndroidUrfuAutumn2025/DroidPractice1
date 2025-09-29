package ru.urfu.droidpractice1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun Article1Composable(
    title: String,
    text: String,
    imageUrl: String,
    likes: Int,
    dislikes: Int,
    userVoteState: UserVoteState,
    onLikeClicked: () -> Unit,
    onDislikeClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onNavigateToNextArticleClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "Изображение к статье",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Основной текст:",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Дополнительная информация курсивом.",
                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onLikeClicked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (userVoteState == UserVoteState.LIKED) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (userVoteState == UserVoteState.LIKED) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text("👍 $likes")
                }

                Button(
                    onClick = onDislikeClicked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (userVoteState == UserVoteState.DISLIKED) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (userVoteState == UserVoteState.DISLIKED) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text("👎 $dislikes")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onShareClicked,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Поделиться статьей")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onNavigateToNextArticleClicked,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("К следующей статье")
            }
        }
    }
}

@Preview(showBackground = true, name = "Article1Composable Preview - Liked")
@Composable
fun Article1ComposablePreviewLiked() {
    MaterialTheme {
        Article1Composable(
            title = "Статья понравилась",
            text = "Этот текст для превью, когда лайк нажат.",
            imageUrl = "https://via.placeholder.com/800x600.png?text=Liked+Article",
            likes = 16,
            dislikes = 3,
            userVoteState = UserVoteState.LIKED,
            onLikeClicked = {},
            onDislikeClicked = {},
            onShareClicked = {},
            onNavigateToNextArticleClicked = {}
        )
    }
}

@Preview(showBackground = true, name = "Article1Composable Preview - No Vote")
@Composable
fun Article1ComposablePreviewNoVote() {
    MaterialTheme {
        Article1Composable(
            title = "Статья без оценки",
            text = "Этот текст для превью, когда нет оценки.",
            imageUrl = "https://via.placeholder.com/800x600.png?text=Neutral+Article",
            likes = 15,
            dislikes = 3,
            userVoteState = UserVoteState.NONE,
            onLikeClicked = {},
            onDislikeClicked = {},
            onShareClicked = {},
            onNavigateToNextArticleClicked = {}
        )
    }
}



