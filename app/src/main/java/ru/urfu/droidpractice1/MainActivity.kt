package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import ru.urfu.droidpractice1.content.MainActivityScreen
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: ArticleViewModel by viewModels()

    companion object {
        const val TAG = "MainActivity"
        const val REQUEST_CODE_SECOND_ARTICLE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        setContent {
            val likes by viewModel.likes.observeAsState(0)
            val dislikes by viewModel.dislikes.observeAsState(0)
            val isSecondArticleRead by viewModel.isSecondArticleRead.observeAsState(false)

            MainActivityScreen(
                likes = likes,
                dislikes = dislikes,
                isSecondArticleRead = isSecondArticleRead,
                onLikeClick = { viewModel.incrementLikes() },
                onDislikeClick = { viewModel.incrementDislikes() },
                onShareClick = { shareArticle() },
                onNextArticleClick = { openSecondArticle() }
            )
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SECOND_ARTICLE) {
            val isRead = data?.getBooleanExtra("is_read", false) ?: false
            viewModel.setSecondArticleRead(isRead)
        }
    }

    private fun shareArticle() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Статья о сериале «Очень странные дела». Рекомендую к прочтению!")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Поделиться статьей"))
    }

    private fun openSecondArticle() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_SECOND_ARTICLE)
    }

    class ArticleViewModel : ViewModel() {

        private val _likes = MutableLiveData(0)
        val likes: LiveData<Int> = _likes

        private val _dislikes = MutableLiveData(0)
        val dislikes: LiveData<Int> = _dislikes

        private val _isSecondArticleRead = MutableLiveData(false)
        val isSecondArticleRead: LiveData<Boolean> = _isSecondArticleRead

        fun incrementLikes() {
            _likes.value = (_likes.value ?: 0) + 1
        }

        fun incrementDislikes() {
            _dislikes.value = (_dislikes.value ?: 0) + 1
        }

        fun setSecondArticleRead(isRead: Boolean) {
            _isSecondArticleRead.value = isRead
        }
    }
}