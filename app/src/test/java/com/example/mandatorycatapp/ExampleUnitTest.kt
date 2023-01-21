package com.example.mandatorycatapp

import com.example.mandatorycatapp.models.Cat
import com.example.mandatorycatapp.models.CatsViewModel
import com.example.mandatorycatapp.repository.CatsRepository
import org.junit.Assert
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {

    @Test
    fun addition_isCorrectTest() {
        Assert.assertEquals(4, 2 + 2)
    }

    @Test
    fun catClassTest(){
        //Arrange
        val result = Cat(
            0,
            "navn",
            "En kat",
            "Roskilde",
            2000,
            "k@k.dk",
            1601,
            "kat.jpg"
        )

        //Assert
        Assert.assertEquals(0, result.id)
        Assert.assertEquals("navn", result.name)
        Assert.assertEquals("En kat", result.description)
        Assert.assertEquals("Roskilde", result.place)
        Assert.assertEquals(2000, result.reward)
        Assert.assertEquals("k@k.dk", result.userId)
        Assert.assertEquals(1601, result.date)
        Assert.assertEquals("kat.jpg", result.pictureUrl)
    }

    @Test
    fun whenValidTest(){
        //Arrange
        val catObj = CatsRepository()
        //Act
        val result = catObj.filterByName("Mis")
        //Assert
        Assert.assertEquals("Mis", result)
    }
}