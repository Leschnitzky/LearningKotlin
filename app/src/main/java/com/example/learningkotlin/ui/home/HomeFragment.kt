package com.example.learningkotlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.example.learningkotlin.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userEditText: EditText
    private lateinit var passEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signupButton: Button
    private lateinit var binding : FragmentHomeBinding

    private val model: HomeViewModel by activityViewModels()

    private lateinit var userInputLayout: TextInputLayout
    private lateinit var passInputLayout: TextInputLayout
    val USER_PATTERN = "^[_A-z0-9]*((-|)*[_A-z0-9])*\$".toRegex()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
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
            else if(!USER_PATTERN.containsMatchIn(userString)){
                userInputLayout.isErrorEnabled = true
                userInputLayout.error = "Illegal user name"
                valid = false
            }
            if (passString.isEmpty()){
                passInputLayout.isErrorEnabled = true
                passInputLayout.error = "Password must not be empty"
                valid = false
            }
            if (valid) {
                auth.signInWithEmailAndPassword(userString, passString).addOnCompleteListener{
                        task ->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        updateLoggedInUser(user)
                    } else {
                        Snackbar.make(binding.root,"Login Failed",Snackbar.LENGTH_LONG).show()
                    }

                }
            }

        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateLoggedInUser(currentUser)
    }

    private fun updateLoggedInUser(currentUser: FirebaseUser?) {
        if(currentUser != null){
            Snackbar.make(binding.root,"Welcome "+currentUser.displayName,Snackbar.LENGTH_LONG).show()

        }

    }
}
