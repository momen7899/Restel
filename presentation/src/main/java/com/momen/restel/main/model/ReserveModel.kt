package com.momen.restel.main.model

data class ReserveModel(
    val id: Int?,
    val room: String?,
    val roomId: Int?,
    val client: String?,
    val clientId: Int?,
    val customer: String?,
    val customerId: Int?,
    val startDate: String?,
    val finishData: String?,
    val price: Int?
)
