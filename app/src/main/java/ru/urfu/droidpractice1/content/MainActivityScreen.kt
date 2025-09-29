@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityScreen(
    isSecondArticleRead: Boolean = false,
    likesCount: Int = 0,
    dislikesCount: Int = 0,
    onLike: () -> Unit = {},
    onDislike: () -> Unit = {},
    onShare: () -> Unit = {},
    onNavigateToSecondArticle: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "Магия",
                            tint = Color(0xFFD4AF37)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Магические статьи")
                        if (isSecondArticleRead) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(
                                containerColor = Color(0xFF2E8B57)
                            ) {
                                Text("✓", color = Color.White, fontSize = 12.sp)
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2C3E50),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A237E),
                            Color(0xFF283593),
                            Color(0xFF303F9F)
                        )
                    )
                )
        ) {
            // Хогвартс
            Image(
                painter = rememberAsyncImagePainter(
                    "https://www.posterior.ru/products/detailed/31/harry-potter-32.jpg"
                ),
                contentDescription = "Хогвартс",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp))

            // Содержание статьи
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Категория
                Text(
                    text = "МАГИЧЕСКИЙ МИР",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFFD4AF37),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0x33D4AF37))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Заголовок
                Text(
                    text = "Гарри Поттер: Путешествие в мир магии",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Мета-информация
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Профессор МакГонагалл",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFB0BEC5)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "1 сентября 1991 • 7 мин",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFB0BEC5)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Контент статьи
                ArticleContent()

                Spacer(modifier = Modifier.height(24.dp))

                // Счетчик лайков/дизлайков
                LikesDislikesCounter(
                    likes = likesCount,
                    dislikes = dislikesCount,
                    onLike = onLike,
                    onDislike = onDislike,
                    onShare = onShare
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Статус второй статьи
                SecondArticleStatus(isRead = isSecondArticleRead)

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка перехода ко второй статье
                Button(
                    onClick = onNavigateToSecondArticle,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD4AF37),
                        contentColor = Color(0xFF2C3E50)
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Узнать о факультетах Хогвартса",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleContent() {
    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.White)) {
                    append("Гарри Поттер - мальчик, который выжил. ")
                }
                append("Его история начинается с трагической гибели родителей и жизни у ужасных родственников Дурслей. ")

                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFFD4AF37))) {
                    append("\n\nВолшебный мир ждал своего героя.")
                }
            },
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 28.sp,
            modifier = Modifier.padding(vertical = 8.dp),
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Цитата
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x33D4AF37)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "\"Волшебство всегда оставляет след. Иногда его можно скрыть, но стереть невозможно.\" - Министр магии",
                style = MaterialTheme.typography.bodyLarge,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                color = Color(0xFFD4AF37),
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.White)) {
                    append("Ключевые моменты:\n\n")
                }
                append("• ")
                withStyle(style = SpanStyle(color = Color(0xFF4FC3F7))) {
                    append("Распределяющая шляпа ")
                }
                append("определяет факультет\n")
                append("• ")
                withStyle(style = SpanStyle(color = Color(0xFF4FC3F7))) {
                    append("Золотой снитч ")
                }
                append("в квиддиче\n")
                append("• ")
                withStyle(style = SpanStyle(color = Color(0xFF4FC3F7))) {
                    append("Дары смерти ")
                }
                append("и их легенда\n")
                append("• ")
                withStyle(style = SpanStyle(color = Color(0xFF4FC3F7))) {
                    append("Омут памяти ")
                }
                append("для просмотра воспоминаний")
            },
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 28.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun LikesDislikesCounter(
    likes: Int,
    dislikes: Int,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onShare: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF37474F)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Оцените магическую статью",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Лайк
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(
                        onClick = onLike,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color(0x334CAF50))
                    ) {
                        Icon(
                            Icons.Default.ThumbUp,
                            contentDescription = "Нравится",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Text(
                        text = likes.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text("Люмос!", color = Color(0xFFB0BEC5), fontSize = 12.sp)
                }

                // Дизлайк
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(
                        onClick = onDislike,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color(0x33F44336))
                    ) {
                        Icon(
                            Icons.Default.ThumbDown,
                            contentDescription = "Не нравится",
                            tint = Color(0xFFF44336),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Text(
                        text = dislikes.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text("Нокс!", color = Color(0xFFB0BEC5), fontSize = 12.sp)
                }

                // Поделиться
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(
                        onClick = onShare,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color(0x33D4AF37))
                    ) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Поделиться",
                            tint = Color(0xFFD4AF37),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Text("Поделиться", color = Color.White, fontSize = 12.sp)
                    Text("Акцио!", color = Color(0xFFB0BEC5), fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun SecondArticleStatus(isRead: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isRead) Color(0x332E8B57) else Color(0x33FF9800)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                if (isRead) Icons.Filled.Favorite else Icons.Filled.Star,
                contentDescription = null,
                tint = if (isRead) Color(0xFF2E8B57) else Color(0xFFFF9800)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isRead) "✓ Статья о факультетах прочитана!"
                else "Статья о факультетах ждет своего читателя",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isRead) FontWeight.Bold else FontWeight.Normal,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun MainScreenPreview() {
    MainActivityScreen()
}