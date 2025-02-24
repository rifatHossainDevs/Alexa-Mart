package com.epsports.alexamart.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.epsports.alexamart.R
import com.epsports.alexamart.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        setAllClickListener()

        return binding.root
    }

    private fun setAllClickListener() {
        binding.btnLogin.setOnClickListener {
            allFieldValidityCheck()
            if (allFieldValidityCheck()){
                Toast.makeText(context, "All Input done!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
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
            binding.emailInputLayout.error = "Email does not matches"
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