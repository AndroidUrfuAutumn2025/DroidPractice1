package ru.urfu.droidpractice1.content

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArticleViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val LIKE_KEY = "like_count"
        private const val DISLIKE_KEY = "dislike_count"
        private const val ARTICLE_READ_KEY = "article_read"
    }

    private val _likeCount = MutableStateFlow(savedStateHandle.get<Int>(LIKE_KEY) ?: 0)
    val likeCount: StateFlow<Int> = _likeCount

    private val _dislikeCount = MutableStateFlow(savedStateHandle.get<Int>(DISLIKE_KEY) ?: 0)
    val dislikeCount: StateFlow<Int> = _dislikeCount

    // Статус прочтения второй статьи
    private val _isArticle2Read = MutableStateFlow(savedStateHandle.get<Boolean>(ARTICLE_READ_KEY) ?: false)
    val isArticle2Read: StateFlow<Boolean> = _isArticle2Read

    fun incrementLike() {
        val newValue = _likeCount.value + 1
        _likeCount.value = newValue
        savedStateHandle[LIKE_KEY] = newValue
    }

    fun incrementDislike() {
        val newValue = _dislikeCount.value + 1
        _dislikeCount.value = newValue
        savedStateHandle[DISLIKE_KEY] = newValue
    }

    fun setArticle2Read(isRead: Boolean) {
        _isArticle2Read.value = isRead
        savedStateHandle[ARTICLE_READ_KEY] = isRead
    }
}