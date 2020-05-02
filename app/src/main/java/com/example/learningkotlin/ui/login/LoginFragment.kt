package com.example.learningkotlin.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.learningkotlin.R
import com.example.learningkotlin.data.User
import com.example.learningkotlin.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.sign


class LoginFragment : Fragment() {

    private lateinit var userEditText: EditText
    private lateinit var passEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signupButton: Button
    private lateinit var binding : FragmentHomeBinding
    private val userLoginViewModel: UserLoginViewModel by activityViewModels()

    private lateinit var userInputLayout: TextInputLayout
    private lateinit var passInputLayout: TextInputLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = this;

        userInputLayout = binding.textInputLayout
        passInputLayout = binding.textInputLayout2
        userEditText = binding.loginUserEditText
        userEditText.setOnFocusChangeListener {
                _, hasFocus ->
            if(hasFocus)
                userInputLayout.isErrorEnabled = false
        }
        passEditText = binding.loginPassEditText
        passEditText.setOnFocusChangeListener {
                _, hasFocus ->
            if(hasFocus)
                passInputLayout.isErrorEnabled = false
        }
        loginButton = binding.loginButton

        loginButton.setOnClickListener {
            var valid = true
            val userString = userEditText.text.toString()
            val passString = passEditText.text.toString()
            if(userString.isEmpty()){
                userInputLayout.isErrorEnabled = true
                userInputLayout.error = "Username must not be empty"
                valid = false
            }
            if (passString.isEmpty()){
                passInputLayout.isErrorEnabled = true
                passInputLayout.error = "Password must not be empty"
                valid = false
            }
            if (valid) {
                userLoginViewModel.signInWithUserAndPass(userString,passString)
                userLoginViewModel.authenticatedUserLiveData?.observe(viewLifecycleOwner, Observer {
                    user ->
                    if (user != null) {
                        updateLoggedInUser(user)
                    }
                } )
            }

        }

        signupButton = binding.signUpButton
        signupButton.setOnClickListener {
            Thread.sleep(40)
            findNavController().navigate(R.id.action_nav_home_to_signUpFragment)
        }
        return binding.root
    }

    private fun updateLoggedInUser(currentUser: User?) {
        if(currentUser != null){
            Snackbar.make(binding.root,"Welcome "+currentUser.email,Snackbar.LENGTH_LONG).show()
        }

    }
}
