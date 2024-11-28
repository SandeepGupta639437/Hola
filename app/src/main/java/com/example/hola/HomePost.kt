package com.example.hola

data class HomePost(
    val left_bar: List<LeftBar>,
    val posts: List<Post>,
    val right_bar: List<RightBar>
)