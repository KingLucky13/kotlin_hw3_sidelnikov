package com.example.kotlin_vk3

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MovieShort(
    val id: Int,
    val title: String,
    val year: Int,
    val rating: Double,
    val imgUrl: String,
    val isBookmarked: Boolean = false
    ): Parcelable
