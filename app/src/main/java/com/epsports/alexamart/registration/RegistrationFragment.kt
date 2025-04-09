package com.epsports.alexamart.registration

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.epsports.alexamart.base.BaseFragment
import com.epsports.alexamart.core.DataState
import com.epsports.alexamart.data.models.UserRegistration
import com.epsports.alexamart.databinding.FragmentRegistrationBinding
import com.google.android.material.button.MaterialButtonToggleGroup


class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate) {

    private val viewModel: RegistrationViewModel by viewModels()

    override fun setAllClickListener() {
        binding.btnRegister.setOnClickListener {
            checkAllValidityCheck()
            if (checkAllValidityCheck()) {
                val user = UserRegistration(
                    binding.etName.text.toString(),
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString(),
                    "seller",
                    ""
                )
                viewModel.userRegistration(user)

                //findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
        }
    }

    override fun allObserver() {
        registrationObserver()
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
        if (toggleButton.checkedButtonIds.isEmpty()) {
            binding.apply {
                errorMessage.visibility = View.VISIBLE
                errorMessage.text = "Please Select an Option"
            }
            return false
        }
        return true
    }

    private fun registrationObserver() {
        viewModel.registrationResponse.observe(viewLifecycleOwner) {
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
                    Toast.makeText(context, "user created successfully${it.data}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}