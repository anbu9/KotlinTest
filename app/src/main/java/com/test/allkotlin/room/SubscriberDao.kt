package com.test.allkotlin.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDao {

    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber) : Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber) :Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber) : Int

    @Query("delete from subscriber_data")
    suspend fun deleteAll()

    @Query("select * from subscriber_data")
    fun getAllSubscriber() : LiveData<List<Subscriber>>
}