package ru.urfu.droidpractice1.content

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArticleViewModel(
    private val state: SavedStateHandle
) : ViewModel() {

    private val _likes = MutableStateFlow(state.get<Int>("likes") ?: 0)
    val likes: StateFlow<Int> = _likes

    private val _dislikes = MutableStateFlow(state.get<Int>("dislikes") ?: 0)
    val dislikes: StateFlow<Int> = _dislikes

    private val _liked = MutableStateFlow(state.get<Boolean>("liked") ?: false)
    val liked: StateFlow<Boolean> = _liked

    private val _disliked = MutableStateFlow(state.get<Boolean>("disliked") ?: false)
    val disliked: StateFlow<Boolean> = _disliked

    private val _secondRead = MutableStateFlow(state.get<Boolean>("second_read") ?: false)
    val secondRead: StateFlow<Boolean> = _secondRead

    fun like() {
        if (_liked.value) {
            _likes.value -= 1
            _liked.value = false
        } else {
            _likes.value += 1
            _liked.value = true
            if (_disliked.value) {
                _dislikes.value -= 1
                _disliked.value = false
            }
        }
        state["likes"] = _likes.value
        state["liked"] = _liked.value
        state["dislikes"] = _dislikes.value
        state["disliked"] = _disliked.value
    }

    fun dislike() {
        if (_disliked.value) {
            _dislikes.value -= 1
            _disliked.value = false
        } else {
            _dislikes.value += 1
            _disliked.value = true
            if (_liked.value) {
                _likes.value -= 1
                _liked.value = false
            }
        }
        state["likes"] = _likes.value
        state["liked"] = _liked.value
        state["dislikes"] = _dislikes.value
        state["disliked"] = _disliked.value
    }

    fun setSecondRead(read: Boolean) {
        _secondRead.value = read
        state["second_read"] = read
    }
}
