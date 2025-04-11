package com.epsports.alexamart.views.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epsports.alexamart.core.DataState
import com.epsports.alexamart.data.models.UserRegistration
import com.epsports.alexamart.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val authService: AuthRepository) :
    ViewModel() {

    private val _registrationResponse = MutableLiveData<DataState<UserRegistration>>()

    val registrationResponse: LiveData<DataState<UserRegistration>> = _registrationResponse

    fun userRegistration(user: UserRegistration) {
        _registrationResponse.postValue(DataState.Loading())

        authService.userRegistration(user).addOnSuccessListener {
            it.user?.let {createdUser->
                user.userId = createdUser.uid
                authService.createUser(user).addOnSuccessListener {
                    _registrationResponse.postValue(DataState.Success(user))
                }.addOnFailureListener {
                    _registrationResponse.postValue(DataState.Error("${it.message}"))
                }
            }
        }.addOnFailureListener {
            _registrationResponse.postValue(DataState.Error("${it.message}"))
        }
    }

}