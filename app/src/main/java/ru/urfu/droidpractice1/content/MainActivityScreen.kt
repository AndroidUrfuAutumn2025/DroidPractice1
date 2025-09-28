@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import org.intellij.lang.annotations.JdkConstants
import ru.urfu.droidpractice1.LikeDislikeCounter
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.shareArticle
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen() {
    val context = LocalContext.current
    val articleTitle = stringResource(R.string.titleStatya1)
    val isArticle = ru.urfu.droidpractice1.SecondActivity.isArticleRead
    val articleURL = "https://www.championat.com/tennis/article-6177716-pekin-2025-setki-rezultaty-raspisanie-gde-smotret-kak-sygrali-daniil-medvedev-andrej-rublyov-mirra-i-diana-v-pare.html"
    DroidPractice1Theme {
        Scaffold(modifier = Modifier
            .fillMaxSize()
            .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row (modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.article_title),
                                style = MaterialTheme.typography.headlineLarge,
                                fontSize = 5.em,
                                color = Color.Gray
                            )
                            IconButton(
                                onClick = {
                                shareArticle(
                                    context,
                                    articleTitle = articleTitle,
                                    articleURL = articleURL
                                )
                            }
                            ) {
                                Icon(
                                    painter = painterResource(android.R.drawable.ic_menu_share),
                                    contentDescription = "Поделиться статьей",
                            ) }

                        }

                    }
                )
            }) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()) {
                    Text(
                        text = stringResource(R.string.titleStatya1),
                        style = MaterialTheme.typography.headlineLarge,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 6.em,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Image(

                        painter = painterResource(R.drawable.image1),
                        contentDescription = "Фотка для первой статьи",
                        modifier = Modifier.size(400.dp, 300.dp)

                    )
                    LikeDislikeCounter()
                    Text(
                        text = stringResource(R.string.bodyText),
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    Text(
                        text = stringResource(R.string.bodyText2),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(0.dp, 14.dp)
                    )
                    Button(
                        onClick = {
                            val intent = Intent(context, SecondActivity::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isArticle) Color.Gray else Color.Unspecified
                        )

                    ) {
                        Text(
                            text= if (!isArticle) "Вот как Флавио победил Андрея в Гамбурге [статья]" else "Вот как Флавио победил Андрея в Гамбурге [статья прочитана]",
                            textAlign = TextAlign.Center
                        )
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen()
}