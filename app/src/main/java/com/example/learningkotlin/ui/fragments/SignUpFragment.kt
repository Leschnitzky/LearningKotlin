package com.example.learningkotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.learningkotlin.R
import com.example.learningkotlin.data.model.User
import com.example.learningkotlin.data.repositories.FirebaseAuthRepository
import com.example.learningkotlin.data.repositories.FirestoreRepository

import com.example.learningkotlin.databinding.FragmentSignUpBinding
import com.example.learningkotlin.ui.viewmodels.UserLoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.weatherapp.util.UserLoginViewModelFactory

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
    lateinit var userLoginViewModelFactory: ViewModelProvider.Factory
    private lateinit var userLoginViewModel: UserLoginViewModel
    private lateinit var binding : FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userLoginViewModelFactory = UserLoginViewModelFactory(
            FirestoreRepository.getInstance(Firebase.firestore),
            FirebaseAuthRepository.getInstance(FirebaseAuth.getInstance())
        )
        userLoginViewModel = ViewModelProvider(this, userLoginViewModelFactory)[UserLoginViewModel::class.java]
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
                valid = displayEmptyErrorIfNeeded(it) && valid
            }
            val emailString = getStringFromInputLayout(email)

            if(emailString != null && !userLoginViewModel.isValidEmail(emailString) && emailString.isNotEmpty()){
                email.isErrorEnabled = true
                email.error = resources.getString(R.string.badly_formatted_email)
                valid = false
            }

//            userLoginViewModel.isEmailInDB(emailString)) {
//                email.isErrorEnabled = true
//                email.error = "A User with the same email address already exists"
//                valid = false
//            }

            val passwordString = getStringFromInputLayout(password)
            val passwordConfirmString = getStringFromInputLayout(passwordConfirm)

            if(!userLoginViewModel.isValidPassword(passwordString!!) && !passwordString.isNullOrEmpty()){
                password.isErrorEnabled = true
                password.error = resources.getString(R.string.error_password_weak)
                valid = false
            }

            if(userLoginViewModel.isValidPassword(passwordString) && passwordConfirmString != passwordString && passwordConfirmString!!.isNotEmpty()){
                password.isErrorEnabled = true
                password.error = resources.getString(R.string.error_password_must_match)
                valid = false
            }

            if(valid){
                val firstNameString = getStringFromInputLayout(firstName)
                val lastNameString = getStringFromInputLayout(lastName)
                val emailString = getStringFromInputLayout(email)
                val passwordString = getStringFromInputLayout(password)
                userLoginViewModel.signUpWithUserToFirebase(
                    User(
                        firstName = firstNameString!!,
                        lastName = lastNameString!!,
                        email = emailString!!,
                        password = passwordString!!
                    )
                )
            }
            userLoginViewModel.createdUserLiveData?.observe(viewLifecycleOwner, Observer {
                    user ->
                userLoginViewModel.addUserDataToFirestore(user)
                userLoginViewModel.fullUserLiveData!!.observe(viewLifecycleOwner, Observer {
                        user ->
                    Snackbar.make(binding.root, "ADDED TO FIRESTORE ${user.firstName} ${user.lastName}", Snackbar.LENGTH_LONG).show()
                })
            })
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
            textInputLayout.error = resources.getString(R.string.error_empty_field)
            isValid = false
        }
        return isValid
    }


}
