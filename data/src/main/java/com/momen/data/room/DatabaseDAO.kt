package com.momen.data.room

import androidx.room.*
import com.momen.data.entity.ReserveEntity
import com.momen.data.entity.UserEntity
import io.reactivex.Single

@Dao
interface DatabaseDAO {

    // user
    @Insert
    fun addUser(user: UserEntity): Single<Long>

    @Update
    fun editUser(userEntity: UserEntity): Single<UserEntity>?

    @Query("SELECT * FROM users WHERE user_name==:userName AND password ==:password AND md5==:md5")
    fun isValidUser(userName: String, password: String, md5: String): Single<UserEntity>

    @Query("SELECT * FROM users WHERE id==:id")
    fun getUser(id: Int): Single<UserEntity>

    @Query("SELECT * FROM users")
    fun getUsers(): Single<List<UserEntity>>

    @Delete
    fun removeUser(id: Int): Single<Int>?

    // customer

    // room


    // reserve
    @Insert
    fun addReserve(reserve: ReserveEntity): Single<Long>

    @Update
    fun updateReserve(reserve: ReserveEntity): Single<Int>

    @Query("SELECT * FROM reserve")
    fun getReserves(): Single<List<ReserveEntity>>
}