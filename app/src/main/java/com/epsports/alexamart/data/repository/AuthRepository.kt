package com.epsports.alexamart.data.repository

import com.epsports.alexamart.data.models.UserRegistration
import com.epsports.alexamart.services.AuthService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthRepository : AuthService {
    override fun userRegistration(user: UserRegistration): Task<AuthResult> {
        val rAuth = FirebaseAuth.getInstance()
        return rAuth.createUserWithEmailAndPassword(user.email, user.password)
    }
}