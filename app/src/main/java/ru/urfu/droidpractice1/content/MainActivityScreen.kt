@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content
import android.util.Log
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import org.jetbrains.annotations.Async
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen() {
    val context = LocalContext.current
    var likesCount by rememberSaveable { mutableIntStateOf(0) }
    var dislikesCount by rememberSaveable { mutableIntStateOf(0) }
    var isRead by rememberSaveable { mutableStateOf(false) }
    val secondActivityLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            result.data?.let { data ->
                isRead = data.getBooleanExtra("article_read", false)
            }
        }
    }
    DroidPractice1Theme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {Text("Статья")},
                    actions = {
                        IconButton(onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Рекомендую прочитать данную статью!"
                                )
                            }
                            context.startActivity(
                                Intent.createChooser(shareIntent,
                                    "Поделиться статьёй"))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Поделиться"
                            )
                        }
                    }
                )

            }

            ) {
            padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Поклонники Umamusume Pretty Derby скорбят о смерти чемпионской скаковой лошади Grass Wonder",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = likesCount.toString()
                    )
                    IconButton(
                        onClick = {likesCount++},
                        modifier = Modifier.padding(start = 0.dp, end = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Лайк",
                            tint = Color.Green
                        )
                    }
                    IconButton(
                        onClick = {dislikesCount++},
                        modifier = Modifier
                            .padding(start = 0.dp, end = 2.dp)
                            
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Лайк",
                            tint = Color.Red,
                            modifier = Modifier
                                .graphicsLayer(rotationZ = 180f)
                        )
                    }
                    Text(
                        text = dislikesCount.toString()
                    )
                }
                Spacer(Modifier.height(12.dp))
                AsyncImage(
                    model = "https://assets-prd.ignimgs.com/2025/08/08/grass-wonder-main-1754672078444.jpg",
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
                )
                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Один из настоящих скаковых лошадей, послуживших вдохновением для персонажа в Umamusume: Pretty Derby, по имени Grass Wonder, печально скончался 8 августа в возрасте 30 лет. Эта новость вызвала волну сочувствия от поклонников аниме-сериала Cygames с персонажами-лошадьми.",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = "В своей выдающейся карьере, длившейся с 1997 по 2000 год, знаменитый скакун по кличке Grass Wonder заслужил репутацию победителя, дважды триумфировав в Arima Kinen, одном из лучших гонок класса 1 в Японии. Из 15 гонок, в которых он участвовал в этот период, он девять раз занял первое место.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(Modifier.height(24.dp))
                AsyncImage(
                    model = "https://static.gosugamers.net/10/0f/c1/60e57eb611b336097b82bc2cd9818439e9b4effde8710add61b71668a3.webp?w=1600",
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Как сообщает ферма Big Red Farm, где Grass Wonder провел последние годы жизни, этот уважаемый бывший скакун поддерживал отличное здоровье до прошлой ночи, когда у него начали проявляться признаки ухудшения. Он мирно скончался от осложнений, связанных с возрастом и затронувших несколько органов, в преклонном возрасте 30 лет, что является замечательным сроком жизни для скаковой лошади.",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Читайте ещё",
                    style = MaterialTheme.typography.headlineMedium
                )

                    Text(
                        "У семьи ленивцев из екатеринбургского зоопарка родился детеныш",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                            color = Color.LightGray.copy(
                                alpha = if (isRead) 0f else 1f
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                            .padding(12.dp)
                            .clickable {
                                val intent = Intent(context, ru.urfu.droidpractice1.SecondActivity::class.java)
                                secondActivityLauncher.launch(intent)
                        },
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = if (isRead) 0.5f else 1f
                            )
                        ))

            }
        }




    }
}



@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen()
}