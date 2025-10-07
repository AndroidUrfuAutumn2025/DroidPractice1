package ru.urfu.droidpractice1.state

data class ArticleState(
    var likes: Int = 0,
    var dislikes: Int = 0,
    var isRead: Boolean = false,
)
