@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen(articleViewModel: ArticleViewModel = viewModel()) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val likeCount by articleViewModel.likeCount.collectAsState()
    val dislikeCount by articleViewModel.dislikeCount.collectAsState()
    val isArticle2Read by articleViewModel.isArticle2Read.collectAsState()

    val articleTitle = stringResource(R.string.article_title)
    val articleSubtitle = stringResource(R.string.article_subtitle)
    val articleText = stringResource(R.string.article_text)

    // Launcher для получения результата из SecondActivity
    val secondActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == SecondActivity.RESULT_ARTICLE_READ) {
            val data = result.data
            val isRead = data?.getBooleanExtra(SecondActivity.EXTRA_ARTICLE_READ, false) ?: false
            articleViewModel.setArticle2Read(isRead)
        }
    }

    DroidPractice1Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = articleTitle)
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Заголовок статьи
                Text(
                    text = articleTitle,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                // Подзаголовок
                Text(
                    text = articleSubtitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )

                // Изображение
                AsyncImage(
                    model = R.drawable.article_image,
                    contentDescription = "Article Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                // Основной текст
                Text(
                    text = articleText,
                    fontSize = 16.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Лайки и дизлайки
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { articleViewModel.incrementLike() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        )
                    ) {
                        Text("👍 $likeCount", fontSize = 16.sp)
                    }

                    Button(
                        onClick = { articleViewModel.incrementDislike() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF44336)
                        )
                    ) {
                        Text("👎 $dislikeCount", fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Статус второй статьи
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isArticle2Read) Color(0xFFE8F5E8) else Color(0xFFFFEBEE)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Статус второй статьи:",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = if (isArticle2Read) "Прочитано" else "Не прочитано",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isArticle2Read) Color(0xFF2E7D32) else Color(0xFFC62828)
                            )
                        }

                        Icon(
                            imageVector = if (isArticle2Read) Icons.Default.CheckCircle else Icons.Default.Pending,
                            contentDescription = "Status",
                            tint = if (isArticle2Read) Color(0xFF2E7D32) else Color(0xFFC62828)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка поделиться
                Button(
                    onClick = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "$articleTitle\n\n$articleText")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, "Поделиться статьей")
                        context.startActivity(shareIntent)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Text("📤 Поделиться статьей")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка перехода ко второй статье
                Button(
                    onClick = {
                        val intent = Intent(context, SecondActivity::class.java)
                        secondActivityLauncher.launch(intent)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.8f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isArticle2Read) Color(0xFF673AB7) else Color(0xFF9C27B0)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 4.dp
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (isArticle2Read) "📖 Перечитать статью" else "📚 Читать статью",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Дополнительная информация о статусе
                if (isArticle2Read) {
                    Text(
                        text = "✅ Вы уже прочитали эту статью. Состояние сохранено!",
                        fontSize = 14.sp,
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                // Информация о сохранении состояния
                Text(
                    text = "💾 Все состояния (лайки, дизлайки, статус прочтения) сохраняются автоматически",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}