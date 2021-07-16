package com.test.allkotlin.repository

import com.test.allkotlin.room.Subscriber
import com.test.allkotlin.room.SubscriberDao

class SubscriberRepository(private val dao: SubscriberDao) {

    val subscribers = dao.getAllSubscriber()

    suspend fun insert(subscriber: Subscriber) : Long{
       return dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber) : Int{
        return dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber) : Int{
        return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }

}