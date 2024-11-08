package com.example.restaurantsapp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    @SerialName("r_id")
    val id: Int,
    @SerialName("r_title")
    val title: String,
    @SerialName("r_description")
    val description: String
)
