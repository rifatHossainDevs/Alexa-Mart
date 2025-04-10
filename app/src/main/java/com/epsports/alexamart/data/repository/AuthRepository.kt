package com.epsports.alexamart.data.repository

import com.epsports.alexamart.data.models.UserLogin
import com.epsports.alexamart.data.models.UserRegistration
import com.epsports.alexamart.services.AuthService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthRepository @Inject constructor(private val rAuth: FirebaseAuth) : AuthService {

    override fun userRegistration(user: UserRegistration): Task<AuthResult> {
        return rAuth.createUserWithEmailAndPassword(user.email, user.password)
    }

    override fun userLogin(user: UserLogin): Task<AuthResult> {
        return rAuth.signInWithEmailAndPassword(user.email, user.password)
    }
}