package com.momen.data.repository.user

import javax.inject.Inject

class UserDataSourceFactory  constructor(private val roomUserDatabase: RoomUserDatabase) {
    fun create(): UserDataSource = roomUserDatabase
}