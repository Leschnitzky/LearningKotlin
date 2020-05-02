package com.example.learningkotlin.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.learningkotlin.data.User

import com.example.learningkotlin.databinding.FragmentSignUpBinding
import com.google.android.material.textfield.TextInputLayout

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    private lateinit var firstName: TextInputLayout
    private lateinit var lastName: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var passwordConfirm: TextInputLayout
    private lateinit var submitButton: Button
    private val userLoginViewModel: UserLoginViewModel by activityViewModels()
    private lateinit var binding : FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = this;

        firstName = binding.signUpFirstName
        lastName = binding.signUpLastName
        password = binding.signUpPassword
        email = binding.signUpEmail
        passwordConfirm = binding.signUpConfirmPassword


        submitButton =  binding.signUpBtnConfirmation

        submitButton.setOnClickListener {
            var valid = true
            arrayOf(firstName, lastName, password, passwordConfirm, email).forEach {
                valid = displayEmptyErrorIfNeeded(it)
            }
            val emailString = getStringFromInputLayout(email)

            if (userLoginViewModel.isEmailInDB(emailString)) {
                email.isErrorEnabled = true
                email.error = "A User with the same email address already exists"
                valid = false
            }

            val passwordString = getStringFromInputLayout(password)
            val passwordConfirmString = getStringFromInputLayout(passwordConfirm)

            if(passwordConfirmString != passwordString){
                password.isErrorEnabled = true
                password.error = "Passwords must match"
                valid = false
            }

            if(valid){
                val firstNameString = getStringFromInputLayout(firstName)
                val lastNameString = getStringFromInputLayout(lastName)
                val emailString = getStringFromInputLayout(email)
                val passwordString = getStringFromInputLayout(password)
                userLoginViewModel.signUpWithUser(User(
                    firstName = firstNameString!!,
                    lastName = lastNameString!!,
                    email = emailString!!,
                    password = passwordString!!
                ))
            }
        }
        return binding.root
    }


    private fun getStringFromInputLayout(textInputLayout: TextInputLayout) : String?{
        return textInputLayout.editText?.text?.toString()
    }

    private fun displayEmptyErrorIfNeeded(textInputLayout: TextInputLayout): Boolean {
        var isValid = true
        val textInputString = getStringFromInputLayout(textInputLayout)
        textInputLayout.editText?.setOnFocusChangeListener {
                _, hasFocus ->
            if(hasFocus)
                textInputLayout.isErrorEnabled = false
        }

        if(textInputString.isNullOrEmpty()){
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = "The following box is empty"
            isValid = false
        }
        return isValid
    }


}
