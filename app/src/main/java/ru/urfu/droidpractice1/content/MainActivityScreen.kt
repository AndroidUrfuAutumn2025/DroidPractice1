@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme
import coil.compose.AsyncImage


@Composable
fun MainActivityScreen(
    activityLauncher: ActivityResultLauncher<Intent>? = null
) {
    val context = LocalContext.current
    var likeCount by rememberSaveable { mutableIntStateOf(0) }
    var dislikeCount by rememberSaveable { mutableIntStateOf(0) }
    var isSecondArticleRead by remember { mutableStateOf(false) }
    
    // функция для чтения состояния из SharedPreferences
    fun updateReadStatus() {
        val sharedPreferences = context.getSharedPreferences("article_prefs", Context.MODE_PRIVATE)
        isSecondArticleRead = sharedPreferences.getBoolean("second_article_read", false)
    }
    
    // обновляем состояние при создании компонента
    LaunchedEffect(Unit) {
        updateReadStatus()
    }
    
    // обновляем состояние при возобновлении активности
    DisposableEffect(context) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                updateReadStatus()
            }
        }
        if (context is ComponentActivity) {
            context.lifecycle.addObserver(observer)
        }
        onDispose {
            if (context is ComponentActivity) {
                context.lifecycle.removeObserver(observer)
            }
        }
    }


    
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
                        IconButton(onClick = {
                            val share = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, "Первая статья: ${context.getString(R.string.article_title)}")
                            }
                            context.startActivity(Intent.createChooser(share, "Поделиться"))
                        }) { Icon(Icons.Filled.Share, contentDescription = "Поделиться") }
                    }
                )
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                Text(
                    text = stringResource(id = R.string.first_article_subtitle),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 32.sp
                )

                Spacer(Modifier.height(12.dp))

                AsyncImage(
                    model = R.drawable.first_activity_photo,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = stringResource(id =R.string.first_article_long_text_1),
                    textAlign = TextAlign.Justify
                )

                Spacer(Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { likeCount += 1 }) {
                            Icon(Icons.Filled.ThumbUp, contentDescription = "Like")
                        }
                        Text(
                            text = likeCount.toString(),
                            color = Color.Red
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { dislikeCount += 1 }) {
                            Icon(Icons.Filled.ThumbDown, contentDescription = "Dislike")
                        }
                        Text(text = dislikeCount.toString(), color = Color.Red)
                    }
                    Spacer(Modifier.weight(1f))
                }
                Button(
                    onClick = {
                        val intent = Intent(context, SecondActivity::class.java)
                        if (activityLauncher != null) {
                            activityLauncher.launch(intent)
                        } else {
                            context.startActivity(intent)
                        }
                    }
                ) {
                    Text(
                        if (isSecondArticleRead) 
                            stringResource(id = R.string.button_article_read)
                        else 
                            stringResource(id = R.string.button_article_not_read)
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