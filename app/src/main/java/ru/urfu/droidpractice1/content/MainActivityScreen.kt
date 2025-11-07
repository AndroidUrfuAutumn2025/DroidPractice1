@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import ru.urfu.droidpractice1.SecondActivity

@Composable
fun MainActivityScreen() {
    //получение текущего контекста приложения 
    val context = LocalContext.current
    var likesCount by rememberSaveable { mutableIntStateOf(0) }
    var dislikesCount by rememberSaveable { mutableIntStateOf(0) }
    var isArticleRead by rememberSaveable { mutableStateOf(false) }

    val secondActivityLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            result.data?.let { data ->
                isArticleRead = data.getBooleanExtra("isRead", false)
            }
        }
    }

    DroidPractice1Theme {
        //Scaffold - предоставляет структуру с AppBar и содержимым
        //modifier = Modifier.fillMaxSize() - модификатор, который растягивает Scaffold на весь доступный размер
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            //выравнивание элементов по вертикали по центру
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text(
                                text = stringResource(id = R.string.article_title),
                                //занимает все доступное пространство в Row, выталкивая другие элементы вправо
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = {
                                    shareArticle(context)
                                },
                                //добавляет отступ 20dp справа
                                modifier = Modifier.padding(end = 20.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon),
                                    contentDescription = "Поделиться статьей",
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }

                    }
                )
                //автоматические отступы, которые Scaffold предоставляет для контента
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    //применение отступов от Scaffold
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Ливерпуль- не просто команда. You Will Never Walk Alone",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 0.dp, bottom = 13.dp),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold // Жирный шрифт
                    ),
                )

                Image(
                    painter = painterResource(id = R.drawable.football1),
                    contentDescription = "Футбольный мяч на зеленом поле",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(200.dp)
                        //скругление углов согласно теме
                        .clip(MaterialTheme.shapes.medium),
                    //масштабирование с обрезкой для заполнения области
                    contentScale = ContentScale.Crop
                )

                Text(
                    //построение текста с разными стилями для разных частей
                    text = buildAnnotatedString {
                        append("Данная команда основана в ")
                        //применение стиля к части текста
                        withStyle(style = SpanStyle(
                            textDecoration = TextDecoration.Underline // Подчеркивание
                        )
                        ) {
                            append("1892")
                        }
                        append(". Именно с этого момента она стала самой сильной и поддерживаемой командой современного футбола с яркими болельщиками и техничными игроками.")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 13.dp, bottom = 5.dp),
                    style = MaterialTheme.typography.bodyLarge
                )

                // Счетчик лайков и дизлайков
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .padding(top = 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Кнопка лайка
                    IconButton(
                        onClick = { likesCount++ },
                        modifier = Modifier
                            .padding(start = 0.dp, end = 2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.like),
                            contentDescription = "Лайк",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                    }
                    Text(
                        text = likesCount.toString(),
                        modifier = Modifier.padding(horizontal = 3.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    // Кнопка дизлайка
                    IconButton(
                        onClick = { dislikesCount++ },
                        modifier = Modifier
                            .padding(start = 0.dp, end = 2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.like),
                            contentDescription = "Дизлайк",
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(180f),
                            tint = Color.Unspecified
                        )
                    }
                    Text(
                        text = dislikesCount.toString(),
                        modifier = Modifier.padding(horizontal = 3.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Text(
                    text = "Игрок команды в один день смог забить гол, поймать рыбу и родить ребенка",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 6.dp)
                        .background(
                            color = Color.LightGray.copy(
                                alpha = if (isArticleRead) 0.1f else 0.3f // Полупрозрачный при прочтении
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                        .clickable {
                            val intent = Intent(context, SecondActivity::class.java)
                            intent.putExtra("isRead", isArticleRead)
                            secondActivityLauncher.launch(intent)
                        },
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = if (isArticleRead) Color.Gray else Color.Unspecified // Серый цвет при прочтении
                    )
                )
            }
        }
    }
}


private fun shareArticle(context: Context) {
    //текст, который будет отправлен
    val shareText = "Ливерпуль- не просто команда. You Will Never Walk Alone\n\nЧитайте статью в нашем приложении!"

    //Intent() - создание намерения для взаимодействия с другими приложениями
    val shareIntent = Intent().apply {
        //действие "отправить"
        action = Intent.ACTION_SEND
        //добавление текста для отправки
        putExtra(Intent.EXTRA_TEXT, shareText)
        //указание типа данных (простой текст)
        type = "text/plain"
    }

    //Создание диалога с выбором приложения для отправки
    val chooserIntent = Intent.createChooser(shareIntent, "Поделиться статьей")

    // Проверяем, есть ли приложения, которые могут обработать этот Intent
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(chooserIntent)
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen()
}