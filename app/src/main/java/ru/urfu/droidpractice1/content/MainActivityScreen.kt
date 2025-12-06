@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import ru.urfu.droidpractice1.R

@Composable
fun MainActivityScreen(
    isSecondArticleRead: Boolean,
    likeCount: MutableState<Int>,
    dislikeCount: MutableState<Int>,
    onNextArticleClick: () -> Unit
) {
    val articleText = """
        Согласно новому отчету Alinea Analytics, научно-фантастический шутер ARC Raiders от Embark Studios стал самой продаваемой игрой в Steam по количеству проданных копий за ноябрь 2025 года.
        
        Этот успех является последним свидетельством того, что этот экстракшен-шутер от третьего лица продолжает оставаться одной из главных игр года. Сообщается, что общее количество проданных копий ARC Raiders на всех платформах (ПК через Steam, PS5, Xbox Series X/S) достигло 7,7 миллиона.
    """.trimIndent()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        val context = LocalContext.current

                        Text(text = stringResource(id = R.string.article_title))
                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, articleText)
                                }
                                context.startActivity(
                                    Intent.createChooser(
                                        intent,
                                        "Поделиться",
                                    )
                                )
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainActivityContent(
                articleText = articleText,
                isSecondArticleRead = isSecondArticleRead,
                likeCount = likeCount,
                dislikeCount = dislikeCount,
                onNextArticleClick = onNextArticleClick
            )
        }
    }
}

@Composable
fun MainActivityContent(
    articleText: String,
    isSecondArticleRead: Boolean,
    likeCount: MutableState<Int>,
    dislikeCount: MutableState<Int>,
    onNextArticleClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Text(
            text = "ARC Raiders стала самой продаваемой игрой в Steam в ноябре 2025 года — на всех платформах уже 7,7 миллиона проданных копий",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        LikeAndDislike(likeCount, dislikeCount)
        Image(
            painter = rememberAsyncImagePainter("https://www.goha.ru/s/A:Nl/mM/flyAB9cfSe.png"),
            contentDescription = "Картинка статьи",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(vertical = 16.dp),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = articleText,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
        NextArticleButton(
            nextArticleText = "На релизе Battlefield 6 количество игроков превысило 750 тысяч, а сейчас в среднем в день играет около 140 тысяч",
            isRead = isSecondArticleRead,
            onClick = onNextArticleClick,
        )
    }
}

@Composable
private fun LikeAndDislike(likeCount: MutableState<Int>, dislikeCount: MutableState<Int>) {
    Row {
        LikeOrDislikeItem(count = likeCount, iconId = R.drawable.ic_like)
        Spacer(modifier = Modifier.size(size = 10.dp))
        LikeOrDislikeItem(count = dislikeCount, iconId = R.drawable.ic_dislike)
    }
}

@Composable
private fun LikeOrDislikeItem(count: MutableState<Int>, iconId: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { count.value++ }) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Like"
            )
        }
        Text(text = "${count.value}")
    }
}

@Composable
fun NextArticleButton(nextArticleText: String, isRead: Boolean, onClick: () -> Unit) {
    Text(
        text = nextArticleText,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 10.dp))
            .background(if (isRead) Color.Gray else MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .clickable(onClick = onClick),
    )
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen(
        isSecondArticleRead = false,
        likeCount = mutableStateOf(7),
        dislikeCount = mutableStateOf(4),
        onNextArticleClick = {},
    )
}