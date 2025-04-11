package com.epsports.alexamart.dashboard

import androidx.navigation.fragment.findNavController
import com.epsports.alexamart.R
import com.epsports.alexamart.base.BaseFragment
import com.epsports.alexamart.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {
    @Inject
    lateinit var rAuth: FirebaseAuth
    override fun setAllClickListener() {
        with(binding) {
            btnLogout.setOnClickListener {
                rAuth.signOut()
                findNavController().navigate(R.id.action_dashboardFragment_to_startFragment)
            }
        }
    }

    override fun allObserver() {

    }


}