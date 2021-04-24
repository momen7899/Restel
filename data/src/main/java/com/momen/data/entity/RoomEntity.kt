package com.momen.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.momen.domain.model.Room

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "Room_Name")
    val roomName: String?,
    @ColumnInfo(name = "Room_Code")
    val roomCode: Int?,
    @ColumnInfo(name = "Customer_Name")
    val customer: String?,
    @ColumnInfo(name = "Start_Date")
    val startDate: String?,
    @ColumnInfo(name = "Finish_Date")
    val finishDate: String?,
    @ColumnInfo(name = "Price")
    val price: Int?
){
    constructor(roomName: String?,
                roomCode: Int?,
                customer: String?,
                startDate: String?,
                finishDate: String?,
                Price: Int):this(0,roomName,roomCode,customer,startDate,finishDate,Price)
}
