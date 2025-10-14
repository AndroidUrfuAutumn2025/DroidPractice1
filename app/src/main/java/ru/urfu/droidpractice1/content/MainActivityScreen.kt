@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen(likeCount: MutableState<Int>, dislikeCount: MutableState<Int>, secondArticle: () -> Unit, isRead: Boolean) {
    val articleText= """
        У Лукашенко мощный бросок». Интервью с американским защитником «Трактора»
        Джордан Гросс рассказывает о смене Минска на Челябинск и визите президента Беларуси в местное «Динамо».
    """.trimIndent()

    DroidPractice1Theme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.article_title),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight(900)
                        )
                    }
                )
            })
        { innerPadding ->
            Column (modifier = Modifier.padding(innerPadding).verticalScroll(rememberScrollState())) {
                Text(
                    text = "«У Лукашенко мощный бросок». Интервью с американским защитником «Трактора»",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom=12.dp))
                Image(
                    painter = painterResource(id = R.drawable.article1_image),
                    contentDescription = "Джордан Гросс рассказывает о смене Минска на Челябинск и визите президента Беларуси в местное «Динамо».",
                    modifier = Modifier.fillMaxWidth().height(230.dp))
                Row(modifier = Modifier.padding(start = 65.dp)){
                    Reactions(likeCount, dislikeCount)
                    Spacer(modifier = Modifier.width(10.dp))
                    ShareButton(articleText)
                }
                Text(text = "Джордан Гросс рассказывает о смене Минска на Челябинск и визите президента Беларуси в местное «Динамо».",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(start = 25.dp, end = 25.dp))
                NextArticle(secondArticle, isRead)
            }


        }
    }
}

@Composable
fun ShareButton(text: String){
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            context.startActivity(Intent.createChooser(intent, "Поделиться статьёй"))
        },
        modifier = Modifier.scale(0.8F),
    )
    {
        Text(text = "Поделиться")
        Image(painter = painterResource(id = R.drawable.baseline_share_24),
            contentDescription = "Share",
            modifier = Modifier.padding(start = 2.dp, end = 0.dp))

    }
}

@Composable
fun Reactions(likeCount: MutableState<Int>, dislikeCount: MutableState<Int>){
    Row {
        Button(onClick = {likeCount.value++}, modifier = Modifier.scale(0.8F)) { Text(text = "${likeCount.value} ❤") }
        Button(onClick = {dislikeCount.value++}, modifier = Modifier.scale(0.8F)) { Text(text = "${dislikeCount.value} \uD83D\uDC94") }
    }
}

@Composable
fun NextArticle(secondArticle: () -> Unit, isRead: Boolean){
    Log.d("MainActivity", "isRead: ${isRead}")
    Button(onClick = secondArticle,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isRead) Color.DarkGray else
                MaterialTheme.colorScheme.primary
        )) {Text("Следующая статья")}
}

//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    val likes = remember {mutableStateOf(0)}
//    val dislikes = remember { mutableStateOf(0) }
//    val secondArticle = remember {  }
//    MainActivityScreen(likes, dislikes)
//}