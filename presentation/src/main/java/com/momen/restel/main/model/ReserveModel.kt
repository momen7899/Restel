package com.momen.restel.main.model

data class ReserveModel(
    val id: Int,
    val room: Int,
    val client: Int,
    val customer: Int,
    val startDate: String,
    val finishData: String,
    val price: Int
)
