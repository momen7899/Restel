package com.momen.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.momen.data.entity.UserEntity
import io.reactivex.Single

@Dao
interface DatabaseDAO {

    @Insert
    fun addContact(user: UserEntity): Single<Long>

    @Query("SELECT * FROM users WHERE user_name==:userName AND password ==:password AND md5==:md5")
    fun isValidUser(userName: String, password: String, md5: String): Single<UserEntity>

    @Query("SELECT * FROM users WHERE id==:id")
    fun getUser(id: Int): Single<UserEntity>

    @Insert
    fun fakeAddContact(user: UserEntity): Long

    @Query("SELECT * FROM users")
    fun getUsers(): Single<List<UserEntity>>

}