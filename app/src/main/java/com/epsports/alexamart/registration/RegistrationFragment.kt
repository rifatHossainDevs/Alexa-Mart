package com.epsports.alexamart.registration

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.epsports.alexamart.R
import com.epsports.alexamart.databinding.FragmentRegistrationBinding
import com.google.android.material.button.MaterialButtonToggleGroup
import org.intellij.lang.annotations.Pattern


class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        setAllClickListener()


        return binding.root
    }

    private fun setAllClickListener() {
        binding.btnRegister.setOnClickListener {
            checkAllValidityCheck()
            if (checkAllValidityCheck()) {
                Toast.makeText(context, "All Input Done!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkAllValidityCheck(): Boolean {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val emailPattern = "^[a-z0-9+_.-]+@[a-z.-]{4,7}\\.[a-z]{2,5}$"
        val toggleButton: MaterialButtonToggleGroup = binding.toggleButton

        if (name == "") {
            binding.etNameLayout.error = "This field must be filled"
            return false
        }
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

        if (toggleButton.isEmpty()) {
            binding.apply {
                errorMessage.visibility = View.VISIBLE
                errorMessage.text = "Please Select an Option"
            }
            return false
        }
        return true
    }
}