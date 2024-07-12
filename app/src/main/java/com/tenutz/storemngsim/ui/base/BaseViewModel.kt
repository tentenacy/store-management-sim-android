package com.tenutz.storemngsim.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tenutz.storemngsim.utils.Event
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    private val _viewEvent = MutableLiveData<Event<Pair<Int, Any>>>()
    val viewEvent: LiveData<Event<Pair<Int, Any>>>
        get() = _viewEvent

    private val _loadingEvent = MutableLiveData<Event<Boolean>>()
    val loadingEvent: LiveData<Event<Boolean>>
        get() = _loadingEvent

    fun viewEvent(content: Pair<Int, Any>) {
        _viewEvent.postValue(Event(content))
    }

    fun loadingEvent(content: Boolean, immediate: Boolean = true) = viewModelScope.launch {
        if(content) {
            _loadingEvent.postValue(Event(true))
        } else if(!immediate) {
            delay(100)
            _loadingEvent.postValue(Event(false))
        } else {
            _loadingEvent.postValue(Event(false))
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}