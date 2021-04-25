package com.momen.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "name")
    val roomName: String?,
    @ColumnInfo(name = "capacity")
    val capacity: Int?,
    @ColumnInfo(name = "price")
    val price: Int?
)