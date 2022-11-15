package com.example.kotcoroomldmvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDataBase : RoomDatabase() {

    abstract val subscriberDAO : SubscriberDAO

    // Singleton
    companion object{
        @Volatile
        private var INSTANCE : SubscriberDataBase? = null
        fun getInstance(context: Context):SubscriberDataBase{
            synchronized(lock = this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SubscriberDataBase::class.java,
                        "subscriber_database"
                    ).build()
                }
                return instance
            }
        }
    }
}

