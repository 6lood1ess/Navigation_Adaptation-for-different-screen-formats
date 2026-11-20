package com.example.mycity.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(
    val id: Int,
    @StringRes val nameRes: Int,
    @DrawableRes val imageRes: Int
)

data class Place(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    @DrawableRes val imageRes: Int
)