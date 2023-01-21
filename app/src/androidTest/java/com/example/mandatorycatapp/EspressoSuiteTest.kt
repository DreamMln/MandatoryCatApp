package com.example.mandatorycatapp

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.Suite

//Suite is used for bundling up a bunch of test-classes
//to run them at the same time
@RunWith(Suite::class)
//instansiate test-classes
@Suite.SuiteClasses(
    MainActivityTest::class,
    FlowTest::class,
    FirstFragmentTest::class
)
//Run all test-classes
class EspressoSuiteTest