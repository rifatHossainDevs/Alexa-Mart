package com.epsports.alexamart.views.login

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.epsports.alexamart.R
import com.epsports.alexamart.base.BaseFragment
import com.epsports.alexamart.core.DataState
import com.epsports.alexamart.dashboard.seller.SellerDashboard
import com.epsports.alexamart.data.models.UserLogin
import com.epsports.alexamart.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    val viewModel: LoginViewModel by viewModels()
    override fun setAllClickListener() {
        with (binding){
            btnLogin.setOnClickListener {
                allFieldValidityCheck()
                if (allFieldValidityCheck()) {
                    val user = UserLogin(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                    viewModel.userLogin(user)
                }
            }

            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        }

    }

    override fun allObserver() {
        loginObserver()
    }

    private fun loginObserver() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when(it){
                is DataState.Error -> {
                    loading.dismiss()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    loading.show()
                    Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                }
                is DataState.Success -> {
                    loading.dismiss()
                    Toast.makeText(context, "Login in Successful for ${it.data}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), SellerDashboard::class.java))
                    requireActivity().finish()
                }
            }
        }
    }

    private fun allFieldValidityCheck(): Boolean {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val emailPattern = "^[a-z0-9+_.-]+@[a-z.-]{4,7}\\.[a-z]{2,5}$"
        if (email == "") {
            binding.emailInputLayout.error = "This field must be filled"
            return false
        }
        if (!email.matches(emailPattern.toRegex())) {
            binding.emailInputLayout.error = "Email format does not match"
            return false
        }
        if (password == "") {
            binding.passwordInputLayout.error = "This field must be filled"
            return false
        }
        if (password.length < 8) {
            binding.passwordInputLayout.error = "Password must have at least 8 character"
            return false
        }
        return true
    }
}