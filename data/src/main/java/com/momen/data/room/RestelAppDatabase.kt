package com.momen.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.momen.data.entity.UserEntity


@Database(entities = [UserEntity::class], version = 1)
public abstract class RestelAppDatabase : RoomDatabase() {

    abstract fun getDatabaseDAO(): DatabaseDAO?

}