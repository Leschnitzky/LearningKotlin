package com.example.learningkotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.learningkotlin.R
import com.example.learningkotlin.data.repositories.FirebaseAuthRepository
import com.example.learningkotlin.data.repositories.FirestoreRepository
import com.example.learningkotlin.viewmodels.GalleryViewModel
import com.example.learningkotlin.viewmodels.UserLoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.weatherapp.util.UserLoginViewModelFactory

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    lateinit var userLoginViewModelFactory: ViewModelProvider.Factory
    private lateinit var userLoginViewModel: UserLoginViewModel

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
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        userLoginViewModel.updateCurrentUser()
        userLoginViewModel.currentUserFullData?.observe(viewLifecycleOwner, Observer {
            textView.text ="${it.firstName} ${it.lastName}"
        })
        return root
    }
}
