package com.momen.domain.model

data class Reserve(
    val id: Int,
    val room: String,
    val client: String,
    val customer: String,
    val startDate: String,
    val finishData: String,
    val price: Int
)
