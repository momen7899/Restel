package com.momen.data.room

import android.content.Context
import androidx.room.Room

class RoomInstance constructor(context: Context) {

    private val instance: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "restel_db.db"
    ).allowMainThreadQueries().build()

    fun getInstance(): AppDatabase {
        return instance
    }

}