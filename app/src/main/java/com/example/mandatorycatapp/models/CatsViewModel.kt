package com.example.mandatorycatapp.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mandatorycatapp.repository.CatsRepository

//nedarver fra viewmodel
class CatsViewModel : ViewModel() {
    private val catsRepository = CatsRepository()

    val catsLiveData: LiveData<List<Cat>> = catsRepository.catsLiveData
    val errorMessageLiveData: LiveData<String> = catsRepository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = catsRepository.updateMessageLiveData

    //constructor
    init {
        reload()
    }
    //Reload them cats
    fun reload(){
        catsRepository.getCats()
    }
    //Why an int?
    //GET a cat
    operator fun get(index: Int): Cat? {
        return catsLiveData.value?.get(index)
    }
    fun add(newCat: Cat) {
        catsRepository.add(newCat)
    }

    fun delete(id: Int) {
        catsRepository.delete(id)
    }

    /*fun update(cat: Cat) {
        catsRepository.update(cat)
    }*/
    fun sortByName() {
        catsRepository.sortByName()
    }
    fun sortByNameDesending() {
        catsRepository.sortByNameDesending()
    }
    fun sortByDes() {
        catsRepository.sortByDes()
    }
    fun filterName(name: String) {
        catsRepository.filterByName(name)
    }
}