@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import android.widget.ImageButton
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
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
import ru.urfu.droidpractice1.LikeDislikeCounter
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.shareArticle
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme
import ru.urfu.droidpractice1.ui.theme.PurpleGrey40

@Composable
fun MainActivityScreen() {
    val context = LocalContext.current
    val articleTitle = stringResource(R.string.article1_title)
    var articleUrl = "https://www.championat.com/other/article-6177608-skandal-vokrug-shahmatnogo-vunderkinda-iz-argentiny-faustino-oro-soperniki-poddayutsya-emu-chtoby-on-pobil-mirovoj-rekord.html"
    val isArticleRead = ru.urfu.droidpractice1.SecondActivity.isArticle2Read
    DroidPractice1Theme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,

                        ) {
                            Text(
                                text = stringResource(id = R.string.article_title),
                                modifier = Modifier
                            )
                            IconButton(
                                onClick = {
                                    shareArticle(
                                        context = context,
                                        articleTitle = articleTitle,
                                        articleUrl = articleUrl
                                    )
                                }
                            ) {
                                Icon(
                                    painter = painterResource(android.R.drawable.ic_menu_share),
                                    contentDescription = "Поделиться"
                                )
                            }
                        }
                    }
                )
            }) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
                    .padding(15.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.article1_title),
                    //textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W700,
                    fontSize = 6.em,
                    fontFamily = FontFamily.Serif,
                )
                Row (
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text (
                        text = stringResource(id = R.string.article1_autor),
                        fontFamily = FontFamily.Serif

                    )
                    Text (
                        text = stringResource(id = R.string.article1_date),
                        fontFamily = FontFamily.Serif

                    )
                }
                Image(
                    painter = painterResource(R.drawable.young_chess_player),
                    contentDescription = "Юный игрок в шахматы",
                    modifier = Modifier.size(400.dp,250.dp)
                        .padding(bottom = 8.dp)
                )

                LikeDislikeCounter()

                Column(
                    modifier = Modifier.padding(bottom = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = stringResource(id = R.string.article1_theme1_title),
                        fontWeight = FontWeight.W700,
                        fontSize = 4.5.em,
                    )
                    Text(
                        text = stringResource(id = R.string.article1_theme1_paragraph1)
                    )
                    Text(
                        text = stringResource(id = R.string.article1_theme1_paragraph2)
                    )


                }

                Column (
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.article1_theme2_title),
                        fontWeight = FontWeight.W700,
                        fontSize = 4.5.em,
                    )
                    Text(
                        text = stringResource(id = R.string.article1_theme2_paragraph1)
                    )

                    //ссылка на вторую статью
                    Button(
                        onClick = {
                            val intent = Intent(context, SecondActivity::class.java)
                            context.startActivity(intent)

                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isArticleRead) Color.Gray else Color.Blue
                        ),
                        shape = CutCornerShape(16.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.article2_title),
                            textAlign = TextAlign.Center
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.article1_theme2_paragraph2)
                    )
                    Text(
                        text = stringResource(id = R.string.article1_theme2_paragraph3)
                    )

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