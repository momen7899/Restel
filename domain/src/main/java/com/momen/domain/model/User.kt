package com.momen.domain.model

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val nationalCode: String,
    val phoneNumber: String,
    val userName: String,
    val password: String,
    val md5: String,
    val address: String,
    val admin: Int
)