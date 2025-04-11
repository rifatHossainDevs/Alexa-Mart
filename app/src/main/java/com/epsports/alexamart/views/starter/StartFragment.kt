package com.epsports.alexamart.views.starter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.epsports.alexamart.R
import com.epsports.alexamart.base.BaseFragment
import com.epsports.alexamart.databinding.FragmentStartBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {
@Inject
lateinit var rAuth: FirebaseAuth
    override fun setAllClickListener() {
        setAutoLogin()

        with(binding){
            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_loginFragment)
            }

            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_registrationFragment)
            }
        }
    }

    private fun setAutoLogin() {
        rAuth.currentUser?.let {
            findNavController().navigate(R.id.action_startFragment_to_dashboardFragment)
        }
    }

    override fun allObserver() {

    }

}