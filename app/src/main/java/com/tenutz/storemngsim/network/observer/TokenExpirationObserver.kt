package com.tenutz.storemngsim.network.observer

interface TokenExpirationObserver {
    fun onTokenExpired()
    fun onRefreshTokenExpired()
}