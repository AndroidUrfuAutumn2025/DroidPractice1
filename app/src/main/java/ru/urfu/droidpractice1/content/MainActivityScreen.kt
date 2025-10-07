@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen(
    isSecondArticleRead: Boolean = false,
    onNavigateToSecondArticle: () -> Unit = {}
)  {
    DroidPractice1Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                ArticleTopAppBar()
            }
        ) { innerPadding ->
            ArticleContent(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                isSecondArticleRead = isSecondArticleRead,
                onNavigateToSecondArticle = onNavigateToSecondArticle
            )
        }
    }
}

@Composable
fun ArticleTopAppBar() {
    val context = LocalContext.current
    val articleText = stringResource(id = R.string.article_full_text)
    val articleTitle = stringResource(id = R.string.article_title)


    TopAppBar(
        title = {
            Text(text = articleTitle)
        },
        actions = {
            IconButton(
                onClick = {
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "$articleTitle\n\n$articleText")
                        type = "text/plain"
                    }
                    context.startActivity(
                        Intent.createChooser(
                            shareIntent,
                            context.getString(R.string.share_article)
                        )
                    )
                }
            ) {
                Icon(Icons.Default.Share, contentDescription = "Поделиться")
            }
        }
    )
}

@Composable
fun ArticleContent( modifier: Modifier = Modifier,
                    isSecondArticleRead: Boolean = false,
                    onNavigateToSecondArticle: () -> Unit = {}) {
    val context = LocalContext.current
    val articleText = stringResource(id = R.string.article_full_text)
    val articleTitle = stringResource(id = R.string.article_title)

    var likes by rememberSaveable { mutableIntStateOf(0) }
    var dislikes by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        // Заголовок статьи
        Text(
            text = articleTitle,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://www.shutterstock.com/image-photo/happy-puppy-welsh-corgi-14-600nw-2270841247.jpg")
                    .crossfade(true)
                    .build(),
                contentDescription = "Изображение статьи",
                modifier = Modifier.size(300.dp)
            )
        }

        // Подзаголовок
        Text(
            text = stringResource(id = R.string.article_subtitle),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Основной текст статьи
        Text(
            text = articleText,
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 24.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(id = R.string.article_quote),
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LikeDislikeCounter(
            likes = likes,
            dislikes = dislikes,
            onLike = { likes++ },
            onDislike = { dislikes++ },
            modifier = Modifier.padding(vertical = 16.dp)
        )


        if (isSecondArticleRead) {
            Text(
                text = "✓ Вторая статья прочитана",
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (!isSecondArticleRead) {
            Text(
                text = "✗ Вторая статья не прочитана",
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = onNavigateToSecondArticle,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Перейти ко второй статье")
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Перейти",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun LikeDislikeCounter(
    likes: Int,
    dislikes: Int,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = onLike) {
                Icon(
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = "Нравится"
                )
            }
            Text(text = "$likes")
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = onDislike) {
                Icon(
                    imageVector = Icons.Default.ThumbDown,
                    contentDescription = "Не нравится"
                )
            }
            Text(text = "$dislikes")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen()
}