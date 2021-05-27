package com.momen.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "reserve",
    foreignKeys = [ForeignKey(
        entity = RoomEntity::class, parentColumns = arrayOf("id"),
        childColumns = arrayOf("room"), onDelete = CASCADE
    ), ForeignKey(
        entity = UserEntity::class, parentColumns = arrayOf("id"),
        childColumns = arrayOf("client"), onDelete = CASCADE
    ), ForeignKey(
        entity = CustomerEntity::class, parentColumns = arrayOf("id"),
        childColumns = arrayOf("customer"), onDelete = CASCADE
    )]
)
data class ReserveEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "room")
    val room: Int,
    @ColumnInfo(name = "client")
    val client: Int,
    @ColumnInfo(name = "customer")
    val customer: Int,
    @ColumnInfo(name = "start_date")
    val startDate: String,
    @ColumnInfo(name = "finish_date")
    val finishData: String,
    @ColumnInfo(name = "price")
    val price: Int
)