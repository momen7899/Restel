package com.momen.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "first_name")
    val firstName: String?,
    @ColumnInfo(name = "last_name")
    val lastName: String?,
    @ColumnInfo(name = "national_code")
    val nationalCode: String?,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String?,
    @ColumnInfo(name = "user_name")
    val userName: String?,
    @ColumnInfo(name = "password")
    val password: String?,
    @ColumnInfo(name = "md5")
    val md5: String?,
    @ColumnInfo(name = "address")
    val address: String?
)