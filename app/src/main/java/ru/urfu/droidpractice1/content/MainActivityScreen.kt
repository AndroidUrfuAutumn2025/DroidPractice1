@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MainActivityScreen(isSecondArticleRead: Boolean = false,
                       onNavigateToSecondArticle: () -> Unit = {}) {
    DroidPractice1Theme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                ArticleTopAppBar()
            }) { innerPadding ->
            ArticleContent(modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
                isSecondArticleRead,
                onNavigateToSecondArticle)
        }
    }
}

@Composable
fun ArticleTopAppBar(){
    val context = LocalContext.current
    val articleTitle= stringResource(id = R.string.article_title)
    val articleContent1 = stringResource(id = R.string.article_content1)
    val articleContent2 = stringResource(id = R.string.article_content2)
    val articleContent3 = stringResource(id = R.string.article_content3)
    val articleContent4 = stringResource(id = R.string.article_content4)
    val articleContent5 = stringResource(id = R.string.article_content5)

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name)

            )
        },
        actions = {
            IconButton(
                onClick = {
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "$articleTitle\n" +
                                "\n$articleContent1" +
                                "\n$articleContent2" +
                                "\n$articleContent3" +
                                "\n$articleContent4" +
                                "\n$articleContent5"
                        )
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
fun ArticleContent(modifier: Modifier = Modifier,
                   isSecondArticleRead: Boolean = false,
                   onNavigateToSecondArticle: () -> Unit = {}){

    var likes by rememberSaveable { mutableIntStateOf(0) }
    var dislikes by rememberSaveable { mutableIntStateOf(0) }

    Column (modifier = modifier
        .verticalScroll(rememberScrollState())
        .padding(16.dp)
    )
    {
        Text(
            text = stringResource(id = R.string.article_title),
            modifier = Modifier
                .background(color = Color(21, 125, 5)),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace,
            fontSize = 20.sp,
            color = Color(255,255,255)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://ecology.md/upload/images/1738360800/1739859232_0824.jpg")
                    .crossfade(true)
                    .build(),
                contentDescription = "Изображение статьи",
                modifier = Modifier.size(300.dp)
            )
        }

        Text(
            text = stringResource(id = R.string.article_content1),
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Text(
            text = stringResource(id = R.string.article_content2),
            textAlign = TextAlign.Justify
        )

        Text(
            text = stringResource(id = R.string.article_content3),
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(vertical = 16.dp),
        )

        Text(
            text = stringResource(id = R.string.article_content4),
            textAlign = TextAlign.Justify
        )

        Text(
            text = stringResource(id = R.string.article_content5),
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(top = 16.dp),
        )
        LikeDislikeCounter(
            likes,
            dislikes,
            onLike = {likes++},
            onDislike = {dislikes++},
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if (!isSecondArticleRead) {
            Text(
                text = "Вторая статья не прочитана",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.Red
            )
        }

        if (isSecondArticleRead) {
            Text(
                text = "Вторая статья прочитана",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.Green
            )
        }

        Button(
            onClick = onNavigateToSecondArticle,
            modifier = Modifier.padding(vertical = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
            containerColor = Color.Green
            )
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
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Нравится"
                )
            }
            Text(text = "$likes")
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = onDislike) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
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