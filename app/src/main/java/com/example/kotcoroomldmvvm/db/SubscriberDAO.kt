package com.example.kotcoroomldmvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {
    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber): Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber): Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query(value = "DELETE FROM subscriber_data_table")
    suspend fun deleteAllSubscribers(): Int

    @Query(value = "SELECT * FROM subscriber_data_table")
    fun getAllSubscriber(): LiveData<List<Subscriber>>

    @Query(value = "SELECT * FROM subscriber_data_table WHERE subscriber_id = :id")
    fun getSubscriberById(id: Int): LiveData<Subscriber>
}