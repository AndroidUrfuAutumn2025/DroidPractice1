@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.app.Activity
import android.content.Context
import ru.urfu.droidpractice1.SecondActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.unit.Dp

@Composable
fun MainActivityScreen() {
    DroidPractice1Theme {
        val vm: ArticleViewModel = viewModel()
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
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Путешествие по Уралу: скрытые жемчужины",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Автор: А. Иванов • 29 сентября 2025",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1601758125946-6ec2ef5b7c2e?w=1200",
                        contentDescription = "Горный пейзаж Урала",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)) {
                                append("Введение. ")
                            }
                            append("Урал — это не только промышленное сердце России, но и невероятно красивый регион с богатой природой. ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("В этой статье ")
                            }
                            append("мы расскажем о местах, которые редко попадают в туристические путеводители.")
                        },
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)) {
                                append("1. Плато ")
                            }
                            withStyle(SpanStyle(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)) {
                                append("Маньпупунёр")
                            }
                            append(" — одно из самых загадочных мест, где ветер и время сотворили каменные изваяния высотой до 42 метров.")
                        },
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)) {
                                append("2. Пещера Кунгурская.")
                            }
                            append(" Ледяные кристаллы и подземные озёра создают атмосферу зимней сказки даже летом.")
                        },
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Совет: ") }
                            append("планируйте маршруты заранее и учитывайте погодные условия. Связь может пропадать в горах.")
                        },
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    val context = LocalContext.current
                    val prefs = context.getSharedPreferences("articles", Context.MODE_PRIVATE)
                    var isSecondRead by remember { mutableStateOf(prefs.getBoolean("second_read", false)) }

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartActivityForResult()
                    ) { result ->
                        if (result.resultCode == Activity.RESULT_OK) {
                            val value = result.data?.getBooleanExtra("second_read", false)
                            if (value != null) {
                                isSecondRead = value
                            } else {
                                isSecondRead = prefs.getBoolean("second_read", false)
                            }
                        } else {
                            isSecondRead = prefs.getBoolean("second_read", false)
                        }
                    }

                    Button(onClick = {
                        launcher.launch(Intent(context, SecondActivity::class.java))
                    }) { Text(text = "Читать следующую статью") }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isSecondRead) "Статус: вторая статья прочитана" else "Статус: вторая статья не прочитана",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(onClick = {
                        val shareText = "Путешествие по Уралу: скрытые жемчужины. " +
                                "Урал — невероятно красивый регион с богатыми маршрутами. " +
                                "1) Плато Маньпупунёр. 2) Пещера Кунгурская. " +
                                "Совет: планируйте маршруты и учитывайте погоду."
                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }
                        val chooser = Intent.createChooser(sendIntent, "Поделиться статьёй")
                        context.startActivity(chooser)
                    }) {
                        Text(text = "Поделиться")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(onClick = { vm.like() }) { Text(text = "Лайк: ${vm.likes}") }
                        Button(onClick = { vm.dislike() }) { Text(text = "Дизлайк: ${vm.dislikes}") }
                    }
                }
            }
        }
    }
}

class ArticleViewModel(private val state: SavedStateHandle) : ViewModel() {
    var likes by mutableStateOf(state["likes"] ?: 0)
        private set
    var dislikes by mutableStateOf(state["dislikes"] ?: 0)
        private set

    fun like() {
        likes += 1
        state["likes"] = likes
    }

    fun dislike() {
        dislikes += 1
        state["dislikes"] = dislikes
    }
}



@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen()
}