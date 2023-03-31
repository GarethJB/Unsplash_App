package com.example.aop.part2.imagedisplay.models


import com.google.gson.annotations.SerializedName

data class Location(
    val city: String?,
    val country: String?,
    val name: String?,
    val position: Position?
)