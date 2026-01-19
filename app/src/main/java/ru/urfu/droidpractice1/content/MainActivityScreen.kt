@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.content.data.ArticleData
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme
import androidx.core.content.edit

private val IMAGE_HEIGHT = 200.dp
private val PADDING = 16.dp
private val FONT_SIZE = 22.sp
private val ICONS_SIZE = 32.dp

private val titleStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = FONT_SIZE
)

@Composable
fun MainActivityScreen() {
    val context = LocalContext.current

    DroidPractice1Theme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = ArticleData.TITLE,
                            style = titleStyle,
                        )
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        shareArticle(context)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Поделиться",
                        modifier = Modifier.size(ICONS_SIZE)
                    )
                }
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                ArticleContent(
                    modifier = Modifier.weight(1f)
                )
                LikesDislikes()
                LaunchToArticle(context)
            }
        }
    }
}

@Composable
fun ArticleContent(modifier: Modifier = Modifier) {
    val articleText = ArticleData.ARTICLE_TEXT.trimIndent()

    LazyColumn(
        modifier = modifier.padding(PADDING)
    ) {
        item {
            AsyncImage(
                model = ArticleData.IMAGE_URL,
                contentDescription = ArticleData.IMAGE_DESCRIPTION,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IMAGE_HEIGHT)
            )
        }
        item {
            Text(
                text = articleText
            )
        }
    }
}

@Composable
fun LaunchToArticle(context: Context) {
    var isArticleRead by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("article_prefs", Context.MODE_PRIVATE)
        isArticleRead = prefs.getBoolean("article_read", false)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val read = result.data?.getBooleanExtra("article_read", false) ?: false
                isArticleRead = read

                val prefs = context.getSharedPreferences("article_prefs", Context.MODE_PRIVATE)
                prefs.edit { putBoolean("article_read", read) }
            }
        }
    }

    OutlinedButton(
        onClick = {
            val intent = Intent(context, SecondActivity::class.java)
            launcher.launch(intent)
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.DarkGray
        )
    ) {
        Text(
            text = if (isArticleRead) {
                "Следующая статья: S1mple стал первым игроком, достигшим разницы +10 тыс. в соотношении убийств и смертей (прочитано)"
            } else {
                "Следующая статья: S1mple стал первым игроком, достигшим разницы +10 тыс. в соотношении убийств и смертей"
            },
            fontSize = 18.sp
        )
    }
}

@Composable
fun LikesDislikes(modifier: Modifier = Modifier) {
    var likes by rememberSaveable { mutableIntStateOf(0) }
    var dislikes by rememberSaveable { mutableIntStateOf(0) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = { likes++ }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Лайк",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(ICONS_SIZE),
                )
            }
            Text(text = likes.toString(), fontSize = FONT_SIZE)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = { dislikes++ }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Дизлайк",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(ICONS_SIZE)
                )
            }
            Text(text = dislikes.toString(), fontSize = FONT_SIZE)
        }
    }
}

private fun shareArticle(context: Context) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, ArticleData.TITLE)
        putExtra(Intent.EXTRA_TEXT, ArticleData.ARTICLE_TEXT)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Поделиться статьей"))
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen()
}