package com.example.learningkotlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.learningkotlin.MainActivity
import com.example.learningkotlin.R
import com.example.learningkotlin.databinding.FragmentHomeBinding
import com.google.android.material.textfield.TextInputLayout

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var editText: EditText
    private lateinit var loginButton: Button
    private lateinit var binding : FragmentHomeBinding
    private lateinit var userInputLayout: TextInputLayout
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(inflater, container,false)

        userInputLayout = binding.textInputLayout
        editText = binding.loginUserEditText
        loginButton = binding.loginButton

        loginButton.setOnClickListener {
            if(editText.text.isEmpty()){
                Toast.makeText(context,"Clicked Login Button",Toast.LENGTH_SHORT)
                userInputLayout.isErrorEnabled = true
                userInputLayout.error = "Username must not be empty"
            }
        }

        return binding.root
    }
}
