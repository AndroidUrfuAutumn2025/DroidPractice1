@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.SecondActivity
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

private const val PREFS_NAME = "app_preferences"
private const val KEY_ARTICLE_READ = "is_second_article_read"
private const val KEY_LIKES = "likes_count"
private const val KEY_DISLIKES = "dislikes_count"
private const val KEY_USER_VOTE = "user_vote_state"

@Composable
fun MainActivityScreen() {
    val context = LocalContext.current
    val prefsManager = remember { ArticlePreferencesManager(context) }
    var isNextArticleRead by remember { mutableStateOf(prefsManager.isRead()) }


    DisposableEffect(Unit) {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == KEY_ARTICLE_READ) {
                isNextArticleRead = prefsManager.isRead()
            }
        }
        prefsManager.registerListener(listener)
        onDispose {
            prefsManager.unregisterListener(listener)
        }
    }

    DroidPractice1Theme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Droid Blog",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NewsFeedLayout(isNextArticleRead, prefsManager)
            }
        }
    }
}

@Composable
fun NewsFeedLayout(isRead: Boolean, prefs: ArticlePreferencesManager) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                SuggestionChip(
                    onClick = { },
                    label = { Text("Технологии") },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    border = null
                )

                Text(
                    text = stringResource(id = R.string.article1_title),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )


                AsyncImage(
                    model = "https://images.unsplash.com/photo-1677442136019-21780ecad995?q=80&w=1000&auto=format&fit=crop",
                    contentDescription = "AI Coding",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )


                Text(
                    text = stringResource(id = R.string.article1_p1),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)


                Text(
                    text = stringResource(id = R.string.article1_p2),
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }


        ActionButtonsSection(prefs)


        NavigationSection(isRead)

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun ActionButtonsSection(prefs: ArticlePreferencesManager) {
    val context = LocalContext.current

    var likes by remember { mutableIntStateOf(prefs.getLikes()) }
    var dislikes by remember { mutableIntStateOf(prefs.getDislikes()) }
    var userVote by remember { mutableIntStateOf(prefs.getUserVote()) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp,
            border = null
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
            ) {

                IconButton(onClick = {
                    if (userVote == 1) return@IconButton
                    if (userVote == -1) { dislikes--; prefs.saveDislikes(dislikes) }
                    likes++
                    userVote = 1
                    prefs.saveLikes(likes)
                    prefs.saveUserVote(userVote)
                }) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Like",
                        tint = if (userVote == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = likes.toString(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.width(12.dp))


                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(24.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )

                Spacer(modifier = Modifier.width(4.dp))


                IconButton(onClick = {
                    if (userVote == -1) return@IconButton
                    if (userVote == 1) { likes--; prefs.saveLikes(likes) }
                    dislikes++
                    userVote = -1
                    prefs.saveDislikes(dislikes)
                    prefs.saveUserVote(userVote)
                }) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Dislike",
                        modifier = Modifier.rotate(180f),
                        tint = if (userVote == -1) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = dislikes.toString(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
        }


        FilledTonalIconButton(
            onClick = {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Смотри, как ИИ меняет программирование!")
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(sendIntent, null))
            },
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Icon(Icons.Default.Share, contentDescription = "Share")
        }
    }
}

@Composable
fun NavigationSection(isRead: Boolean) {
    val context = LocalContext.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                context.startActivity(Intent(context, SecondActivity::class.java))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Читать далее", fontSize = 16.sp)
        }

        if (isRead) {
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Прочитано",
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}


class ArticlePreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun isRead() = prefs.getBoolean(KEY_ARTICLE_READ, false)
    fun getLikes() = prefs.getInt(KEY_LIKES, 0)
    fun saveLikes(count: Int) = prefs.edit().putInt(KEY_LIKES, count).apply()
    fun getDislikes() = prefs.getInt(KEY_DISLIKES, 0)
    fun saveDislikes(count: Int) = prefs.edit().putInt(KEY_DISLIKES, count).apply()
    fun getUserVote() = prefs.getInt(KEY_USER_VOTE, 0)
    fun saveUserVote(state: Int) = prefs.edit().putInt(KEY_USER_VOTE, state).apply()
}

@Preview(showBackground = true)
@Composable
fun NewsPreview() {
    MainActivityScreen()
}