package com.example.spotifyfirebase.screen.loginScreen.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.spotifyfirebase.api.model.user.Authorization
import com.example.spotifyfirebase.api.model.user.User
import com.example.spotifyfirebase.api.repository.ApiRepository
import com.example.spotifyfirebase.datastore.DatastoreRepository
import com.example.spotifyfirebase.navigation.navGraph.profileNavGraph.constants.ProfileRoute.Route.PROFILE_ROUTE
import com.example.spotifyfirebase.utils.Constants.TAG_DATE_STORE
import com.example.spotifyfirebase.utils.Constants.TAG_RETROFIT
import com.example.spotifyfirebase.utils.Constants.TOKEN_SHARED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val tokenPref: SharedPreferences,
    private val datastoreRepository: DatastoreRepository
):ViewModel() {
    private val _responseValidate:MutableStateFlow<String> = MutableStateFlow("")
    val responseValidate:StateFlow<String> = _responseValidate.asStateFlow()

    fun authorization(authorization: Authorization, navController: NavController){
        viewModelScope.launch {
            try {
                if (validateAuthorization(
                        email = authorization.email,
                        password = authorization.password
                )){
                    val response = apiRepository.authorization(
                        authorization = authorization
                    )
                    response.body()!!.toString()
                    if (response.isSuccessful){
                        tokenPref.edit()
                            .putString(
                                TOKEN_SHARED,
                                response.body()?.access_token
                            ).apply()
                        saveStatusRegistration(
                            userRegistration = true
                        )
                        delay(1000L)
                        navController.navigate(
                            PROFILE_ROUTE
                        )
                    }
                }
            }catch (e:Exception){
                Log.e(TAG_RETROFIT, e.message.toString())
            }
        }
    }

    fun registration(
        user: User,
        navController: NavController
    ){
        viewModelScope.launch {
            try {
                if (validateRegistration(
                        username = user.username,
                        email = user.email,
                        password = user.password
                )){
                    apiRepository.registration(
                        user = user
                    )

                    authorization(
                        authorization = Authorization(
                            email = user.email,
                            password = user.password
                        ),
                        navController = navController
                    )

                    saveStatusRegistration(
                        userRegistration = true
                    )
                }
            }catch (e:Exception){
                Log.e(TAG_RETROFIT, e.message.toString())
            }
        }
    }

    private fun validateAuthorization(
        email:String,
        password:String
    ):Boolean{

        if (email.isEmpty() || password.isEmpty()) {
            _responseValidate.value = "Заполгите все поля"
            return false
        }
        if (email.length < 6) {
            _responseValidate.value = "Email должен состоять из 6 или больше символов"
            return false
        }
        if (password.length < 6) {
            _responseValidate.value = "Password должен состоять из 6 или больше символов"
            return false
        }

        if (!email.any("." :: contains) || !email.any("@" :: contains)) {
            _responseValidate.value = "Некорректно введен Email"
            return false
        }
        _responseValidate.value = ""
        return true
    }

    private fun validateRegistration(
        username:String,
        email:String,
        password:String
    ):Boolean{

        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            _responseValidate.value = "Заполгите все поля"
            return false
        }
        if (email.length < 6) {
            _responseValidate.value = "Email должен состоять из 6 или больше символов"
            return false
        }
        if (password.length < 6) {
            _responseValidate.value = "Password должен состоять из 6 или больше символов"
            return false
        }

        if (username.length < 6){
            _responseValidate.value = "User должен состоять из 6 или больше символов"
            return false
        }

        if (!email.any("." :: contains) || !email.any("@" :: contains)) {
            _responseValidate.value = "Некорректно введен Email"
            return false
        }
        _responseValidate.value = ""
        return true
    }

    private fun saveStatusRegistration(userRegistration:Boolean){
        viewModelScope.launch {
            try {
                datastoreRepository.saveStatusRegistration(userRegistration = userRegistration)
            }catch (e:Exception){
                Log.e(TAG_DATE_STORE, e.message.toString())
            }
        }
    }
}