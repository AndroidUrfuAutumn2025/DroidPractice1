package ru.urfu.droidpractice1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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