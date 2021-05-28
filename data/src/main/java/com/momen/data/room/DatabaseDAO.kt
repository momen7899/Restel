package com.momen.data.room

import androidx.room.*
import com.momen.data.entity.CustomerEntity
import com.momen.data.entity.ReserveEntity
import com.momen.data.entity.RoomEntity
import com.momen.data.entity.UserEntity
import io.reactivex.Single

@Dao
interface DatabaseDAO {

    // user
    @Insert
    fun addUser(user: UserEntity): Single<Long>?

    @Update
    fun editUser(userEntity: UserEntity): Single<Int>?

    @Query("SELECT * FROM users WHERE user_name==:userName AND password ==:password AND md5==:md5")
    fun isValidUser(userName: String, password: String, md5: String): Single<UserEntity>?

    @Query("SELECT * FROM users WHERE id==:id")
    fun getUser(id: Int): Single<UserEntity>?

    @Query("SELECT * FROM users")
    fun getUsers(): Single<List<UserEntity>>?

    @Delete
    fun removeUser(userEntity: UserEntity): Single<Int>?

    // customer
    @Insert
    fun addCustomer(customerEntity: CustomerEntity): Single<Long>?

    @Update
    fun editCustomer(customerEntity: CustomerEntity): Single<Int>?

    @Query("SELECT * FROM customers WHERE id==:id")
    fun getCustomer(id: Int): Single<CustomerEntity>?

    @Query("SELECT * FROM customers")
    fun getCustomers(): Single<List<CustomerEntity>>?

    @Delete
    fun removeCustomer(customerEntity: CustomerEntity): Single<Int>?

    // room
    @Insert
    fun addRoom(roomEntity: RoomEntity): Single<Long>?

    @Update
    fun editRoom(roomEntity: RoomEntity): Single<Int>?

    @Query("SELECT * FROM rooms WHERE id==:id")
    fun getRoom(id: Int): Single<RoomEntity>?

    @Query("SELECT * FROM rooms")
    fun getRooms(): Single<List<RoomEntity>>?

    @Delete
    fun removeRoom(roomEntity: RoomEntity): Single<Int>?

    // reserve
    @Insert
    fun addReserve(reserve: ReserveEntity): Single<Long>?

    @Update
    fun updateReserve(reserve: ReserveEntity): Single<Int>?

    @Query("SELECT * FROM reserve")
    fun getReserves(): Single<List<ReserveEntity>>?

    @Delete
    fun removeReserve(reserveEntity: ReserveEntity): Single<Int>?

}