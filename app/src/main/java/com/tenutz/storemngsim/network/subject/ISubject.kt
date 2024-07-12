package com.tenutz.storemngsim.network.subject

interface ISubject<T> {
    fun registerObserver(observer: T)
    fun unregisterObserver(observer: T)
}