package com.example.musicapp.ui.details

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.musicapp.R
import com.example.musicapp.fakes.test_artist
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ArtistFragmentTest {

    @Test
    fun test_isArtistDataVisible() {

        val bundle = bundleOf("artist" to test_artist)
        val scenario = launchFragmentInContainer<ArtistFragment>(fragmentArgs = bundle)
        onView(withId(R.id.tvName)).check(matches(withText(test_artist.name)))
        onView(withId(R.id.tvLink)).check(matches(withText(test_artist.link)))

    }

}

