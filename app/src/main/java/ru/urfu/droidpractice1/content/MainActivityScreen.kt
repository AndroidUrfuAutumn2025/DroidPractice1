@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.ui.theme.Blue77
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme
import kotlin.jvm.java

@Composable
fun MainActivityScreen() {
    var isLiked by rememberSaveable { mutableStateOf(false) }
    var likeCount by rememberSaveable { mutableStateOf(0) }
    val context = LocalContext.current
    DroidPractice1Theme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.article_title)
                        )
                    }
                )

            },
            bottomBar = {
                NavigationBar {
                    Row(modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                    ) {
                        Text(text = "$likeCount",
                            modifier = Modifier.padding(10.dp)
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        IconButton(onClick = {
                            if (!isLiked) {
                                likeCount++
                                isLiked = true
                            } else {
                                likeCount--
                                isLiked = false
                            }

                        }) {
                            Icon(
                                imageVector = if (isLiked) Icons.Outlined.Favorite else Icons.Filled.FavoriteBorder ,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(60.dp)
                            )
                        }
                        IconButton(onClick = {
                            val intent = Intent(context, SecondActivity::class.java)
                            context.startActivity(intent)
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(60.dp)
                            )
                        }
                        IconButton(onClick = {}, modifier = Modifier
                            .align(Alignment.Bottom))
                        {
                            Icon(imageVector = Icons.Default.Share,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
            ) { innerPadding ->
            Column (modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
            ){
                Card (
                    shape = RoundedCornerShape(
                        bottomStart = 20.dp,
                        topEnd = 20.dp
                    ),
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(1f)
                        .size(70.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(Blue77)
                            .fillMaxWidth(1f)
                            .size(70.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.name_player_one),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Serif,
                            textDecoration = TextDecoration.Underline,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(20.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
                Box (modifier = Modifier
                    .fillMaxWidth(1f)
                    .size(250.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.nik),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                    )
                }
                Card(
                    shape = RoundedCornerShape(
                        bottomStart = 20.dp,
                        topEnd = 20.dp
                    ),
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(Blue77)
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxSize()
                        ) {
                            Text(text = stringResource(id = R.string.title_one),
                                modifier = Modifier
                                    .padding(2.dp),
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Text (text = stringResource(id = R.string.age_one_player),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .padding(2.dp)
                                )
                            Text (text = stringResource(id = R.string.first_name_title),
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier
                                    .padding(2.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.content_title),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(2.dp)
                            )
                        }
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