package com.example.pokedex.model.models

import androidx.compose.ui.graphics.Color

enum class TypeColors(val color: Color, val id: String) {
    Normal(
        color = Color(red = 168, green = 167, blue = 122),
        id = "1",
    ),
    Fire(
        color = Color(red =238, green =129, blue =48),
        id = "10",
    ),
    Water(
        color = Color(red =99, green =144, blue =240),
        id = "11",
    ),
    Electric(
        color = Color(red =247, green =208, blue =44),
        id = "13",
    ),
    Grass(
        color = Color(red =122, green =199, blue =76),
        id = "12",
    ),
    Ice(
        color = Color(red =150, green =217, blue =214),
        id = "15",
    ),
    Fighting(
        color = Color(red =194, green =46, blue =40),
        id = "2",
    ),
    Poison(
        color = Color(red =163, green =62, blue =161),
        id = "4",
    ),
    Ground(
        color = Color(red =226, green =191, blue =101),
        id = "5",
    ),
    Flying(
        color = Color(red =169, green =143, blue =243),
        id = "3",
    ),
    Psychic(
        color = Color(red =249, green =85, blue =135),
        id = "14",
    ),
    Bug(
        color = Color(red =166, green =185, blue =26),
        id = "7",
    ),
    Rock(
        color = Color(red =182, green =161, blue =54),
        id = "6",
    ),
    Ghost(
        color = Color(red =115, green =87, blue =151),
        id = "8",
    ),
    Dragon(
        color = Color(red = 111, green =53, blue =252),
        id = "16",
    ),
    Dark(
        color = Color(red =112, green =87, blue =70),
        id = "17",
    ),
    Steel(
        color = Color(red =183, green =183, blue =206),
        id = "9",
    ),
    Fairy(
        color = Color(red = 214, green =133, blue =173),
        id = "18",
    ),
    Stellar(
        color = Color(red = 153, green = 202, blue = 180),
        id = "19",
    ),
    Unknown(
        color = Color(red = 240, green = 240, blue = 240),
        id = "1001",
    ),
}