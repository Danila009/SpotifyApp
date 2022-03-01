package com.example.spotifyfirebase.data

import com.example.spotifyfirebase.data.model.Music
import com.example.spotifyfirebase.utils.Constants.MUSIC_COLLECTION
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class MusicDatabase {

    private val firestore = Firebase.firestore
    private val music = firestore.collection(MUSIC_COLLECTION)

    suspend fun getAllMusic():List<Music>{
        return try {
            music.get().await().toObjects(Music::class.java)
        }catch (e:Exception){
            emptyList()
        }
    }
}