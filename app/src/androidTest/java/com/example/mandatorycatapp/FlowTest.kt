package com.example.mandatorycatapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
class FlowTest{
    @get: Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun flowTest(){
        //Starter på firstfragment
        //Arrange - is the id in view in FirstFragment
        onView(withId(R.id.FirstFragment))
         //check/assert
         .check(matches(isDisplayed()))

        //Arrange/View
        onView(withId(R.id.action_button_signin))
        //Act/Action - klik på login knap
        .perform(click())
        //Assert/Check
        .check(matches(isDisplayed()))

        //Now transistion to fragment login

        //Arrange/View - is the id in view in LoginFragment
        onView(withId(R.id.LoginFragment))
        //Assert/Check
        .check(matches(isDisplayed()))

        //Arrange/View
        onView(withId(R.id.button_back))
        //Act/Action - klik på back knap
        .perform(click())
        //Assert/Check
        .check(matches(isDisplayed()))

        //transistion back to first fragment
    }
}