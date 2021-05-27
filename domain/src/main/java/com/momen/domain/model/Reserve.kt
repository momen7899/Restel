package com.momen.domain.model

data class Reserve(
    val id: Int,
    val room: Int,
    val client: Int,
    val customer: Int,
    val startDate: String,
    val finishData: String,
    val price: Int
)
