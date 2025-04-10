package com.epsports.alexamart.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epsports.alexamart.core.DataState
import com.epsports.alexamart.data.models.UserLogin
import com.epsports.alexamart.data.repository.AuthRepository
import com.epsports.alexamart.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authService: AuthRepository): ViewModel() {
    private val _loginResponse = MutableLiveData<DataState<UserLogin>>()

    val loginResponse : LiveData<DataState<UserLogin>> = _loginResponse

    fun userLogin(user: UserLogin){

        _loginResponse.postValue(DataState.Loading())
        authService.userLogin(user).addOnSuccessListener {
            _loginResponse.postValue(DataState.Success(user))
        }.addOnFailureListener {
            _loginResponse.postValue(DataState.Error("${it.message}"))
        }
    }
}