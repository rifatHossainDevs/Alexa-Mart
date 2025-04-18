package com.epsports.alexamart.services

import com.epsports.alexamart.data.models.UserLogin
import com.epsports.alexamart.data.models.UserRegistration
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthService {

    fun userRegistration(user: UserRegistration): Task<AuthResult>

    fun userLogin(user: UserLogin): Task<AuthResult>

    fun createUser(user: UserRegistration):  Task<Void>


}