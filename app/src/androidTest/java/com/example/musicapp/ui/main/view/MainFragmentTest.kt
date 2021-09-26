package com.example.musicapp.ui.main.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.musicapp.R
import com.example.musicapp.data.db.TrackDao
import com.example.musicapp.data.entities.Track
import com.example.musicapp.di.RetrofitModule
import com.example.musicapp.di.RoomModule
import com.example.musicapp.fakes.FakeDeezerService
import com.example.musicapp.fakes.FakeTrackDao
import com.example.musicapp.fakes.TRACKS_DATASET
import com.example.musicapp.retrofit.DeezerService
import com.example.musicapp.util.launchFragmentInHiltContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(RetrofitModule::class, RoomModule::class)
class MainFragmentTest{
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        launchFragmentInHiltContainer<MainFragment>()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object TestRoomModule {
        @Provides
        fun provideTrackDao(): TrackDao = FakeTrackDao().apply {
            insertTracks(TRACKS_DATASET)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object TestDataSourceModule {
        @Provides
        fun provideDeezerService(): DeezerService = FakeDeezerService()
    }

    @Test
    fun test_backNavigation_toMainFragment() {
        val position = 1
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                onView(withId(R.id.recyclerTracks))
                    .perform(
                        actionOnItemAtPosition<TrackAdapter.TrackViewHolder>(
                            position,
                            click()
                        )
                    )

                pressBack()

                onView(withId(R.id.recyclerTracks)).check(matches(isDisplayed()))
            }
        }


    }

    @Test
    fun test_recyclerItemDataIsCorrect() {
        val position = 0
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                scrollAtAndCheckTrackVisible(position, TRACKS_DATASET[position])
            }
        }
    }

    @Test
    fun test_selectListItem_isTrackFragmentVisible() {
        val position = 0
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                onView(withId(R.id.recyclerTracks))
                    .perform(
                        RecyclerViewActions
                            .scrollToPosition<TrackAdapter.TrackViewHolder>(position),
                        actionOnItemAtPosition<TrackAdapter.TrackViewHolder>(
                            position,
                            click()
                        )
                    )
            }
        }
    }

    private fun scrollAtAndCheckTrackVisible(position: Int, track: Track) {
        onView(withId(R.id.recyclerTracks))
            .perform(
                RecyclerViewActions
                    .scrollToPosition<TrackAdapter.TrackViewHolder>(position)
            )
        onView(withId(R.id.tvTitle))
            .check(matches(withText(track.title)))
        onView(withId(R.id.tvArtist))
            .check(matches(withText(track.artist.name)))
    }

    private fun execute(f: () -> Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                f()
            }
        }
    }
}
