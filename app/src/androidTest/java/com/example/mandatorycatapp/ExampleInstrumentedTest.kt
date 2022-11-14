package com.example.mandatorycatapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.util.regex.Pattern.matches

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

//Example local unit test, which will execute on the
// development machine (host).

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    //@JvmField
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mandatorycatapp", appContext.packageName)

        //holder pause indtil resten er klar
        //pause(3000)
        Espresso.onView(ViewMatchers.withText("SORT"))
            .check(ViewAssertions.matches(isDisplayed()))

        //pause(3000)
        onView(withText("FILTER"))
            .check(ViewAssertions.matches(isDisplayed()))

        Espresso.onView(withId(R.id.action_signin)).perform(ViewActions.click())
        // tricks transition to authen fragment

        //derefter
        Espresso.onView(ViewMatchers.withText("Authentication"))
            .check(ViewAssertions.matches(isDisplayed()))

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