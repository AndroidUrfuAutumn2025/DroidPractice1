@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.article_title)) })
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            ArticlePage(
                isSecondArticleRead = isSecondArticleRead,
                likeCount = likeCount,
                dislikeCount = dislikeCount,
                onNextArticleClick = onNextArticleClick
            )
        }
    }
}

@Composable
fun ArticlePage(
    isSecondArticleRead: Boolean,
    likeCount: MutableState<Int>,
    dislikeCount: MutableState<Int>,
    onNextArticleClick: () -> Unit
) {
    val articleText = """
        «Крылья Советов» уступили «Спартаку» со счётом 0:3. 
        Главный тренер самарской команды Адиев прокомментировал поражение.
        «Конечно, нам непросто. Сегодня игроки сделали всё, что могли. Нужно разбирать ошибки и работать дальше», — сказал тренер.
    """.trimIndent()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Тренер «Крыльев Советов» Адиев прокомментировал поражение от «Спартака»",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Реакция главного тренера после матча",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = rememberAsyncImagePainter("https://img.championat.com/s/732x488/news/big/f/z/trener-krylev-sovetov-adiev-.jpg"),
            contentDescription = "Картинка статьи",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = articleText,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        ShareButton(articleText)
        Spacer(modifier = Modifier.height(16.dp))
        LikeDislikeCounter(likeCount, dislikeCount)
        Spacer(modifier = Modifier.height(16.dp))
        NextArticleButton(isRead = isSecondArticleRead, onClick = onNextArticleClick)
    }
}

@Composable
fun ShareButton(articleText: String) {
    val context = LocalContext.current
    Button(onClick = {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, articleText)
        }
        context.startActivity(Intent.createChooser(intent, "Поделиться статьей"))
    }) {
        Text(text = "Поделиться")
    }
}

@Composable
fun LikeDislikeCounter(likeCount: MutableState<Int>, dislikeCount: MutableState<Int>) {
    Row {
        Button(onClick = { likeCount.value++ }) {
            Text("👍 ${likeCount.value}")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = { dislikeCount.value++ }) {
            Text("👎 ${dislikeCount.value}")
        }
    }
}

@Composable
fun NextArticleButton(isRead: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isRead) Color.Gray else MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = "Читать следующую статью")
    }
}
