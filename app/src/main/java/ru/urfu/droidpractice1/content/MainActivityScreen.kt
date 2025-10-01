@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen(
    likes: Int,
    dislikes: Int,
    isSecondArticleRead: Boolean,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onOpenSecond: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val articleTitle = "Шокирующие новости 😲"
    val articleBody = "Ивангай и Марьяна Ро, одна из самых обсуждаемых пар российского YouTube, объявили о расставании..."
    val fullArticleText = "$articleTitle\n\n$articleBody"


    DroidPractice1Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.article_title)) },
                    actions = {
                        IconButton(onClick = {
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, fullArticleText)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Поделиться"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(scrollState)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Статья
                Column {
                    Text(
                        text = articleTitle,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Блок лайков и дизлайков
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Лайк
                        IconButton(onClick = onLike) {
                            Image(painter = painterResource(id = R.drawable.img_2), contentDescription = "Лайк")
                        }
                        Text(text = likes.toString(), style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.width(16.dp))
                        // Дизлайк
                        IconButton(onClick = onDislike) {
                            Image(painter = painterResource(id = R.drawable.img_3), contentDescription = "Дизлайк")
                        }
                        Text(text = dislikes.toString(), style = MaterialTheme.typography.bodyLarge)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.img),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = articleBody,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (isSecondArticleRead) {
                        Text("Вторая статья прочитана ✅")
                    }
                }

                Button(
                    onClick = onOpenSecond
                ) {
                    Text("Перейти ко второй статье")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen(10, 5, true, {}, {}, {})
}
