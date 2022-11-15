package com.example.kotcoroomldmvvm.repos

import com.example.kotcoroomldmvvm.db.Subscriber
import com.example.kotcoroomldmvvm.db.SubscriberDAO

class SubscriberRepository(private val dao : SubscriberDAO) {
    val subscribers = dao.getAllSubscriber()

    suspend fun insertSubscriber(subscriber: Subscriber): Long{
        return dao.insertSubscriber(subscriber)
    }

    suspend fun updateSubscriber(subscriber: Subscriber): Int{
        return dao.updateSubscriber(subscriber)
    }

    suspend fun deleteSubscriber(subscriber: Subscriber){
        dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAllSubscriber(): Int{
        return dao.deleteAllSubscribers()
    }
}