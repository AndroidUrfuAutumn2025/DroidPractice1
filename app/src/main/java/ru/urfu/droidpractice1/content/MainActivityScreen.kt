package ru.urfu.droidpractice1.ui

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleApp() {
    val context = LocalContext.current
    var likeCount by remember { mutableIntStateOf(0) }
    var dislikeCount by remember { mutableIntStateOf(0) }
    var isSecondArticleRead by remember {
        mutableStateOf(
            context.getSharedPreferences("article_prefs", Context.MODE_PRIVATE)
                .getBoolean(SecondActivity.KEY_READ_STATE, false)
        )
    }

    val secondArticleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val read = result.data?.getBooleanExtra(SecondActivity.KEY_READ_STATE, false) ?: false
            isSecondArticleRead = read
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Tech Insights",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "Читайте интересную статью о будущем технологий!")
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Поделиться статьей"))
                },
                icon = { Icon(Icons.Default.Share, contentDescription = null) },
                text = { Text("Поделиться") },
                containerColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Заголовок статьи
            Text(
                text = "Будущее искусственного интеллекта: что нас ждет в 2024 году",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Start
            )

            // Изображение статьи
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://images.unsplash.com/photo-1677442136019-21780ecad995?w=800")
                    .crossfade(true)
                    .build(),
                contentDescription = "Искусственный интеллект и технологии",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.placeholder_image)
            )

            // Счетчик лайков
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { likeCount++ },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(Icons.Default.ThumbUp, contentDescription = "Нравится")
                    }
                    Text("$likeCount", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { dislikeCount++ },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(Icons.Default.ThumbDown, contentDescription = "Не нравится")
                    }
                    Text("$dislikeCount", style = MaterialTheme.typography.bodyMedium)
                }

                Badge(
                    containerColor = if (isSecondArticleRead) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = if (isSecondArticleRead) "✓ Прочитано" else "Новая",
                        fontSize = 12.sp
                    )
                }
            }

            // Содержание статьи
            ArticleContent()

            // Кнопка перехода ко второй статье
            Button(
                onClick = {
                    val intent = Intent(context, SecondActivity::class.java)
                    secondArticleLauncher.launch(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (isSecondArticleRead) "Вторая статья прочитана"
                    else "Читать вторую статью",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ArticleContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        // Введение
        Text(
            text = "Искусственный интеллект продолжает трансформировать нашу жизнь с невероятной скоростью. В этой статье мы рассмотрим ключевые тенденции, которые определят развитие ИИ в ближайшем будущем.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp),
            lineHeight = 24.sp
        )

        // Раздел 1
        Text(
            text = "Генеративный ИИ и креативность",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Генеративные модели, такие как GPT-4 и DALL-E, открыли новые горизонты для творчества. Эти системы способны создавать контент, который ранее считался исключительной прерогативой человека.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            lineHeight = 22.sp
        )

        // Цитата
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = "\"Мы находимся на пороге новой эры, где ИИ станет не просто инструментом, а творческим партнером для человечества.\"",
                style = MaterialTheme.typography.bodyLarge,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        // Раздел 2
        Text(
            text = "Этические вопросы и регулирование",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "С развитием ИИ возникают сложные этические дилеммы. Важно разработать прозрачные стандарты и механизмы контроля для обеспечения безопасного и ответственного использования технологий.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            lineHeight = 22.sp
        )

        // Дополнительное изображение
        Image(
            painter = painterResource(R.drawable.ai_future),
            contentDescription = "Будущее технологий",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        // Заключение
        Text(
            text = "Будущее ИИ обещает быть захватывающим, но требует ответственного подхода. Технологии должны служить человечеству, а не наоборот.",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(vertical = 16.dp),
            lineHeight = 24.sp
        )
    }
}