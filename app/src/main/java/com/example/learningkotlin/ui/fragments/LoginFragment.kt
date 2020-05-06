package com.example.learningkotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.learningkotlin.R
import com.example.learningkotlin.data.model.ErrorEvent
import com.example.learningkotlin.data.model.User
import com.example.learningkotlin.databinding.FragmentLoginBinding
import com.example.learningkotlin.ui.viewmodels.UserLoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {

    private lateinit var userEditText: EditText
    private lateinit var passEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signupButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: FragmentLoginBinding
    private val userLoginViewModel: UserLoginViewModel by activityViewModels()

    private lateinit var userInputLayout: TextInputLayout
    private lateinit var passInputLayout: TextInputLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this;

        progressBar = binding.progressBar
        progressBar.visibility = View.INVISIBLE
        userInputLayout = binding.textInputLayout
        passInputLayout = binding.textInputLayout2
        userEditText = binding.loginMailEditText
        userEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                userInputLayout.isErrorEnabled = false
        }
        passEditText = binding.loginPassEditText
        passEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                passInputLayout.isErrorEnabled = false
        }
        loginButton = binding.loginButton

        loginButton.setOnClickListener {
            var valid = true
            val userString = userEditText.text.toString()
            val passString = passEditText.text.toString()
            if (userString.isEmpty()) {
                userInputLayout.isErrorEnabled = true
                userInputLayout.error = "Username must not be empty"
                valid = false
            }
            if (passString.isEmpty()) {
                passInputLayout.isErrorEnabled = true
                passInputLayout.error = "Password must not be empty"
                valid = false
            }
            if (valid) {
                userLoginViewModel.signInWithUserAndPass(userString, passString)
                userLoginViewModel.authenticatedUserLiveData?.observe(
                    viewLifecycleOwner,
                    Observer { user ->
                        if (user != null) {
                            updateLoggedInUser(user)
                        }
                    })
                userLoginViewModel.signInError.observe(viewLifecycleOwner,
                    Observer { error ->
                        if(error != null) {
                            displayError(error)
                        }
                    }
                )
            }

        }

        signupButton = binding.signUpButton
        signupButton.setOnClickListener {
            Thread.sleep(40)
            findNavController().navigate(R.id.action_nav_home_to_signUpFragment)
        }
        return binding.root
    }

    private fun displayError(error: ErrorEvent) {
        when(error) {
            ErrorEvent.NONE -> {}
            ErrorEvent.USERNAME_DOES_NOT_EXIST -> Snackbar.make(binding.root, "Login failed: "+ resources.getString(R.string.error_user_no_exist)  , Snackbar.LENGTH_LONG).show()
            ErrorEvent.WRONG_PASSWORD -> Snackbar.make(binding.root, "Login failed:  "+ resources.getString(R.string.error_wrong_password) , Snackbar.LENGTH_LONG).show()
        }
    }

    private fun updateLoggedInUser(currentUser: User?) {
        if (currentUser != null) {
            Snackbar.make(binding.root, "Welcome ${currentUser.firstName} ${currentUser.lastName}", Snackbar.LENGTH_LONG).show()
        }

    }
}
