package com.epsports.alexamart.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epsports.alexamart.core.DataState
import com.epsports.alexamart.data.models.UserRegistration
import com.epsports.alexamart.data.repository.AuthRepository

class RegistrationViewModel: ViewModel() {

    private val _registrationResponse = MutableLiveData<DataState<UserRegistration>>()

    val registrationResponse: LiveData<DataState<UserRegistration>> = _registrationResponse

    fun userRegistration(user: UserRegistration) {
        val authService = AuthRepository()
        _registrationResponse.postValue(DataState.Loading())

        authService.userRegistration(user).addOnSuccessListener {
            _registrationResponse.postValue(DataState.Success(user))
        }.addOnFailureListener {
            _registrationResponse.postValue(DataState.Error("${it.message}"))
        }
    }

}