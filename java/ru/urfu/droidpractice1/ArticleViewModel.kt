package ru.urfu.droidpractice1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ArticleViewModel(private val state: SavedStateHandle) : ViewModel() {
    var likes by mutableIntStateOf(state["likes"] ?: 0)
        private set
    var dislikes by mutableIntStateOf(state["dislikes"] ?: 0)
        private set

    fun like() {
        likes += 1
        state["likes"] = likes
    }

    fun dislike() {
        dislikes += 1
        state["dislikes"] = dislikes
    }
}