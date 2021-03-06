package com.momen.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reserve")
data class ReserveEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "room")
    val room: String?,
    @ColumnInfo(name = "room_id")
    val roomId: Int?,
    @ColumnInfo(name = "client")
    val client: String?,
    @ColumnInfo(name = "client_id")
    val clientId: Int?,
    @ColumnInfo(name = "customer")
    val customer: String?,
    @ColumnInfo(name = "customer_id")
    val customerId: Int?,
    @ColumnInfo(name = "start_date")
    val startDate: String?,
    @ColumnInfo(name = "finish_date")
    val finishData: String?,
    @ColumnInfo(name = "price")
    val price: Int?
)