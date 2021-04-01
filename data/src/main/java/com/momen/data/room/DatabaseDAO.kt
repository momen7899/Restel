package com.momen.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.momen.data.entity.ReserveEntity
import com.momen.data.entity.UserEntity
import io.reactivex.Single

@Dao
interface DatabaseDAO {

    @Insert
    fun addUser(user: UserEntity): Single<Long>

    @Query("SELECT * FROM users WHERE user_name==:userName AND password ==:password AND md5==:md5")
    fun isValidUser(userName: String, password: String, md5: String): Single<UserEntity>

    @Query("SELECT * FROM users WHERE id==:id")
    fun getUser(id: Int): Single<UserEntity>

    @Query("SELECT * FROM users")
    fun getUsers(): Single<List<UserEntity>>

    @Insert
    fun addReserve(reserve: ReserveEntity): Single<Long>

    @Update
    fun updateReserve(reserve: ReserveEntity): Single<Int>

    @Query("SELECT * FROM reserve")
    fun getReserves(): Single<List<ReserveEntity>>
}