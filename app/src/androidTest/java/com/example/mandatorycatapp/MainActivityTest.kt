package com.example.mandatorycatapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @Test
    fun testMainActivityIsInView(){
    //launch an activity (Rule) with espresso, by creating an scenario
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

     //hvorfor er activityScenario ikke global? for at undgå gentagelse
     //det kan være mere effektivt at isolere den, hvis man feks vil teste
     //på en specifik prop

        //test om activity er i view og om den er der
        //view activity mains id, og check på om id'et matcher det der er displayed?
        onView(ViewMatchers.withId(R.id.main))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //er toolbar id'et in view
        onView(ViewMatchers.withId(R.id.toolbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}