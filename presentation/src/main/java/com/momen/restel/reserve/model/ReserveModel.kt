package com.momen.restel.reserve.model

data class ReserveModel(
    val id: Int,
    val room: String,
    val client: String,
    val customer: String,
    val startDate: String,
    val finishData: String,
    val price: Int
)
