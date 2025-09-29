@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme
import kotlin.Int

@Composable
fun MainActivityScreen(likeCounter: MutableState<Int> = mutableIntStateOf(0),
                       dislikeCounter: MutableState<Int> = mutableIntStateOf(0),
                       isLikePressed: MutableState <Boolean> = mutableStateOf(false),
                       isDislikePressed: MutableState <Boolean> = mutableStateOf(false),
                       isArticleRead: Boolean = false, secondActivity: () -> Unit) {
    val firstPart = "Mercedes-Benz 190 (W201) — автомобиль марки Mercedes-Benz. " +
            "Создан в 1980 году. Выпускался с начала 1982 года по 1993 год. " +
            "В 1993 году был снят с производства, на смену данной модели пришёл " +
            "Mercedes-Benz W202."
    val secondPart = "В начале 80-х годов Mercedes-Benz испытывала трудности. " +
            "Компания, выпускавшая классические модели автомобилей, " +
            "переживала последствия нефтяного кризиса 1973 года, встал " +
            "вопрос выпуска более компактной и экономичной машины. И в 1982 " +
            "году появляется новая экономичная и компактная модель Mercedes-Benz " +
            "190 (W201). После появления компания захватывает лидерство в " +
            "европейском сегменте C и вплотную стала конкурировать с BMW 3 серии. " +
            "Уменьшение длины кузова на 300 мм в сравнении с традиционными " +
            "седанами Mercedes-Benz W123 и применение новых легких сплавов " +
            "позволило выиграть 160 кг снаряженной массы, четырехдверный " +
            "кузов седан получил (впервые для Mercedes-Benz) клиновидный силуэт, " +
            "стекла и двери заподлицо с поверхностью и накладки вместо водосточных " +
            "желобов на крыше, что снизило коэффициент лобового сопротивления С\" " +
            "до 0,33 и, в свою очередь, значительно уменьшило расход топлива. " +
            "В нарушение традиции концерн продолжал давать индекс «190» всем " +
            "моделям семейства W201 вне зависимости от объема двигателя, лишь " +
            "добавляя дополнительные цифры в название модели. "
    val context = LocalContext.current
    DroidPractice1Theme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.article_title)
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SEND)
                                    .setType("text/plain")
                                    .putExtra(Intent.EXTRA_SUBJECT, "Статья")
                                    .putExtra(Intent.EXTRA_TEXT, "Mercedes-Benz W201")
                                context.startActivity(intent)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share"
                            )
                        }
                    }
                )
            }) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                Column (
                    modifier = Modifier
                        .verticalScroll(rememberScrollState()))
                {
                    Text(
                        text = "Mercedes-Benz W201",
                        modifier = Modifier.padding(15.dp, 0.dp, 5.dp, 0.dp),
                        fontSize = 25.sp
                    )
                    Reactions(likeCounter, dislikeCounter, isLikePressed, isDislikePressed)
                    AsyncImage(model = "https://kolesa-uploads.ru/-/cc7bdc9c-c955-4136-8830-9ce279b9bacd/1-zaglavnaia.jpg",
                        contentDescription = "W201",
                        modifier = Modifier.padding(15.dp, 10.dp, 15.dp, 10.dp)
                            .clip(RoundedCornerShape(10)))
                    Text(
                        text = firstPart,
                        modifier = Modifier.padding(15.dp, 0.dp, 5.dp, 10.dp),
                    )
                    NextArticleButton(isArticleRead, secondActivity)
                    Text(
                        text = secondPart,
                        modifier = Modifier.padding(15.dp, 10.dp, 5.dp, 0.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Reactions(likeCounter: MutableState<Int>, dislikeCounter: MutableState<Int>,
              isLikePressed: MutableState<Boolean>, isDislikePressed: MutableState<Boolean>) {
    Row(modifier = Modifier.padding(start = 4.dp)) {
        IconButton(
            onClick = {
                if (isLikePressed.value) {
                    isLikePressed.value = false
                    likeCounter.value--
                } else {
                    if (isDislikePressed.value) {
                        isDislikePressed.value = false
                        dislikeCounter.value--
                    }
                    isLikePressed.value = true
                    likeCounter.value++
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = "Like"
            )
        }
        Text(
            text = likeCounter.value.toString(),
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 13.dp)
        )
        IconButton(
            onClick = {
                if (isDislikePressed.value) {
                    isDislikePressed.value = false
                    dislikeCounter.value--
                } else {
                    if (isLikePressed.value) {
                        isLikePressed.value = false
                        likeCounter.value--
                    }
                    isDislikePressed.value = true
                    dislikeCounter.value++
                }
            }
        ) {
            Icon(
                modifier = Modifier.rotate(180f),
                imageVector = Icons.Default.ThumbUp,
                contentDescription = "Dislike",
            )
        }
        Text(
            text = dislikeCounter.value.toString(),
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 13.dp)
        )
    }
}
@Composable
fun NextArticleButton(isArticleRead: Boolean = false, secondActivity: () -> Unit) {
    Button(
        onClick = secondActivity,
        modifier = Modifier.padding(15.dp, 0.dp, 20.dp, 0.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xffd1d1d1),
            contentColor = if (isArticleRead) Color(0xff969696) else Color(0xff000000)
        )
    ) {
        Text(
            text = "Mercedes-Benz W202 — легковой автомобиль среднего класса " +
                    "немецкого автопроизводителя Mercedes-Benz из серии С-класса. " +
                    "До появления A-класса в 1997 году оставался стартовой " +
                    "моделью компании.",
            fontSize = 12.sp,
            fontWeight = FontWeight(400)
        )
    }
}
