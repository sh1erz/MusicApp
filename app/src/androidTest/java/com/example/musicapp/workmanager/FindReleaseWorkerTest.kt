package com.example.musicapp.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.db.RoomModule
import com.example.retrofit.RetrofitModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(RetrofitModule::class, RoomModule::class)
@HiltAndroidTest
class FindReleaseWorkerTest {

    private val uiDevice by lazy { UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()) }
    private val timeout = 15000L
    private lateinit var context: Context

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        context = InstrumentationRegistry.getInstrumentation().targetContext
        WorkManager.initialize(
            context, Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .setMinimumLoggingLevel(android.util.Log.DEBUG)
                .build()
        )
    }

    @Test
    fun doWorkTest() {
        val request = OneTimeWorkRequestBuilder<FindReleaseWorker>()
            .build()
        WorkManager.getInstance(context).enqueue(request)

        uiDevice.apply {
            openNotification()
            val release = "Check out new release of"
            wait(Until.hasObject(By.textStartsWith(release)), timeout)
            findObject(By.textStartsWith(release)).clickAndWait(Until.newWindow(), timeout)
            wait(Until.hasObject(By.textStartsWith("Track")), timeout)
        }
    }
}
