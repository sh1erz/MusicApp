package com.example.musicapp.data.db

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.example.musicapp.fakes.TRACKS_DATASET
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4ClassRunner::class)
class TrackDatabaseMigrationsTest {

    private val TEST_DB = "migration-test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        TrackDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    val testTrack = TRACKS_DATASET[0]

    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
        var db = helper.createDatabase(TEST_DB, 1).apply {
            execSQL(
                "INSERT INTO track (id, listened, title, link, duration, preview, artist, album) " +
                        "VALUES(${testTrack.id}, ${testTrack.listened}, '${testTrack.title}', " +
                        "'${testTrack.link}' ,${testTrack.duration} ,'${testTrack.musicUri}' ,'${
                            Gson().toJson(
                                testTrack.artist
                            )
                        }', " +
                        "'${Gson().toJson(testTrack.album)}')"
            )
            close()
        }
        db = helper.runMigrationsAndValidate(
            TEST_DB,
            2,
            true,
            MIGRATION_1_2
        )
        val appDb = Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            TrackDatabase::class.java,
            TEST_DB
        ).addMigrations(MIGRATION_1_2).build()
        val track = appDb.getTrackDao().getTrackById(testTrack.id)
        assertEquals(0, track.newColumn)
    }
}