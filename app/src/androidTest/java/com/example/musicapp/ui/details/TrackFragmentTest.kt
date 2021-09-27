package com.example.musicapp.ui.details

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.example.musicapp.di.RetrofitModule
import com.example.musicapp.di.RoomModule
import com.example.musicapp.fakes.TRACKS_DATASET
import com.example.musicapp.ui.details.TrackFragment.Companion.TRACK
import com.example.musicapp.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(RetrofitModule::class, RoomModule::class)
class TrackFragmentTest{
    private val position = 1
    private val track = TRACKS_DATASET[position]
    private val uiDevice by lazy { UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()) }

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        Intents.init()
        launchFragmentInHiltContainer<TrackFragment>(bundleOf(TRACK to track))
    }
    @After
    fun after(){
        Intents.release()
    }

    @Test
    fun test_showNotification(){
        val timeout = 2000L
        val expectedAppName = "MusicApp"

        uiDevice.apply {
            wait(Until.hasObject(By.text(expectedAppName)), timeout)
            openNotification()
            wait(Until.hasObject(By.textStartsWith(expectedAppName)), timeout)
        }
    }

    @Test
    fun test_onNotificationClick_goToActivity(){
        val timeout = 2000L
        val expectedTitle = track.title
        uiDevice.apply {
            wait(Until.hasObject(By.text(expectedTitle)), timeout)
            openNotification()
            wait(Until.hasObject(By.textStartsWith(expectedTitle)), timeout)
            findObject(By.textStartsWith(expectedTitle)).clickAndWait(Until.newWindow(), timeout)
        }
        intended(hasAction(Intent.ACTION_MAIN))
    }

}