package com.example.mandatorycatapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.hamcrest.CoreMatchers.endsWith
import org.junit.Assert.*
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FirstFragmentTest{

    //launch an activity (Rule) with espresso, by creating an scenario
    //Now make it global, so you dont need to write a scenariorule everytime
    //add at testrule that will be called before each function, it will do
    // the same as creating an scenariorule in each one
    //jeg er i tvivl om hvorfor jeg ikke må lave reglen direkte på firstfragment
    @get: Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testA_IdMessageSortAndFilter(){
        //first - is the id FirstFragment
        onView(withId(R.id.FirstFragment))
         //check/assert
        .check(matches(isDisplayed()))

        //second - is the textview_message correct
        onView(withId(R.id.textview_message))
            .check(matches(isDisplayed()))

        //third - test visibility - is the sort and filter btn visible
        onView(withId(R.id.FirstFragment))
            .check(matches(isDisplayed()))

        //test, does that text say SORT, is the text what it should be
        onView(withText("SORT"))
            .check(matches(isDisplayed())) //method 1
        //another way to test visibility, instead of using is displayed
        //if one doesnt work, use the other way
        onView(withText("FILTER"))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE))) //method 2
    }

    //check navigation, click btn - flow
    @Test
    fun testB_FirstFragmentToLogInFragment() {
        //is the sign in btn in view
        onView(withId(R.id.action_button_signin))
           .check(matches(isDisplayed()))

        //click on sign in btn, you'll be navigated to login fragment
        onView(withId(R.id.action_button_signin))
            .perform(click())

        //transition happens
        //to fragment_login

        //is there a text with Authentication?
        onView(withText("Authentication"))
             //check/Assert
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun testC_LoginUserFromFirstFragToLoginFrag() {
        //by clicking sign in btn, you'll be navigated to login fragment
        onView(withId(R.id.action_button_signin))
            .perform(click())
        //transition happens to LoginFragment
        //onView(withId(R.id.fragment_login))

        //user needs to logged in
        onView(withId(R.id.emailInputField)).perform(typeText("user@user.dk"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.passwordInputField)).perform(typeText("123456"))
        Espresso.closeSoftKeyboard()

        //press btn to sign the user in
        onView(withId(R.id.sign_in_button)).perform(click())
        Espresso.closeSoftKeyboard()
    }

    @Test
    fun testD_CreateCat() {
        //tryk på fab knappen for at oprette en kat
        onView(withId(R.id.fab_add_cat)).perform(click())
        //formular frem
        onView(withText("REPORT LOST CAT"))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.cat_name_input)).perform(typeText("Klovnekat"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.cat_description_input)).perform(typeText("hej kat"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.cat_place_input)).perform(typeText("Roskilde"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.cat_reward_input)).perform(typeText("1200"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.cat_date_input)).perform(typeText("281122"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.cat_picture_url_input)).perform(typeText("cat.jpg"))
        Espresso.closeSoftKeyboard()
        //tryk på knappen
        onView(withId(R.id.buttonCreateCat)).perform(click())

        //transition happens to first fragment, snackbar

        //den kan ikke asserte den tekst der står inde i kortet/recyclerview.
        //onView(withId(R.id.recyclerView))
        //.check(matches(isDisplayed()))
        //pause(2000)
        //onView(withText("Misser"))
         //.check(matches(isDisplayed()))

        //ASSERT - if theres a snackbar(text) that pops up with "Cat created"
        pause(2000)
        onView(withText("Cat created"))
        .check(matches(isDisplayed()))
    }

    //pause metoden
    private fun pause(millis: Long) {
            try {
                Thread.sleep(millis)
                // https://www.repeato.app/android-espresso-why-to-avoid-thread-sleep/
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
    }
}