package com.momen.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.momen.data.entity.CustomerEntity
import com.momen.data.entity.ReserveEntity
import com.momen.data.entity.RoomEntity
import com.momen.data.entity.UserEntity


@Database(
    entities = [UserEntity::class, CustomerEntity::class, RoomEntity::class, ReserveEntity::class],
    version = 1, exportSchema = false
)
public abstract class RestelAppDatabase : RoomDatabase() {

    abstract fun getDatabaseDAO(): DatabaseDAO?

}