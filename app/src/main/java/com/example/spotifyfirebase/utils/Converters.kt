package com.example.spotifyfirebase.utils

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

object Converters {
    fun <T>Flow<T>.launchWhenStarted(lifecycleScope: LifecycleCoroutineScope){
        lifecycleScope.launchWhenStarted {
            this@launchWhenStarted.collect()
        }
    }

    fun replaceRange(string: String, int: Int):String{
        if (string.length < int)
            return string
        return string.replaceRange(
            int until string.length,
            "..."
        )
    }

}