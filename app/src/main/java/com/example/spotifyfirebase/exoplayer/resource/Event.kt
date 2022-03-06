package com.example.spotifyfirebase.exoplayer.resource

open class Event<out T>(
    private val data:T
) {
    var hasBeenHandled = false
        private set

    fun getContentNotHandled():T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            data
        }
    }

    fun peekContent() = data
}