package com.example.learningkotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learningkotlin.data.repositories.FirebaseAuthRepository
import com.example.learningkotlin.data.model.ErrorEvent
import com.example.learningkotlin.data.repositories.FirestoreRepository
import com.example.learningkotlin.data.model.User
import com.example.learningkotlin.data.model.retrofit.Summoner
import com.example.learningkotlin.data.repositories.RetrofitRepository
import com.google.firebase.auth.GoogleAuthCredential
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class UserLoginViewModel constructor(val authRepository: FirebaseAuthRepository, val firestoreRepository: FirestoreRepository): ViewModel() {

    var fullUserLiveData: LiveData<User>? = null
    var currentUserFullData: LiveData<User>? = null
    var summonerObservable: Observable<Summoner>? = null
    val signInError: MutableLiveData<ErrorEvent> = MutableLiveData()
    val EMAIL_PATTERN = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex()
    val PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$".toRegex()
    var authenticatedUserLiveData: LiveData<User>? = null
    var createdUserLiveData: LiveData<User>? = null
    val signUpError: MutableLiveData<ErrorEvent> = MutableLiveData()
    var emailIsInDB: LiveData<Boolean>? = null
    var retrofitRepository: RetrofitRepository? = null


    fun isValidEmail(emailString: String): Boolean {
        return EMAIL_PATTERN.containsMatchIn(emailString)
    }

    fun isValidPassword(passwordString: String): Boolean {
        return PASSWORD_PATTERN.containsMatchIn(passwordString)
    }

    fun signInWithGoogleCredentials(googleAuthCredential : GoogleAuthCredential) {
        authenticatedUserLiveData = authRepository.signInWithGoogleCredentials(googleAuthCredential);
    }

    fun signInWithUserAndPass(user : String, pass: String) {
        authenticatedUserLiveData = authRepository.signInWithGoogleWithUserAndPass(user,pass,signInError);
    }

    //Todo: check how to look for in Firebase
    fun isEmailInDB(emailString: String): LiveData<Boolean> {
        emailIsInDB = firestoreRepository.checkEmailInDB(emailString)
        return emailIsInDB!!
    }

    fun signUpWithUserToFirebase(user: User) {
        createdUserLiveData = authRepository.createUser(user,signUpError)
    }

    fun updateCurrentUser(){
        val user = authRepository.getCurrentUser()
        currentUserFullData = firestoreRepository.getUserData(user)
    }

    fun addUserDataToFirestore(user: User) {
        fullUserLiveData = firestoreRepository.addUser(user)
    }

    fun setRegion(regionString: String){
        //TODO: Add urls by region
        val retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create())
            .baseUrl("https://euw1.api.riotgames.com/lol/")
            .build()

        retrofitRepository = RetrofitRepository(retrofit)
    }

    fun updateSummoner(summonerString: String?) {
        summonerObservable = retrofitRepository!!.getSummoner(summonerString)

    }


}