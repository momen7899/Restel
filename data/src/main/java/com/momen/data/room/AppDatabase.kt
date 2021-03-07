package com.momen.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.momen.data.entity.UserEntity


@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDatabaseDAO(): DatabaseDAO?

}