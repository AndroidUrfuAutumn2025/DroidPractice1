@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import android.content.Intent
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

@Composable
fun MainActivityScreen() {
    val context = LocalContext.current
    var isRead by rememberSaveable { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val read = result.data?.getBooleanExtra("article_read", false) ?: false
            isRead = read
        }
    }
    DroidPractice1Theme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.article_title)
                        )
                    }
                )
            }) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                ArticleContent(
                    isRead = isRead,
                    onOpenSecond = {
                        val intent = Intent(context, SecondActivity::class.java)
                        launcher.launch(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun ArticleContent(isRead: Boolean, onOpenSecond: () -> Unit) {
    val context = LocalContext.current
    var likes by rememberSaveable { mutableIntStateOf(0) }
    var dislikes by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Почему от России в «Золотом мяче» Рафинья так низко? Все подробности голосования",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "«Золотой мяч» по итогам сезона-2024/2025 вручили 22 сентября, однако результаты голосования резонируют до сих пор. Так что с чистой совестью подводим итоги. От России, как и всегда, голосовал журналист Константин Клещёв.",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Card(shape = RoundedCornerShape(12.dp)) {
            AsyncImage(
                model = "https://img.championat.com/s/1350x900/news/big/p/c/intervyu-s-konstantinom-kleschyovym_17586584231337384420.jpg",
                contentDescription = "Интервью с Константином Клещёвым",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxSize()
            )
        }
        Text(
            text = "— Как давно вы голосуете за «Золотой мяч» от России?",
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Text(
            text = "— С 1993 года, больше 30 лет. Каждый год, без исключений. Как на работу!",
            fontSize = 15.sp,
            color = Color.Black
        )
        Text(
            text = "— Вы должны аргументировать решение для France Football?",
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Text(
            text = "— Нет, не должен. Отправляешь итог своего голосования, но никаких сопроводительных материалов не отсылаешь. Твой выбор понятен: любой из претендентов достоин быть в числе лучших. Не самым лучшим, конечно — тут отдельные критерии.",
            fontSize = 15.sp,
            color = Color.Black
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { likes++ }) {
                Icon(Icons.Filled.ThumbUp, contentDescription = "Like", tint = Color.Green)
            }
            Text(text = likes.toString(), modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.size(8.dp))
            IconButton(onClick = { dislikes++ }) {
                Icon(Icons.Filled.ThumbDown, contentDescription = "Dislike", tint = Color.Red)
            }
            Text(text = dislikes.toString(), modifier = Modifier.size(24.dp))
        }
        if (isRead) {
            Text(
                text = "Вторая статья прочитана!",
                color = Color(0xFF388E3C),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Почему от России в «Золотом мяче» Рафинья так низко? Все подробности голосования")
                }
                context.startActivity(Intent.createChooser(shareIntent, null))
            }) {
                Icon(Icons.Filled.Share, contentDescription = "Share")
            }
            Button(onClick = onOpenSecond) {
                Text("Ко второй статье")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen()
}