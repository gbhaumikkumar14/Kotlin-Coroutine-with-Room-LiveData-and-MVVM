package com.example.kotcoroomldmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotcoroomldmvvm.Utils.Event
import com.example.kotcoroomldmvvm.db.Subscriber
import com.example.kotcoroomldmvvm.repos.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewModel(private val repo: SubscriberRepository): ViewModel() {
    val subscribers = repo.subscribers

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val btnSave = MutableLiveData<String>()
    val btnDeleteAll = MutableLiveData<String>()
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    private val statusMessage = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>> get() = statusMessage

    init {
        updateButtonNames()
    }

    private fun clearFields(){
        inputName.value = ""
        inputEmail.value = ""
    }

    private fun updateButtonNames(){
        if(isUpdateOrDelete){
            btnSave.value = "Update"
            btnDeleteAll.value = "Delete"
        }else{
            btnSave.value = "Save"
            btnDeleteAll.value = "Clear all"
        }
    }

    fun saveOrUpdate(){
        if(isUpdateOrDelete){
            isUpdateOrDelete = false
            updateSubscriber(
                Subscriber(
                    id = subscriberToUpdateOrDelete.id,
                    name = inputName.value!!,
                    email = inputEmail.value!!
                )
            )
            clearFields()
            updateButtonNames()
        }else {
            insertSubscriber(
                Subscriber(
                    id = 0,
                    name = inputName.value!!,
                    email = inputEmail.value!!
                )
            )
            clearFields()
        }
    }

    fun clearAllOrDelete(){
        if(isUpdateOrDelete){
            isUpdateOrDelete = false
            clearFields()
            deleteSubscriber(subscriberToUpdateOrDelete)
            updateButtonNames()
        }else{
            clearAll()
        }
    }

    private fun insertSubscriber(subscriber:Subscriber) = viewModelScope.launch(Dispatchers.IO) {
            val result = repo.insertSubscriber(subscriber)
            Log.d("ViewModel", "insertSubscriber: rowId: $result")
            if(result > -1) {
                withContext(Dispatchers.Main) {
                    statusMessage.value = Event("Inserted Id $result !")
                }
            }
    }

    private fun updateSubscriber(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        val result = repo.updateSubscriber(subscriber)
        if(result > 0) {
            withContext(Dispatchers.Main) {
                statusMessage.value = Event("Updated $result record!")
            }
        }
    }

    private fun deleteSubscriber(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteSubscriber(subscriber)
        withContext(Dispatchers.Main){
            statusMessage.value = Event("Deleted !")
        }
    }

    private fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        val result = repo.deleteAllSubscriber()
        if(result > 0) {
            withContext(Dispatchers.Main) {
                statusMessage.value = Event("Deleted all $result records !")
            }
        }else{
            withContext(Dispatchers.Main) {
                statusMessage.value = Event("Nothing to delete !")
            }
        }
    }

    fun initUpdateOrDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        updateButtonNames()
    }
}