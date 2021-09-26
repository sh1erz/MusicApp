package com.example.musicapp.ui.main.view

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.musicapp.R
import com.example.musicapp.util.launchFragmentInHiltContainer
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainFragmentTest{
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object TestDbModule{

    }

    @Test
    fun test_initFragment() {
        launchFragmentInHiltContainer<MainFragment>()
    }

    private fun scrollAtAndCheckTestVisible(position: Int, text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerTracks))
            .perform(
                RecyclerViewActions
                    .scrollToPosition<TrackAdapter.TrackViewHolder>(position))
        Espresso.onView(ViewMatchers.withText(text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}