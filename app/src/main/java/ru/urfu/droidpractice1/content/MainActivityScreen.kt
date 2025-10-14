@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

import coil.compose.AsyncImage
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.graphics.RectangleShape
import ru.urfu.droidpractice1.SecondActivity
import androidx.activity.result.contract.ActivityResultContracts
import android.util.Log

@Composable
fun MainActivityScreen() {
    val context = LocalContext.current

    var likes by rememberSaveable { mutableStateOf(0) }
    var dislikes by rememberSaveable { mutableStateOf(0) }
    var isArticleRead by rememberSaveable { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val data = result.data
            val switchStateFromSecond = data?.getBooleanExtra("switch_state_result", false) ?: false
            isArticleRead = switchStateFromSecond
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
                            val shareText = "Посмотри статью"
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, shareText)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, "Поделиться через")
                            context.startActivity(shareIntent)
                        }) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "Поделиться")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.first_text),
                        fontSize = 30.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 36.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    AsyncImage(
                        model = "https://img.championat.com/s/1350x900/news/big/i/u/zaschitnik-sochi-stal-samym-.jpg",
                        contentDescription = "Article Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.main_text),
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Cursive,
                        fontStyle = FontStyle.Italic,
                        lineHeight = 36.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(onClick = { likes++ }) {
                            Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "Лайк")
                        }
                        Text(text = "$likes", color = Color(0xFF00b800))
                        IconButton(onClick = { dislikes++ }) {
                            Icon(imageVector = Icons.Default.ThumbDown, contentDescription = "Дизлайк")
                        }
                        Text(text = "$dislikes", color = Color.Red)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedButton(
                        onClick = {
                            val intent = Intent(context, SecondActivity::class.java)
                            intent.putExtra("switch_state_result", isArticleRead)
                            launcher.launch(intent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RectangleShape
                    ) {
                        Text(
                            text = if (isArticleRead)
                                "Читать следующую статью (прочитано)"
                            else
                                "Читать следующую статью"
                        )
                    }
                }
            }
        }
    }
}



