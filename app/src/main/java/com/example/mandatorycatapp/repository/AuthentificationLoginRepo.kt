package com.example.mandatorycatapp.repository

import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class AuthentificationLoginRepo {
    //kun en user af gangen, ingen liste
    val userMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    //errorMutableLiveData for errors
    val errorMutableLiveData: MutableLiveData<String> = MutableLiveData<String>()
    //MutableLiveData for updating
    val updateMutableLiveData: MutableLiveData<String> = MutableLiveData()

    //signup a user
    fun registerNewUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            task -> if (task.isSuccessful) {
            userMutableLiveData.postValue(auth.currentUser)
          }else{
              errorMutableLiveData.postValue(task.exception?.message)
          }
        }
    }
    //log a user in
    fun logIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            task -> if (task.isSuccessful){
                userMutableLiveData.postValue(auth.currentUser)
            }else{
                errorMutableLiveData.postValue(task.exception?.message)
            }
        }
    }
    //logout
    fun signOut(){
        auth.signOut()
        userMutableLiveData.postValue(auth.currentUser)
    }

}