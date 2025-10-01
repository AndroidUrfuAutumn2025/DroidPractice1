@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen(
    onNavigateToSecondArticle: () -> Unit,
    isSecondArticleRead: Boolean,
    likeCount: Int = 0,
    dislikeCount: Int = 0,
    isLiked: Boolean = false,
    isDisliked: Boolean = false,
    onLikeClick: () -> Unit = {},
    onDislikeClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    DroidPractice1Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.first_article_title)
                        )
                    },
                    actions = {
                        IconButton(onClick = onShareClick) {
                            Icon(Icons.Default.Share, contentDescription = "Share")
                        }
                    }
                )
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.first_article_header1),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = stringResource(id = R.string.first_article_date),
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp
                )

                AsyncImage(
                    model = "https://img.championat.com/s/1350x900/news/big/k/r/kajrat-proigral-dva-matcha-v-lige-chempionov-s-obschim-schyotom-1-9_175925771349487904.jpg",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onLikeClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isLiked) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = if (isLiked) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Icon(Icons.Default.ThumbUp, contentDescription = "Like")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = likeCount.toString())
                    }

                    Button(
                        onClick = onDislikeClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDisliked) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = if (isDisliked) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Icon(Icons.Default.ThumbDown, contentDescription = "Dislike")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = dislikeCount.toString())
                    }


                }

                Text(
                    text = stringResource(id = R.string.first_article_par1),
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )

                Text(
                    text = stringResource(id = R.string.first_article_header2),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = stringResource(id = R.string.first_article_par2),
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Justify
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToSecondArticle() },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.second_article_header),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (!isSecondArticleRead) Color.Black else Color.LightGray,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen(
        onNavigateToSecondArticle = {},
        isSecondArticleRead = false,
        likeCount = 0,
        dislikeCount = 0,
        isLiked = false,
        isDisliked = false
    )
}