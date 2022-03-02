package com.example.spotifyfirebase.screen.profileScreen.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyfirebase.api.model.user.User
import com.example.spotifyfirebase.api.repository.ApiRepository
import com.example.spotifyfirebase.datastore.DatastoreRepository
import com.example.spotifyfirebase.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val datastoreRepository: DatastoreRepository
):ViewModel() {
    private val _responseUserInfo: MutableStateFlow<User> = MutableStateFlow(User())
    val responseUserInfo: StateFlow<User> = _responseUserInfo.asStateFlow()

    private val _responseStatusRegistration:MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val responseStatusRegistration:StateFlow<Boolean?> = _responseStatusRegistration.asStateFlow()

    fun getUserInfo(){
        viewModelScope.launch {
            try {
                _responseUserInfo.value = apiRepository.getUserInfo().body()!!
            }catch (e: Exception){
                Log.e(Constants.TAG_RETROFIT, e.message.toString())
            }
        }
    }

    fun readStatusRegistration(){
        viewModelScope.launch {
            try {
                datastoreRepository.readStatusRegistration().collect {
                    _responseStatusRegistration.value = it
                }
            }catch (e:Exception){
                Log.e(Constants.TAG_DATE_STORE, e.message.toString())
            }
        }
    }
}