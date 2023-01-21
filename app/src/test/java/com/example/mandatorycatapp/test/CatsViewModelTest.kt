package com.example.mandatorycatapp.test


import com.example.mandatorycatapp.models.Cat
import com.example.mandatorycatapp.models.CatsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CatsViewModelTest {


    @Test
    fun setupTest() {
       //Test setup - create a cat object
       //Arrange
        val cat = CatsViewModel()
        //val catObj = Cat(
           // 0, "Missekat", "En kat", "Roskilde",
          //  1500, "kat@kat.dk", 2001, "kat.png"
       // )
    }

    @Test
    fun addCatTest() {
        //Arrange - setup the test
        val cat = CatsViewModel()
        //Act
        val addResult = cat.add(newCat = Cat(7, "kat", "en kat",
            "roskilde", 3000, "email", 1701, "url"))

        //Assert - check if
        Assert.assertEquals(7, addResult)
    }
}