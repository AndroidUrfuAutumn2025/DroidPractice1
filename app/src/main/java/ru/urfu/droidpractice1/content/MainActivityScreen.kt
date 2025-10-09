package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityScreen() {
    val context = LocalContext.current

    var likeCount by rememberSaveable { mutableIntStateOf(0) }
    var dislikeCount by rememberSaveable { mutableIntStateOf(0) }
    var isSecondArticleRead by rememberSaveable { mutableStateOf(false) }

    val secondArticleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val read = result.data?.getBooleanExtra("read_state", false) ?: false
            isSecondArticleRead = read
        }
    }

    DroidPractice1Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.article_title))
                    },
                    actions = {
                        IconButton(onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Ознакомьтесь с этой интересной статьей: Статьи"
                                )
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Поделиться статьей"))
                        }) {
                            Icon(Icons.Filled.Share, contentDescription = "Поделиться")
                        }
                    }
                )
            },
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            val intent = Intent(context, SecondActivity::class.java).apply {
                                putExtra("read_state", isSecondArticleRead)
                            }
                            secondArticleLauncher.launch(intent)
                        },
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = if (isSecondArticleRead) "Вторая статья прочитана" else "Перейти ко второй статье"
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Искусственный интеллект в современном мире",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Как ИИ меняет нашу повседневную жизнь",
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = "https://images.unsplash.com/photo-1677442136019-21780ecad995?w=400",
                    contentDescription = "Иллюстрация искусственного интеллекта",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = """
                        В современном мире искусственный интеллект (ИИ) становится все более важной частью нашей жизни. 
                        Он используется в различных отраслях, от медицины до финансов, и оказывает значительное влияние на то, как мы живем и работаем.
                    """.trimIndent(),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { likeCount++ }) {
                            Icon(
                                Icons.Filled.ThumbUp,
                                contentDescription = "Лайк",
                                tint = Color.Blue
                            )
                        }
                        Text(
                            text = likeCount.toString(),
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { dislikeCount++ }) {
                            Icon(
                                Icons.Filled.ThumbDown,
                                contentDescription = "Дизлайк",
                                tint = Color.Red
                            )
                        }
                        Text(
                            text = dislikeCount.toString(),
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    DroidPractice1Theme {
        MainActivityScreen()
    }
}