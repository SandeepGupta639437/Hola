package com.example.hola

data class HomePageApi(
    val left_bar: List<LeftBar>,
    val posts: List<HomePageResponse>,
    val right_bar: List<RightBar>
)