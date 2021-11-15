# MusicApp
Streaming music service for Android devices. History of listened tracks. Notifications with new releases. Alternative theme styles.

### Used technologies:

- Jetpack Navigation
- MVP
- MVVM
- Hilt
- Retrofit
- RxJava2
- Glide
- Picasso
- Kotlin Coroutines
- Firebase
- Espresso
- UI Automator

### What was made in this project:

- Created layouts for all screens.
- Developed multimodule app architecture with Repository pattern using Hilt for DI.
- Created Bottom Navigation, app navigation using Jetpack Navigation, configured transitions between fragments.
- Created History screen using MVP, retrieving listened tracks from Room database.
- Created Search screen using MVVM and RecycleView for artist and track types.
- Implemented search hints with network calls and RxJava.
- Implemented music player using foreground service with MediaPlayer.
- Implemented getting track data with REST network calls using Retrofit and Glide framework.
- Implemented user theme and releases notification settings, stored in Shared Preferences.
- Created song releases notifications with WorkManager.
- Implemented Firebase Crashlutics and Cloud Messaging for the app.
- Configured Obfuscation for release build.
- UI tested using Espresso and UI Automator.
