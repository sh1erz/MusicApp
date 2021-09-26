package com.example.musicapp.ui.details

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.musicapp.R
import com.example.musicapp.data.entities.Artist
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

val test_artist = Artist(
    27,
    "Name Surname",
    "https://www.deezer.com/artist/27",
    null,
    "https://api.deezer.com/artist/27/image",
    "https://cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/56x56-000000-80-0-0.jpg",
    "https://cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/250x250-000000-80-0-0.jpg",
    "https://cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/500x500-000000-80-0-0.jpg",
    "https://cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/1000x1000-000000-80-0-0.jpg",
    null,
    null,
    "https://api.deezer.com/artist/27/top?limit=50"
)