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
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.learningkotlin.R
import com.example.learningkotlin.data.model.ErrorEvent
import com.example.learningkotlin.data.model.User
import com.example.learningkotlin.databinding.FragmentLoginBinding
import com.example.learningkotlin.ui.viewmodels.GalleryViewModel
import com.example.learningkotlin.ui.viewmodels.UserLoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signupButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: FragmentLoginBinding
    private lateinit var userLoginViewModel: UserLoginViewModel

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passInputLayout: TextInputLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userLoginViewModel = ViewModelProviders.of(this).get(UserLoginViewModel::class.java)
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this;
        progressBar = binding.progressBar
        progressBar.visibility = View.INVISIBLE
        emailInputLayout = binding.textInputLayout
        passInputLayout = binding.textInputLayout2
        emailEditText = binding.loginMailEditText
        emailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                emailInputLayout.isErrorEnabled = false
        }
        passEditText = binding.loginPassEditText
        passEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                passInputLayout.isErrorEnabled = false
        }
        loginButton = binding.loginButton

        loginButton.setOnClickListener {
            var valid = true
            val emailString = emailEditText.text.toString()
            val passString = passEditText.text.toString()
            if (emailString.isEmpty()) {
                emailInputLayout.isErrorEnabled = true
                emailInputLayout.error = resources.getString(R.string.empty_email_error)
                valid = false
            }
            if (passString.isEmpty()) {
                passInputLayout.isErrorEnabled = true
                passInputLayout.error = resources.getString(R.string.empty_password_error)
                valid = false
            }

            if(!userLoginViewModel.isValidEmail(emailString)){
                // Dont Overlap errors
                if(!emailInputLayout.isErrorEnabled) {
                    emailInputLayout.isErrorEnabled = true
                    emailInputLayout.error = resources.getString(R.string.badly_formatted_email)
                }
                valid = false
            }
            if (valid) {
                userLoginViewModel.signInWithUserAndPass(emailString, passString)
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
