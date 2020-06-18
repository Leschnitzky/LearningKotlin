package com.example.learningkotlin.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.learningkotlin.R
import com.example.learningkotlin.data.model.ErrorEvent
import com.example.learningkotlin.data.model.User
import com.example.learningkotlin.data.repositories.FirebaseAuthRepository
import com.example.learningkotlin.data.repositories.FirestoreRepository
import com.example.learningkotlin.databinding.FragmentLoginBinding
import com.example.learningkotlin.viewmodels.UserLoginViewModel
import com.weatherapp.util.UserLoginViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.android.support.DaggerFragment


class LoginFragment : DaggerFragment() {

    private lateinit var emailEditText: EditText
    private lateinit var alertDialog: AlertDialog
    private lateinit var passEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signupButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: FragmentLoginBinding
    lateinit var userLoginViewModelFactory: ViewModelProvider.Factory
    private lateinit var userLoginViewModel: UserLoginViewModel

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passInputLayout: TextInputLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userLoginViewModelFactory = UserLoginViewModelFactory(
            FirestoreRepository.getInstance(Firebase.firestore),
            FirebaseAuthRepository.getInstance(FirebaseAuth.getInstance())
        )
        userLoginViewModel = ViewModelProvider(this, userLoginViewModelFactory)[UserLoginViewModel::class.java]
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this;
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

            alertDialog = AlertDialog.Builder(requireContext())
                .setTitle("Loading")
                .setCancelable(false)
                .setView(layoutInflater.inflate(R.layout.process_dialog,null))
                .create()

            alertDialog.show()
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
                            alertDialog.dismiss()
                            findNavController().navigate(R.id.action_nav_home_to_nav_gallery)
                        }
                    })
                userLoginViewModel.signInError.observe(viewLifecycleOwner,
                    Observer { error ->
                        alertDialog.dismiss()
                        if(error != null) {
                            displayError(error)
                        }
                    }

                )
            } else {
                alertDialog.dismiss()
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
