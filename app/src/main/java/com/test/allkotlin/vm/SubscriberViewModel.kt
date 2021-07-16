package com.test.allkotlin.vm

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.allkotlin.Event
import com.test.allkotlin.repository.SubscriberRepository
import com.test.allkotlin.room.Subscriber
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel(),Observable {

    val subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete : Subscriber

    @Bindable
    val inputName = MutableLiveData<String?>()
    @Bindable
    val inputMobile = MutableLiveData<String?>()

    val items = arrayListOf("Select Book", "Book A", "Book B", "Book C")

    val itemPosition = MutableLiveData<Int>()

    private var selectItem: String? = null
        get() = itemPosition.value?.let {
            items.get(it)
        }

    set(value) {field = selectItem}

    @Bindable
    val saveOrupdateButton = MutableLiveData<String>()
    @Bindable
    val clearButton = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrupdateButton.value = "Save"
        clearButton.value = "Clear All"
    }

    fun saveOrUpdate(){

        if (inputName.value == null){
            statusMessage.value = Event("name should not blank")
        }else if (inputMobile.value == null){
            statusMessage.value = Event("mobile no should not blank")
        }else if (inputMobile.value!!.length != 10){
            statusMessage.value = Event("enter correct mobile no")
        }else if (selectItem.equals("Select Book")){
            statusMessage.value = Event("select book")
        }else{
            if (isUpdateOrDelete){
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.mobile = inputMobile.value!!
                subscriberToUpdateOrDelete.book = selectItem!!
                update(subscriberToUpdateOrDelete)
            }else {

                val name = inputName.value!!
                val mobile = inputMobile.value!!
                val book = selectItem!!

                insert(Subscriber(0, name, mobile, book))
                inputName.value = null
                inputMobile.value = null
                selectItem = "Select Book"
            }
        }
    }

    fun clearOrDelete(){
        if (isUpdateOrDelete){
            delete(subscriberToUpdateOrDelete)
        }else{
            clearAll()
        }
    }

    fun insert(subscriber: Subscriber) = viewModelScope.launch {
        val newRowId = repository.insert(subscriber)
        if (newRowId > -1) {
            statusMessage.value = Event("Subscriber inserted successfully $newRowId")
        }else{
            statusMessage.value = Event("Error occurred")
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber)
    {
        inputName.value = subscriber.name
        inputMobile.value = subscriber.mobile
        selectItem = subscriber.book
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrupdateButton.value = "Update"
        clearButton.value = "Delete"

    }

    fun update(subscriber: Subscriber) = viewModelScope.launch {
        val updateRow = repository.update(subscriber)
        if (updateRow > 0) {
            inputName.value = null
            inputMobile.value = null
            selectItem = "Select Book"
            isUpdateOrDelete = false
            subscriberToUpdateOrDelete = subscriber
            saveOrupdateButton.value = "Save"
            clearButton.value = "Clear All"
            statusMessage.value = Event("$updateRow Subscriber updated successfully")
        }else{
            statusMessage.value = Event("Error occurred")
        }
    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch {
        repository.delete(subscriber)
        inputName.value = null
        inputMobile.value = null
        selectItem = "Select Book"
        isUpdateOrDelete = false
        subscriberToUpdateOrDelete = subscriber
        saveOrupdateButton.value = "Save"
        clearButton.value = "Clear All"
        statusMessage.value = Event("Subscriber deleted successfully")
    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
        statusMessage.value = Event("All Subscriber deleted successfully")
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}