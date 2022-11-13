package com.example.mandatorycatapp.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mandatorycatapp.repository.AuthentificationLoginRepo
import com.example.mandatorycatapp.repository.CatsRepository
import com.google.firebase.auth.FirebaseUser

class AuthentificationLoginViewModel: ViewModel() {
    private val authentificationLoginRepo: AuthentificationLoginRepo = AuthentificationLoginRepo()

    val userMutableLiveData: LiveData<FirebaseUser> = authentificationLoginRepo.userMutableLiveData
    //val errorMessageLiveData: LiveData<String> = authentificationLoginRepo.errorMessageLiveData
    //val updateMessageLiveData: LiveData<String> = authentificationLoginRepo.updateMessageLiveData

    //constructor
    //init {
        //reload()
    //}
    //opret en user
    fun registerUser(email: String, password: String){
        authentificationLoginRepo.registerNewUser(email,password)
    }
    //log en user ind
    fun logIn(email: String, password: String){
        authentificationLoginRepo.logIn(email, password)
    }
    //log en user ud
    fun signOut(){
        authentificationLoginRepo.signOut()
    }

}