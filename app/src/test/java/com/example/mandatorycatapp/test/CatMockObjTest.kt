package com.example.mandatorycatapp.test

import com.example.mandatorycatapp.models.CatMockObj
import org.junit.Assert
import org.junit.Test

class CatMockObjTest{

    //Annotate with test
    @Test
    fun whenInputIsValid(){
        //I will test a case when input is valid

        //Arrange
        //hvis navn ikke er tomt
        val name = "Mis"
        //hvis pris ikke er 0, men er større end 0
        val price = 2000

        //Act - get the result
        //I will pass name, price
        val result = CatMockObj.validateInput(name, price)

        //Assert - check if result is true, run the test-case
        //Assert.assertThat(result).isEquals(true)
        Assert.assertEquals(true, result)

        //Point is: here we define, whenever we pass some valid name and price,
        //we should get True from our validateInput function,
    }
}