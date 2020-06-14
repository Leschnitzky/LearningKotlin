package com.example.learningkotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsSpinner
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.learningkotlin.R
import com.example.learningkotlin.data.model.ErrorEvent
import com.example.learningkotlin.data.model.User
import com.example.learningkotlin.data.model.retrofit.Summoner
import com.example.learningkotlin.data.repositories.FirebaseAuthRepository
import com.example.learningkotlin.data.repositories.FirestoreRepository

import com.example.learningkotlin.databinding.FragmentSignUpBinding
import com.example.learningkotlin.viewmodels.UserLoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.weatherapp.util.UserLoginViewModelFactory
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    private lateinit var firstName: TextInputLayout
    private lateinit var spinner: AbsSpinner
    private lateinit var summoner_name: TextInputLayout
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

        bindWidgets(inflater,container)
        setUILogic()

        return binding.root
    }

    private fun setUILogic() {
        submitButton.setOnClickListener {
            var valid = true
            arrayOf(firstName, lastName, password, passwordConfirm,summoner_name, email).forEach {
                valid = displayEmptyErrorIfNeeded(it) && valid
            }
            val emailString = getStringFromInputLayout(email)

            if(emailString != null && !userLoginViewModel.isValidEmail(emailString) && emailString.isNotEmpty()){
                email.isErrorEnabled = true
                email.error = resources.getString(R.string.badly_formatted_email)
                valid = false
            }

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

            val summonerString = getStringFromInputLayout(summoner_name)
            val regionString = spinner.selectedItem.toString()

            userLoginViewModel.setRegion(regionString)
            if(!summonerString.isNullOrEmpty()){
                userLoginViewModel.updateSummoner(summonerString)
            }


            if(valid){

                userLoginViewModel.summonerObservable!!.subscribeOn(Schedulers.io()).subscribe {
                        summoner: Summoner ->
                    if(summoner == null){

                    } else {
                        val firstNameString = getStringFromInputLayout(firstName)
                        val lastNameString = getStringFromInputLayout(lastName)
                        val emailString = getStringFromInputLayout(email)
                        val passwordString = getStringFromInputLayout(password)
                        val summonerNameString = getStringFromInputLayout(summoner_name)
                        val regionString = regionString
                        userLoginViewModel.signUpWithUserToFirebase(
                            User(
                                firstName = firstNameString!!,
                                lastName = lastNameString!!,
                                email = emailString!!,
                                password = passwordString!!,
                                summoner = summonerNameString!!,
                                region = regionString!!
                            )
                        )
                    }
                }


            }
            userLoginViewModel.createdUserLiveData?.observe(viewLifecycleOwner, Observer {
                    user ->
                userLoginViewModel.addUserDataToFirestore(user)
                userLoginViewModel.fullUserLiveData!!.observe(viewLifecycleOwner, Observer {
                        user ->
                    findNavController().navigate(R.id.action_signUpFragment_to_nav_gallery)
                })
            })
            userLoginViewModel.signUpError.observe(viewLifecycleOwner, Observer {
                if(it != ErrorEvent.NONE){
                    Snackbar.make(binding.root, resources.getString(R.string.error_user_already_exists), Snackbar.LENGTH_LONG).show()
                }
            })
        }
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


    private fun bindWidgets(inflater: LayoutInflater,container: ViewGroup?) {
        binding = FragmentSignUpBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = this;

        firstName = binding.signUpFirstName
        lastName = binding.signUpLastName
        password = binding.signUpPassword
        email = binding.signUpEmail
        summoner_name = binding.signUpSummonerName
        spinner = binding.regionSpinner
        passwordConfirm = binding.signUpConfirmPassword
        submitButton =  binding.signUpBtnConfirmation
    }


}
