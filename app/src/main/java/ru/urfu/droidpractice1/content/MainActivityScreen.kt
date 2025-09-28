@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.urfu.droidpractice1.MainScreenHandler
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme
import ru.urfu.droidpractice1.ui.theme.Typography

@Composable
fun MainActivityScreen(
    handler: MainScreenHandler, isRead: Boolean = false, likesCount: Int = 0
) {
    DroidPractice1Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(), topBar = {
                TopAppBar(title = {
                    Text(
                        text = stringResource(id = R.string.article_title)
                    )
                }, actions = {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable { handler.onToShareClicked() },
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = "Поделиться"
                    )
                })
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.main_article_header),
                    style = Typography.headlineMedium,
                    fontWeight = FontWeight.Medium
                )

                AsyncImage(
                    model = "https://c.f1news.ru/userfiles/179009.jpg",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )

                Text(
                    text = stringResource(id = R.string.main_article_text_1),
                    style = Typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = stringResource(id = R.string.main_article_text_2),
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                        .clickable { handler.onSecondArticleClicked() }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(10.dp),
                        text = stringResource(id = R.string.second_article_header),
                        color = if (isRead) Color.Gray else Color.Black,
                    )
                }

                Row(modifier = Modifier.padding(vertical = 10.dp)) {
                    Icon(
                        modifier = Modifier
                            .clickable { handler.onDislikeClicked() },
                        painter = painterResource(id = R.drawable.thumb_down),
                        contentDescription = "Убрать лайк"
                    )

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        text = likesCount.toString(),
                        color = Color.Black,
                        fontSize = 20.sp
                    )

                    Icon(
                        modifier = Modifier
                            .clickable { handler.onLikeClicked() },
                        painter = painterResource(id = R.drawable.thumb_up),
                        contentDescription = "Поставить лайк"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen(handler = object : MainScreenHandler {
        override fun onSecondArticleClicked() {}
        override fun onToShareClicked() {}
        override fun onLikeClicked() {}
        override fun onDislikeClicked() {}
    })
}