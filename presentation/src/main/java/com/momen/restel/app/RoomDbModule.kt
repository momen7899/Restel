package com.momen.restel.app

import android.content.Context
import androidx.room.Room
import com.momen.data.room.RestelAppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomDbModule(private val context: Context) {

    @Provides
    fun provideAppDatabase(): RestelAppDatabase {
        return Room.databaseBuilder(
            context,
            RestelAppDatabase::class.java,
            "restel_db.db"
        ).build()
    }
}