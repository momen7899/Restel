package com.momen.data.repository.datasource.room

import com.momen.data.repository.datasource.user.UserDataSource
import javax.inject.Inject

class RoomDataSourceFactory @Inject constructor(private val remoteRoomDataBase: RemoteRoomDataBase) {
    fun create(): RoomDataSource = remoteRoomDataBase
}