@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen(
    onShareArticle: (content: String) -> Unit,
    handleRate: (
        targetRateState: MutableIntState,
        otherRateState: MutableIntState,
        isTargetChosen: MutableState<Boolean>,
        isOtherChosen: MutableState<Boolean>
    ) -> Unit,
    onOpenNextArticle: () -> Unit,
    isNextRead: Boolean
) {
    var isLiked = rememberSaveable { mutableStateOf(false) }
    var isDisliked = rememberSaveable { mutableStateOf(false) }
    var likesCount = rememberSaveable { mutableIntStateOf(0) }
    var dislikesCount = rememberSaveable { mutableIntStateOf(0) }

    val articleHeading = "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
    val nextArticleHeading = "Phasellus commodo sed metus vitae consectetur"

    DroidPractice1Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
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
                                onShareArticle(articleHeading)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Поделиться"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = articleHeading,
                    style = MaterialTheme.typography.headlineLarge,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (likesCount.intValue > 0) {
                        Text(
                            text = likesCount.intValue.toString()
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Нравится",
                        tint = if (isLiked.value) Color.Black else Color.Gray,
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    handleRate(
                                        likesCount,
                                        dislikesCount,
                                        isLiked,
                                        isDisliked
                                    )
                                }
                            )
                    )
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Не нравится",
                        tint = if (isDisliked.value) Color.Black else Color.Gray,
                        modifier = Modifier
                            .rotate(180f)
                            .clickable(
                                onClick = {
                                    handleRate(
                                        dislikesCount,
                                        likesCount,
                                        isDisliked,
                                        isLiked
                                    )
                                }
                            )
                    )
                    if (dislikesCount.intValue > 0) {
                        Text(
                            text = dislikesCount.intValue.toString()
                        )
                    }
                }
                Text(
                    text = """
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sollicitudin nisl id bibendum ultricies. Phasellus dui nibh, iaculis at ligula commodo, pellentesque iaculis metus. Integer urna nisl, molestie eu euismod nec, dictum vel tortor. Suspendisse potenti. Integer vel sagittis ante. In consequat sed mauris finibus euismod.
                    """.trimIndent(),
                    textAlign = TextAlign.Justify
                )
                AsyncImage(
                    model = "https://i.sunhome.ru/journal/75/doping-mozga.orig.jpg",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
                Text(
                    text = """
                        Vivamus egestas sed velit vitae vehicula. Pellentesque erat nunc, imperdiet a libero ut, mattis pulvinar arcu. Sed quis mi scelerisque, ornare leo sed, placerat turpis. Sed vitae pulvinar tellus, id vulputate metus. Interdum et malesuada fames ac ante ipsum primis in faucibus. Nullam ex velit, vestibulum et odio eget, pharetra maximus tellus.
                    """.trimIndent(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = """
                        Morbi vel posuere massa, non aliquet quam. Pellentesque aliquam nulla lorem, vel sagittis ante consectetur sed. Vivamus pharetra neque at enim pulvinar, in rutrum ligula tincidunt. Etiam et ligula vel lorem tempus varius. Nulla tincidunt placerat neque, eget porta nisl ultrices euismod. Maecenas venenatis sit amet nisl a lobortis.
                    """.trimIndent()
                )
                Text(
                    text = """
                        Phasellus molestie sapien id dui viverra euismod. Vestibulum a mi tincidunt, egestas eros at, elementum purus. Vestibulum at facilisis nisl, eu dapibus enim. Pellentesque dictum ipsum dictum, tempor ante a, elementum urna. Pellentesque pretium interdum tempor. Pellentesque tincidunt tortor pellentesque sem finibus, et porttitor augue consequat.
                    """.trimIndent()
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clickable(
                            onClick = onOpenNextArticle
                        )
                ) {
                    Text(
                        text = nextArticleHeading,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (!isNextRead) Color.Black else Color.Gray,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen(
        onShareArticle = {},
        handleRate = { _,_,_,_ -> },
        onOpenNextArticle = {},
        isNextRead = false
    )
}