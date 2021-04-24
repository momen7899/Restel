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
    val address: String?,
    @ColumnInfo(name = "admin")
    val admin: Int?
){
    constructor( firstName: String?, lastName: String?, nationalCode: String?, phoneNumber: String?, userName: String?, password: String?, md5: String?, address: String?, admin: Int?) : this(0,firstName , lastName ,
                                            nationalCode , phoneNumber , userName , password , md5 , address , admin )
}